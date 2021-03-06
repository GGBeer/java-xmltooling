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

/**
 * 
 */
package org.opensaml.xml.mock;

import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.Validator;

/**
 * A very simple validator that ensures an XMLObject is of type {@link org.opensaml.xml.mock.SimpleXMLObject}.
 */
public class SimpleXMLObjectValidator implements Validator<SimpleXMLObject> {
    
    /**
     * Constructor
     */
    public SimpleXMLObjectValidator(){
        
    }

    /** {@inheritDoc} */
    public void validate(SimpleXMLObject xmlObject) throws ValidationException {
        if(xmlObject instanceof SimpleXMLObject){
            return;
        }else{
            throw new ValidationException("XMLObject not an instance of SimpleXMLObject as it should be");
        }
    }
}