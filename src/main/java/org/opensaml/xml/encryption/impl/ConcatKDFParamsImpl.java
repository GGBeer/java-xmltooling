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

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.ConcatKDFParams;
import org.opensaml.xml.signature.DigestMethod;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Concrete implementation of {@link org.opensaml.xml.encryption.ConcatKDFParams}.
 */
public class ConcatKDFParamsImpl extends AbstractValidatingXMLObject implements ConcatKDFParams
{
  /**
   * AlgorithmID attribute (hexBinary).
   */
  private String algorithmID;

  /**
   * PartyUInfo attribute (hexBinary).
   */
  private String partyUInfo;

  /**
   * PartyVInfo attribute (hexBinary).
   */
  private String partyVInfo;

  /**
   * SuppPubInfo attribute (hexBinary).
   */
  private String suppPubInfo;

  /**
   * SuppPrivInfo attribute (hexBinary).
   */
  private String suppPrivInfo;


  /** DigestMethod child element value. */
  private DigestMethod digestMethod;

  /**
   * List of wildcard &lt;any&gt; XMLObject children.
   */
  private IndexedXMLObjectChildrenList xmlChildren;

  /**
   * Constructor.
   *
   * @param namespaceURI     namespace URI
   * @param elementLocalName element local name
   * @param namespacePrefix  namespace prefix
   */
  protected ConcatKDFParamsImpl(String namespaceURI, String elementLocalName, String namespacePrefix)
  {
    super(namespaceURI, elementLocalName, namespacePrefix);
    xmlChildren = new IndexedXMLObjectChildrenList(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getAlgorithmID()
  {
    return this.algorithmID;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAlgorithmID(String newAlgorithmID)
  {
    this.algorithmID = prepareForAssignment(this.algorithmID, newAlgorithmID);
  }

  @Override
  public String getPartyUInfo()
  {
    return this.partyUInfo;
  }

  @Override
  public void setPartyUInfo(String newPartyUInfo)
  {
    this.partyUInfo = prepareForAssignment(this.partyUInfo, newPartyUInfo);

  }

  @Override
  public String getPartyVInfo()
  {
    return this.partyVInfo;
  }

  @Override
  public void setPartyVInfo(String newPartyVInfo)
  {
    this.partyVInfo = prepareForAssignment(this.partyVInfo, newPartyVInfo);

  }

  @Override
  public String getSuppPubInfo()
  {
    return this.suppPubInfo;
  }

  @Override
  public void setSuppPubInfo(String newSuppPubInfo)
  {
    this.suppPubInfo = prepareForAssignment(this.suppPubInfo, newSuppPubInfo);
  }

  @Override
  public String getSuppPrivInfo()
  {
    return this.suppPrivInfo;
  }

  @Override
  public void setSuppPrivInfo(String newSuppPrivInfo)
  {
    this.suppPrivInfo = prepareForAssignment(this.suppPrivInfo, newSuppPrivInfo);
  }

  @Override
  public DigestMethod getDigestMethod()
  {
    return this.digestMethod;
  }

  @Override
  public void setDigestMethod(DigestMethod newDigestMethod)
  {
    this.digestMethod = prepareForAssignment(this.digestMethod, newDigestMethod);
  }

  /**
   * {@inheritDoc}
   */
  public List<XMLObject> getUnknownXMLObjects()
  {
    return (List<XMLObject>) this.xmlChildren;
  }

  /**
   * {@inheritDoc}
   */
  public List<XMLObject> getUnknownXMLObjects(QName typeOrName)
  {
    return (List<XMLObject>) this.xmlChildren.subList(typeOrName);
  }

  /**
   * {@inheritDoc}
   */
  public List<XMLObject> getOrderedChildren()
  {
    ArrayList<XMLObject> children = new ArrayList<XMLObject>();

    if (digestMethod != null) {
      children.add(digestMethod);
    }

    children.addAll(xmlChildren);

    if(children.size() == 0)
    {
      return null;
    }

    return Collections.unmodifiableList(children);
  }

}
