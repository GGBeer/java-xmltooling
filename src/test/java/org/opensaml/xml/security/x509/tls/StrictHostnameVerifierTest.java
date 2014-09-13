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

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;

import org.opensaml.xml.security.x509.X509Util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class StrictHostnameVerifierTest extends TestCase {
    
    private HostnameVerifier verifier;
    
    public void setUp() {
        verifier = new StrictHostnameVerifier();
    }
    
    public void testSuccessDN() {
        String host = "foo.example.org";
        SSLSession sslSession = buildSSLSession(host, "cn=foo.example.org, O=SomeOrg");
        Assert.assertTrue(verifier.verify(host, sslSession));
    }

    public void testFailureDN() {
        String host = "foo.example.org";
        SSLSession sslSession = buildSSLSession(host, "cn=notfoo.example.org, O=SomeOrg");
        Assert.assertFalse(verifier.verify(host, sslSession));
    }

    public void testSuccessAltnames() {
        String host = "foo.example.org";
        SSLSession sslSession = buildSSLSession(host, "cn=notfoo.example.org, O=SomeOrg", "www.example.org", host);
        Assert.assertTrue(verifier.verify(host, sslSession));
    }

    public void testFailureAltnames() {
        String host = "foo.example.org";
        SSLSession sslSession = buildSSLSession(host, "cn=notfoo.example.org, O=SomeOrg", "www.example.org");
        Assert.assertFalse(verifier.verify(host, sslSession));
    }
    
    public void testMaliciousDNs() {
        String host = "www.apache.org";
        SSLSession sslSession = buildSSLSession(host, "cn=foo.example.org, o=foo \\,cn=www.apache.org");
        Assert.assertFalse(verifier.verify(host, sslSession));
        
        sslSession = buildSSLSession(host, "cn=foo.example.org, o=cn=www.apache.org\\, foo");
        Assert.assertFalse(verifier.verify(host, sslSession));
    }

    /**
     * @param string
     * @return
     */
    private SSLSession buildSSLSession(String host, String subjectDN, String ... altNames) {
        X500Principal subject = new X500Principal(subjectDN);
        Collection<List<?>> subjectAltNames = null;
        
        if (altNames.length > 0) {
            subjectAltNames = new LinkedList<List<?>>();
            for (String altName : altNames) {
                List<Object> altNameEntry = new ArrayList<Object>(2);
                altNameEntry.add(0, X509Util.DNS_ALT_NAME);
                altNameEntry.add(1, altName);
                subjectAltNames.add(altNameEntry);
            }
        }
        
        X509Certificate cert = new MockX509Certificate(subject, subjectAltNames);
        return new MockSSLSession(cert, host);
    }

}
