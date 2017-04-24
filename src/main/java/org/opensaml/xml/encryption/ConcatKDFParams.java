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
import org.opensaml.xml.signature.DigestMethod;
import org.opensaml.xml.util.XMLConstants;
import org.opensaml.xml.validation.ValidatingXMLObject;

import javax.xml.namespace.QName;

/**
 * XMLObject representing XML Encryption 1.1, http://www.w3.org/2009/xmlenc11#ConcatKDFParams element.
 */
public interface ConcatKDFParams extends ValidatingXMLObject, ElementExtensibleXMLObject {
    
    /** Element local name. */
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "ConcatKDFParams";
    
    /** Default element name. */
    public static final QName DEFAULT_ELEMENT_NAME = new QName(XMLConstants.XMLENC11_NS, DEFAULT_ELEMENT_LOCAL_NAME,
            XMLConstants.XMLENC11_PREFIX);
    
    /** Local name of the XSI type. */
    public static final String TYPE_LOCAL_NAME = "ConcatKDFParamsType";
        
    /** QName of the XSI type. */
    public static final QName TYPE_NAME = new QName(XMLConstants.XMLENC11_NS, TYPE_LOCAL_NAME,
            XMLConstants.XMLENC11_PREFIX);
    

    /** Attributes */
    /** AlgorithmID attribute name (hexBinary). */
    public static final String ALGORITHM_ID_ATTRIBUTE_NAME = "AlgorithmID";
    
    /** PartyUInfo attribute name (hexBinary). */
    public static final String PARTY_U_INFO_ATTRIBUTE_NAME = "PartyUInfo";
    
    /** PartyVInfo attribute name (hexBinary). */
    public static final String PARTY_V_INFO_ATTRIBUTE_NAME = "PartyVInfo";
    
    /** SuppPubInfo attribute name (hexBinary). */
    public static final String SUPP_PUB_INFO_ATTRIBUTE_NAME = "SuppPubInfo";
    
    /** SuppPrivInfo attribute name (hexBinary). */
    public static final String SUPP_PRIV_INFO_ATTRIBUTE_NAME = "SuppPrivInfo";
    
   
    /**
     * Gets the algorithmID attribute used in this ConcatKDFParams.
     * 
     * @return the AlgorithmID attribute (hex)string
     */
    public String getAlgorithmID();
 
    /**
     * Sets the algorithmID attribute used in this ConcatKDFParams.
     * 
     * @param newAlgorithmID the new AlgorithmID attribute (hex)string
     */
    public void setAlgorithmID(String newAlgorithmID);


  /**
   * Gets the algorithmID attribute used in this ConcatKDFParams.
   *
   * @return the AlgorithmID attribute (hex)string
   */
  public String getPartyUInfo();

  /**
   * Sets the PartyUInfo attribute used in this ConcatKDFParams.
   *
   * @param newPartyUInfo the new PartyUInfo attribute (hex)string
   */
  public void setPartyUInfo(String newPartyUInfo);

  /**
   * Gets the PartyVInfo attribute used in this ConcatKDFParams.
   *
   * @return the PartyVInfo attribute (hex)string
   */
  public String getPartyVInfo();

  /**
   * Sets the PartyVInfo attribute used in this ConcatKDFParams.
   *
   * @param newPartyVInfo the new PartyVInfo attribute (hex)string
   */
  public void setPartyVInfo(String newPartyVInfo);

  /**
   * Gets the SuppPubInfo attribute used in this ConcatKDFParams.
   *
   * @return the SuppPubInfo attribute (hex)string
   */
  public String getSuppPubInfo();

  /**
   * Sets the SuppPubInfo attribute used in this ConcatKDFParams.
   *
   * @param newSuppPubInfo the new SuppPubInfo attribute (hex)string
   */
  public void setSuppPubInfo(String newSuppPubInfo);

  /**
   * Gets the SuppPrivInfo attribute used in this ConcatKDFParams.
   *
   * @return the SuppPrivInfo attribute (hex)string
   */
  public String getSuppPrivInfo();

  /**
   * Sets the SuppPrivInfo attribute used in this ConcatKDFParams.
   *
   * @param newSuppPrivInfo the new SuppPrivInfo attribute (hex)string
   */
  public void setSuppPrivInfo(String newSuppPrivInfo);

  /**
   * Gets the DigestMethod child element.
   *
   * @return the DigestMethod child element
   */
  public DigestMethod getDigestMethod(DigestMethod newDigestMethod);

  /**
   * Sets the DigestMethod child element.
   *
   * @param newDigestMethod the new DigestMethod child element
   */
  public void setDigestMethod(DigestMethod newDigestMethod);

}
