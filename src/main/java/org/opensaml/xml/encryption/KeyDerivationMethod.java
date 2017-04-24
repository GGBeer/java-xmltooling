/*
 * Licensed to the University Corporation for Advanced Internet Development, 
 * Inc. (UCAID) under one or more contributor license agreements.  See the 
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.xml.encryption;

import org.opensaml.xml.ElementExtensibleXMLObject;
import org.opensaml.xml.util.XMLConstants;
import org.opensaml.xml.validation.ValidatingXMLObject;

import javax.xml.namespace.QName;

/**
 * XMLObject representing XML Encryption 1.1, http://www.w3.org/2009/xmlenc11#KeyDerivationMethod element.
 */
public interface KeyDerivationMethod extends ValidatingXMLObject, ElementExtensibleXMLObject {
    
    /** Element local name. */
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "KeyDerivationMethod";
    
    /** Default element name. */
    public static final QName DEFAULT_ELEMENT_NAME = new QName(XMLConstants.XMLENC11_NS, DEFAULT_ELEMENT_LOCAL_NAME,
            XMLConstants.XMLENC11_PREFIX);
    
    /** Local name of the XSI type. */
    public static final String TYPE_LOCAL_NAME = "KeyDerivationMethodType";
        
    /** QName of the XSI type. */
    public static final QName TYPE_NAME = new QName(XMLConstants.XMLENC11_NS, TYPE_LOCAL_NAME,
            XMLConstants.XMLENC11_PREFIX);
    
    /** Algorithm attribute name. */
    public static final String ALGORITHM_ATTRIBUTE_NAME = "Algorithm";
    
   
    /**
     * Gets the algorithm URI attribute used in this EncryptionMethod.
     * 
     * @return the Algorithm attribute URI attribute string
     */
    public String getAlgorithm();
 
    /**
     * Sets the algorithm URI attribute used in this EncryptionMethod.
     * 
     * @param newAlgorithm the new Algorithm URI attribute string
     */
    public void setAlgorithm(String newAlgorithm);
    

}
