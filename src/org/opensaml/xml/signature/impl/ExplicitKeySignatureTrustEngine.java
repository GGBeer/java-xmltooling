/*
 * Copyright [2007] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.xml.signature.impl;

import org.apache.log4j.Logger;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.SecurityHelper;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.CredentialCriteriaSet;
import org.opensaml.xml.security.credential.CredentialResolver;
import org.opensaml.xml.security.credential.UsageCredentialCriteria;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.security.trust.ExplicitKeyTrustEvaluator;
import org.opensaml.xml.security.trust.TrustedCredentialTrustEngine;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureTrustEngine;

/**
 * An implementation of {@link SignatureTrustEngine} which evaluates the validity and trustworthiness
 * of XML and raw signatures.
 * 
 * <p>Processing is first performed as described in {@link BaseSignatureTrustEngine}. If based on this processing,
 * it is determined that the Signature's KeyInfo is not present or does not contain a resolveable valid (and trusted)
 * signing key, then all trusted credentials obtained by the trusted credential resolver will be used 
 * to attempt to validate the signature.</p>
 */
public class ExplicitKeySignatureTrustEngine extends BaseSignatureTrustEngine<Iterable<Credential>>
    implements TrustedCredentialTrustEngine<Signature>{
    
    /** Class logger. */
    private static Logger log = Logger.getLogger(ExplicitKeySignatureTrustEngine.class);
    
    /** Resolver used for resolving trusted credentials. */
    private CredentialResolver credentialResolver;
    
    /** The external explicit key trust engine to use as a basis for trust in this implementation. */
    private ExplicitKeyTrustEvaluator keyTrust;
    
    /**
     * Constructor.
     *
     * @param resolver credential resolver used to resolve trusted credentials.
     * @param keyInfoResolver KeyInfo credential resolver used to obtain the (advisory) signing credential 
     *          from a Signature's KeyInfo element.
     */
    public ExplicitKeySignatureTrustEngine(CredentialResolver resolver, KeyInfoCredentialResolver keyInfoResolver) {
        super(keyInfoResolver);
        if (resolver == null) {
            throw new IllegalArgumentException("Credential resolver may not be null");
        }
        credentialResolver = resolver;
        
        keyTrust = new ExplicitKeyTrustEvaluator();
    }
    
    /** {@inheritDoc} */
    public CredentialResolver getCredentialResolver() {
        return credentialResolver;
    }

    /** {@inheritDoc} */
    public boolean validate(Signature signature, CriteriaSet trustBasisCriteria) 
            throws SecurityException {
        
        checkParams(signature, trustBasisCriteria);
        CredentialCriteriaSet credentialCriteria = SecurityHelper.getCredentialCriteria(trustBasisCriteria);
        if (! credentialCriteria.contains(UsageCredentialCriteria.class)) {
            credentialCriteria.add( new UsageCredentialCriteria(UsageType.SIGNING));
        }
        
        Iterable<Credential> trustedCredentials = getCredentialResolver().resolve(credentialCriteria);
        
        if (validate(signature, trustedCredentials)) {
            return true;
        }
        
        // If the credentials extracted from Signature's KeyInfo did not verify the
        // signature and/or establish trust, as a fall back attempt to verify the signature with
        // the trusted credentials directly.
        for (Credential trustedCredential : trustedCredentials) {
            if (verifySignature(signature, trustedCredential)) {
                log.debug("Successfully verified signature using resolved trusted credential");
                return true;
            }
        }
        log.error("Failed to verify signature using either KeyInfo-derived or directly trusted credentials");
        return false;
    }
    
    /** {@inheritDoc} */
    protected boolean evaluateTrust(Credential untrustedCredential, Iterable<Credential> trustedCredentials) 
            throws SecurityException {
        
        return keyTrust.validate(untrustedCredential, trustedCredentials);
    }

}
