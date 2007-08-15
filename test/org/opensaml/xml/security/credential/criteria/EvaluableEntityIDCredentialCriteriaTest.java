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

package org.opensaml.xml.security.credential.criteria;

import junit.framework.TestCase;

import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.BasicCredential;
import org.opensaml.xml.security.criteria.EntityIDCriteria;

/**
 *
 */
public class EvaluableEntityIDCredentialCriteriaTest extends TestCase {
    
    private BasicCredential credential;
    private String entityID;
    private EntityIDCriteria criteria;
    
    public EvaluableEntityIDCredentialCriteriaTest() {
        entityID = "someEntityID";
    }

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        credential = new BasicCredential();
        credential.setEntityId(entityID);
        
        criteria = new EntityIDCriteria(entityID);
    }
    
    public void testSatifsy() {
        EvaluableEntityIDCredentialCriteria evalCrit = new EvaluableEntityIDCredentialCriteria(criteria);
        assertTrue("Credential should have matched the evaluable criteria", evalCrit.evaluate(credential));
    }

    public void testNotSatisfy() {
        criteria.setEntityID("OTHER");
        EvaluableEntityIDCredentialCriteria evalCrit = new EvaluableEntityIDCredentialCriteria(criteria);
        assertFalse("Credential should have matched the evaluable criteria", evalCrit.evaluate(credential));
    }
    
    public void testCanNotEvaluate() {
        credential.setEntityId(null);
        EvaluableEntityIDCredentialCriteria evalCrit = new EvaluableEntityIDCredentialCriteria(criteria);
        assertNull("Credential should have matched the evaluable criteria", evalCrit.evaluate(credential));
    }
    
    public void testRegistry() throws SecurityException {
        if (! EvaluableCredentialCriteriaRegistry.isInitialized()) {
            EvaluableCredentialCriteriaRegistry.init();
        }
        
        EvaluableCredentialCriteria evalCrit = EvaluableCredentialCriteriaRegistry.getEvaluator(criteria);
        assertNotNull("Evaluable criteria was unavailable from the registry", evalCrit);
        assertTrue("Credential should have matched the evaluable criteria", evalCrit.evaluate(credential));
    }
}
