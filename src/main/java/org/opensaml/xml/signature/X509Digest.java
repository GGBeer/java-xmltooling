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

package org.opensaml.xml.signature;


import javax.xml.namespace.QName;

import org.opensaml.xml.schema.XSBase64Binary;
import org.opensaml.xml.util.XMLConstants;

/**
 * XMLObject representing XML Digital Signature 1.1 X509Digest element.
 */
public interface X509Digest extends XSBase64Binary {
    
    /** Element local name. */
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "X509Digest";
    
    /** Default element name. */
    public static final QName DEFAULT_ELEMENT_NAME =
            new QName(XMLConstants.XMLSIG11_NS, DEFAULT_ELEMENT_LOCAL_NAME, XMLConstants.XMLSIG11_PREFIX);
    
    /** Local name of the XSI type. */
    public static final String TYPE_LOCAL_NAME = "X509DigestType"; 
        
    /** QName of the XSI type. */
    public static final QName TYPE_NAME =
            new QName(XMLConstants.XMLSIG11_NS, TYPE_LOCAL_NAME, XMLConstants.XMLSIG11_PREFIX);
    
    /** Algorithm attribute name. */
    public static final String ALGORITHM_ATTRIB_NAME = "Algorithm";
    
    /**
     * Get the Algorithm URI attribute value.
     *  
     * @return the Algorithm URI attribute value
     */
    public String getAlgorithm();
    
    /**
     * 
     * Set the Algorithm URI attribute value.
     *  
     * @param newAlgorithm the new Algorithm URI attribute value
     */
    public void setAlgorithm(String newAlgorithm);
    
}