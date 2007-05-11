/*
 * Copyright [2006] [University Corporation for Advanced Internet Development, Inc.]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.xml.encryption;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

import org.apache.log4j.Logger;
import org.apache.xml.security.Init;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.signature.KeyInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A class for encrypting XMLObjects, their content, and keys.
 */
public class Encrypter {
    
    /** Class logger. */
    private Logger log = Logger.getLogger(Encrypter.class);

    /**
     * Encrypts the DOM representation of the XMLObject.
     * 
     * @param xmlObject the XMLObject to be encrypted
     * @param encParams parameters for encrypting the data
     * @param kekParams parameters for encrypting the encryption key
     * 
     * @return the resulting EncryptedData element
     * @throws EncryptionException exception thrown on encryption errors
     */
    public EncryptedData encryptElement(XMLObject xmlObject, 
            EncryptionParameters encParams, KeyEncryptionParameters kekParams)  throws EncryptionException {
        return encryptElement(xmlObject, encParams, kekParams, false);
    }
    
    /**
     * Encrypts the DOM representation of the content of an XMLObject.
     * 
     * @param xmlObject the XMLObject to be encrypted
     * @param encParams parameters for encrypting the data
     * @param kekParams parameters for encrypting the encryption key
     * 
     * @return the resulting EncryptedData element
     * @throws EncryptionException exception thrown on encryption errors
     */
    public EncryptedData encryptElementContent(XMLObject xmlObject, 
            EncryptionParameters encParams, KeyEncryptionParameters kekParams) throws EncryptionException {
        return encryptElement(xmlObject, encParams, kekParams, true);
    }
    
    /**
     * Encrypts a key used for encrypting an XMLObject.
     * 
     * @param key the key to encrypt
     * @param containingDocument the document that will own the resulting element
     * @param kekParams parameters for encrypting the key
     * 
     * @return the resulting EncryptedKey element
     * 
     * @throws EncryptionException  exception thrown on encryption errors
     */
    public EncryptedKey encryptKey(Key key, 
            KeyEncryptionParameters kekParams, Document containingDocument) throws EncryptionException {
        
        XMLCipher xmlCipher;
        try {
            xmlCipher = XMLCipher.getInstance(kekParams.getAlgorithm());
            xmlCipher.init(XMLCipher.WRAP_MODE, kekParams.getEncryptionKey());
        } catch (XMLEncryptionException e) {
            throw new EncryptionException("Error initializing cipher instance on key encryption", e); 
        }
        
        org.apache.xml.security.encryption.EncryptedKey encryptedKey;
        try {
            encryptedKey = xmlCipher.encryptKey(containingDocument, key);
        } catch (XMLEncryptionException e) {
            throw new EncryptionException("Error encrypting element on key encryption", e);
        }
        
        Unmarshaller unmarshaller =
            Configuration.getUnmarshallerFactory().getUnmarshaller(EncryptedKey.DEFAULT_ELEMENT_NAME);
        
        EncryptedKey xmlEncryptedKey;
        try {
            xmlEncryptedKey = 
                (EncryptedKey) unmarshaller.unmarshall(xmlCipher.martial(containingDocument, encryptedKey));
        } catch (UnmarshallingException e) {
            throw new EncryptionException("Error unmarshalling EncryptedKey element");
        }
        
        if (kekParams.getKeyInfo() != null) {
            xmlEncryptedKey.setKeyInfo(kekParams.getKeyInfo());
        }
        
        if (kekParams.getRecipient() != null) {
            xmlEncryptedKey.setRecipient(kekParams.getRecipient());
        }
        
        return xmlEncryptedKey;
    }
    
