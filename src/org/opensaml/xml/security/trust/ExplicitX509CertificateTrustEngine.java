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

package org.opensaml.xml.security.trust;

import java.security.cert.X509Certificate;

import org.apache.log4j.Logger;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.CredentialCriteriaSet;
import org.opensaml.xml.security.credential.CredentialResolver;
import org.opensaml.xml.security.x509.X509Credential;

/**
 * Trust engine that evaluates a credential's X.509 certificate against certificates expressed within a set 
 * of trusted credentials obtained from a credential resolver.
 * 
 * The credential being tested is valid if its entity certificate matches the entity certificate contained 
 * within any of the trusted credentials produced by the given credential resolver.
 */
public class ExplicitX509CertificateTrustEngine extends AbstractTrustEngine<X509Credential> 
        implements TrustEngine<X509Credential> {

    /** Class logger. */
    private static Logger log = Logger.getLogger(ExplicitX509CertificateTrustEngine.class);
    
    /**
     * Constructor.
     * 
     * @param credentialResolver credential resolver which is used to resolve trusted credentials
     */
    public ExplicitX509CertificateTrustEngine(CredentialResolver credentialResolver) {
        if (credentialResolver == null) {
            throw new IllegalArgumentException("Credential resolver may not be null");
        }
        setCredentialResolver(credentialResolver); 
    }

    public boolean validate(X509Credential untrustedCredential, X509Credential trustedCredential)
            throws SecurityException {
        
        X509Certificate untrustedCertificate = untrustedCredential.getEntityCertificate();
        X509Certificate trustedCertificate = trustedCredential.getEntityCertificate();
        if (untrustedCertificate == null) {
            if (log.isDebugEnabled()) {
                log.debug("Untrusted credential contained no entity certificate, unable to evaluate");
            }
            return false;
        } else if (trustedCertificate == null) {
            if (log.isDebugEnabled()) {
                log.debug("Trusted credential contained no entity certificate, unable to evaluate");
            }
            return false;
        }
        
        if (untrustedCertificate.equals(trustedCertificate)) {
            if (log.isDebugEnabled()) {
                log.debug("Validated credential for entity " + untrustedCredential.getEntityId()
                        + " against trusted entity certificate");
            }
            return true;
        }

        if (log.isDebugEnabled()) {
            log.debug("Credential for entity " + untrustedCredential.getEntityId()
                    + " did not validate against trusted entity certificate");
        }

        return false;
    }
    
    public boolean validate(X509Credential untrustedX509Credential, Iterable<Credential> trustedCredentials) 
            throws SecurityException {
        
        for (Credential trustedCredential : trustedCredentials) {
            if ( ! (trustedCredential instanceof X509Credential)) {
                if (log.isDebugEnabled()) {
                    log.debug("Skipping evaluation against trusted, non-X509Credential");
                }
                continue;
            }
            X509Credential trustedX509Credential = (X509Credential) trustedCredential;
            if (validate(untrustedX509Credential, trustedX509Credential)) {
                return true;
            }
        }
        
        return false;
    }

    /** {@inheritDoc} */
    public boolean validate(X509Credential untrustedCredential, CredentialCriteriaSet trustedCredentialCriteria) 
            throws SecurityException {
        
        if (untrustedCredential == null) {
            return false;
        }

        if (log.isDebugEnabled()) {
            log.debug("Validating credential for entity " + untrustedCredential.getEntityId());
        }
        
        return validate(untrustedCredential, getCredentialResolver().resolveCredentials(trustedCredentialCriteria));
    }
    
}