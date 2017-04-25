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

package org.opensaml.xml.encryption.validator;

import org.opensaml.xml.encryption.ConcatKDFParams;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.Validator;

/**
 * Checks {@link org.opensaml.xml.encryption.ConcatKDFParams} for Schema compliance. 
 */
public class ConcatKDFParamsSchemaValidator implements Validator<ConcatKDFParams> {

    /** {@inheritDoc} */
    public void validate(ConcatKDFParams xmlObject) throws ValidationException {
        validateAlgorithm(xmlObject);
    }

    /**
     * Validate the algorithm URI.
     * 
     * @param xmlObject the object to validate
     * @throws ValidationException  thrown if the object is invalid
     */
    protected void validateAlgorithm(ConcatKDFParams xmlObject) throws ValidationException
    {
        // The ds:DigestMethod element identifies the digest algorithm used by the KDF. Compliant implementations MUST
        // support SHA-256 and SHA-1 (support for SHA-1 is present only for backwards-compatibility reasons).
        // Support for SHA-384 and SHA-512 is OPTIONAL.
        if (xmlObject.getDigestMethod() == null)
        {
          throw new ValidationException("ConcatKDFParams did not contain a DigestMethod child");
        }

        // The AlgorithmID, PartyUInfo, PartyVInfo, SuppPubInfo and SuppPrivInfo attributes are as defined in [SP800-56A].
        // Their presence is optional but AlgorithmID, PartyVInfo and PartyUInfo MUST be present for applications that
        // need to comply with [SP800-56A].
        // e.g. use AlgorithmID="00" PartyUInfo="" PartyVInfo=""
    }

}
