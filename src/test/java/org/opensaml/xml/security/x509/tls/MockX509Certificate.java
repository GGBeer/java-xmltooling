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

package org.opensaml.xml.security.x509.tls;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.GeneralNamesBuilder;

/**
 * Mock impl of {@link X509Certificate} used in testing hostname verifiers.
 */
public class MockX509Certificate extends X509Certificate {
    
    private X500Principal subjectX500Principal;
    
    private Collection<List<?>> subjectAltNames;
    
    private Map<String, byte[]> extensions;

    /**
     * Constructor.
     *
     * @param subjectX500Principal
     */
    public MockX509Certificate(X500Principal subject, Collection<List<?>> subjAlts) {
        super();
        subjectX500Principal = subject;
        subjectAltNames = subjAlts;
        extensions = new HashMap<String, byte[]>();
        
        // Add proper DER-encoded alt names extension based on subjAlts values, so works with code that extracts 
        // subject alt names via extensions parsing.
        if (subjAlts != null && subjAlts.size() > 0) {
            GeneralNamesBuilder generalNamesBuilder = new GeneralNamesBuilder();
            for (List<?> subjAlt : subjAlts) {
                Integer type = (Integer) subjAlt.get(0);
                String value = (String) subjAlt.get(1);
                GeneralName generalName = new GeneralName(type, value);
                generalNamesBuilder.addName(generalName);
            }
            GeneralNames generalNames = generalNamesBuilder.build();
            
            try {
                Extension ext = new Extension(Extension.subjectAlternativeName, false, generalNames.getEncoded());
                extensions.put(ext.getExtnId().getId(), ext.getExtnValue().getEncoded("DER"));
            } catch (IOException e) {
                throw new RuntimeException("Problem building subject alt names extension", e);
            }
        }
        
    }
    
    public Map<String, byte[]> getExtensionsMap() {
        return extensions;
    }
    
    /** {@inheritDoc} */
    public X500Principal getSubjectX500Principal() {
        return subjectX500Principal;
    }
    
    /** {@inheritDoc} */
    public Collection<List<?>> getSubjectAlternativeNames() throws CertificateParsingException {
        return subjectAltNames;
    }
    
    /** {@inheritDoc} */
    public byte[] getExtensionValue(String oid) {
        return getExtensionsMap().get(oid);
    }
    
    
    // Methods below here are just unimplemented stubs
    
    /** {@inheritDoc} */
    public boolean hasUnsupportedCriticalExtension() {
        return false;
    }

    /** {@inheritDoc} */
    public Set<String> getCriticalExtensionOIDs() {
        return null;
    }

    /** {@inheritDoc} */
    public Set<String> getNonCriticalExtensionOIDs() {
        return null;
    }

    /** {@inheritDoc} */
    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
    }

    /** {@inheritDoc} */
    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
    }

    /** {@inheritDoc} */
    public int getVersion() {
        return 0;
    }

    /** {@inheritDoc} */
    public BigInteger getSerialNumber() {
        return null;
    }

    /** {@inheritDoc} */
    public Principal getIssuerDN() {
        return null;
    }

    /** {@inheritDoc} */
    public Principal getSubjectDN() {
        return null;
    }

    /** {@inheritDoc} */
    public Date getNotBefore() {
        return null;
    }

    /** {@inheritDoc} */
    public Date getNotAfter() {
        return null;
    }

    /** {@inheritDoc} */
    public byte[] getTBSCertificate() throws CertificateEncodingException {
        return null;
    }

    /** {@inheritDoc} */
    public byte[] getSignature() {
        return null;
    }

    /** {@inheritDoc} */
    public String getSigAlgName() {
        return null;
    }

    /** {@inheritDoc} */
    public String getSigAlgOID() {
        return null;
    }

    /** {@inheritDoc} */
    public byte[] getSigAlgParams() {
        return null;
    }

    /** {@inheritDoc} */
    public boolean[] getIssuerUniqueID() {
        return null;
    }

    /** {@inheritDoc} */
    public boolean[] getSubjectUniqueID() {
        return null;
    }

    /** {@inheritDoc} */
    public boolean[] getKeyUsage() {
        return null;
    }

    /** {@inheritDoc} */
    public int getBasicConstraints() {
        return 0;
    }

    /** {@inheritDoc} */
    public byte[] getEncoded() throws CertificateEncodingException {
        return null;
    }

    /** {@inheritDoc} */
    public void verify(PublicKey key) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException,
            NoSuchProviderException, SignatureException {
        
    }

    /** {@inheritDoc} */
    public void verify(PublicKey key, String sigProvider) throws CertificateException, NoSuchAlgorithmException,
            InvalidKeyException, NoSuchProviderException, SignatureException {
        
    }

    /** {@inheritDoc} */
    public String toString() {
        return null;
    }

    /** {@inheritDoc} */
    public PublicKey getPublicKey() {
        return null;
    }

}