    /**
     * Encrypts the given XMLObject.
     * 
     * @param xmlObject the XMLObject to be encrypted
     * @param encParams the encryption parameters to use
     * @param kekParams the key encryption parameters to use
     * @param encryptContentMode whether just the content of the XMLObject should be encrypted
     * 
     * @return the resulting EncryptedData element
     * @throws EncryptionException exception thrown on encryption errors
     */
    private EncryptedData encryptElement(
            XMLObject xmlObject,
            EncryptionParameters encParams,  KeyEncryptionParameters kekParams, 
            boolean encryptContentMode) throws EncryptionException {
        
        checkParams(encParams, kekParams);
        
        Element targetElement = xmlObject.getDOM();
        if(targetElement == null){
            Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(xmlObject);
            try {
                targetElement = marshaller.marshall(xmlObject);
            } catch (MarshallingException e) {
                throw new EncryptionException("Error marshalling target XMLObject", e);
            }
        }
        
        Document ownerDocument = xmlObject.getDOM().getOwnerDocument();
        
        XMLCipher xmlCipher;
        try {
            xmlCipher = XMLCipher.getInstance(encParams.getAlgorithm());
            xmlCipher.init(XMLCipher.ENCRYPT_MODE, encParams.getEncryptionKey());
        } catch (XMLEncryptionException e) {
            throw new EncryptionException("Error initializing cipher instance", e);
        }
        org.apache.xml.security.encryption.EncryptedData encryptedData;
        try {
            encryptedData = xmlCipher.encryptData(ownerDocument, targetElement, encryptContentMode);
        } catch (Exception e) {
            throw new EncryptionException("Error encrypting element", e);
        }
        
        Unmarshaller unmarshaller =
            Configuration.getUnmarshallerFactory().getUnmarshaller(EncryptedData.DEFAULT_ELEMENT_NAME);
        
        EncryptedData xmlEncryptedData;
        try {
            xmlEncryptedData = (EncryptedData) unmarshaller.unmarshall(xmlCipher.martial(ownerDocument, encryptedData));
        } catch (UnmarshallingException e) {
            throw new EncryptionException("Error unmarshalling EncryptedData element", e);
        }
        
        if (encParams.getKeyInfo() != null) {
            xmlEncryptedData.setKeyInfo(encParams.getKeyInfo());
        }
        
        if (kekParams != null && kekParams.getEncryptionKey() != null) {
            EncryptedKey xmlEncryptedKey = encryptKey(encParams.getEncryptionKey(), kekParams, ownerDocument);
            
            if (xmlEncryptedData.getKeyInfo() == null) {
                KeyInfo keyInfo = (KeyInfo) Configuration.getBuilderFactory()
                    .getBuilder(KeyInfo.DEFAULT_ELEMENT_NAME).buildObject(KeyInfo.DEFAULT_ELEMENT_NAME);
                xmlEncryptedData.setKeyInfo(keyInfo);
            }
            
            xmlEncryptedData.getKeyInfo().getEncryptedKeys().add(xmlEncryptedKey);
            
        }
        
        return xmlEncryptedData;
    }
    
    /**
     * Check the encryption parameters and key encryption parameters for valid combinations of options.
     * If an encryption key is to be automatically generated, generate it here.
     * 
     * @param encParams the encryption parameters to use
     * @param kekParams the key encryption parameters to use
     * @throws EncryptionException exception thrown on encryption errors
     */
    private void checkParams(EncryptionParameters encParams, 
            KeyEncryptionParameters kekParams) throws EncryptionException {
        
        if (encParams == null ) {
            throw new EncryptionException("EncryptionParameters argument was null");
        }
        
        if (encParams.getEncryptionKey() == null && (kekParams == null || kekParams.getEncryptionKey() == null)) {
            throw new EncryptionException("Using a generated encryption key "
                    + "requires a KeyEncryptionParameters object and key");
        }
        
        if (encParams.getEncryptionKey() == null) {
                encParams.setEncryptionKey(generateEncryptionKey(encParams.getAlgorithm())); 
        }
        
        if (encParams.getEncryptionKey() == null) {
            throw new EncryptionException("No encryption key was generated by the KeyGenerator");
        }
    }
    
    /**
     * Randomly generates a Java JCE symmetric Key object from the specified XML Encryption algorithm URI.
     * 
     * @param algoURI  The XML Encryption algorithm URI
     * @return a randomly-generated symmetric Key
     * @throws EncryptionException thrown if there is a problem generating the key
     */
    public Key generateEncryptionKey(String algoURI) throws EncryptionException {
        String jceAlgorithmName = JCEMapper.getJCEKeyAlgorithmFromURI(algoURI);
        int keyLength = JCEMapper.getKeyLengthFromURI(algoURI);
        Key key = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(jceAlgorithmName);
            keyGenerator.init(keyLength);
            key = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
                throw new EncryptionException("Algorithm URI not found when generating encryption key: " 
                        + algoURI);
        }
        return key;
    }
    
    /*
     * Initialize the Apache XML security library if it hasn't been already
     */
    static {
        if (!Init.isInitialized()) {
            Init.init();
        }
    }
}