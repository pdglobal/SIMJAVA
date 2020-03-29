/*
 * ====================================================================
 * ======== The Apache Software License, Version 1.1
 * ==================
 * ==========================================================
 * Copyright (C) 2002 The Apache Software Foundation. All rights
 * reserved. Redistribution and use in source and binary forms, with
 * or without modifica- tion, are permitted provided that the
 * following conditions are met: 1. Redistributions of source code
 * must retain the above copyright notice, this list of conditions and
 * the following disclaimer. 2. Redistributions in binary form must
 * reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other
 * materials provided with the distribution. 3. The end-user
 * documentation included with the redistribution, if any, must
 * include the following acknowledgment: "This product includes
 * software developed by SuperBonBon Industries
 * (http://www.sbbi.net/)." Alternately, this acknowledgment may
 * appear in the software itself, if and wherever such third-party
 * acknowledgments normally appear. 4. The names "UPNPLib" and
 * "SuperBonBon Industries" must not be used to endorse or promote
 * products derived from this software without prior written
 * permission. For written permission, please contact info@sbbi.net.
 * 5. Products derived from this software may not be called
 * "SuperBonBon Industries", nor may "SBBI" appear in their name,
 * without prior written permission of SuperBonBon Industries. THIS
 * SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR ITS
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLU- DING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE. This software consists of voluntary contributions made
 * by many individuals on behalf of SuperBonBon Industries. For more
 * information on SuperBonBon Industries, please see
 * <http://www.sbbi.net/>.
 */
package net.sbbi.upnp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Handy stuff for dealing with XML
 *
 * @author ryanm
 */
public class XMLUtil
{

    /**
     * {@link XPath} instance
     */
    public static final XPath xpath = XPathFactory.newInstance ().newXPath ();

    private static final char buggyChar = (char) 0;

    private static final DocumentBuilder builder;

    static {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
        factory.setNamespaceAware (false);
        DocumentBuilder b = null;
        try {
            b = factory.newDocumentBuilder ();
        } catch (ParserConfigurationException e) {
            e.printStackTrace ();
        }
        builder = b;
    }

    /**
     * @param url
     *
     * @return the xml string at that url
     */
    public static String getXMLString (URL url) {
        try {
            InputStream in = url.openStream ();

            StringBuilder xml = new StringBuilder ();
            byte[] buffer = new byte[512];
            int readen = 0;
            while ((readen = in.read (buffer)) != -1) {
                xml.append (new String (buffer, 0, readen));
            }

            String doc = xml.toString ();

            if (doc.indexOf (buggyChar) != -1) {
                doc = doc.replace (buggyChar, ' ');
            }

            return doc;
        } catch (IOException e) {
            e.printStackTrace ();
        }

        return null;
    }

    /**
     * Fetches the xml, fixes any wonky characters in it
     *
     * @param url
     *
     * @return The xml {@link Document}
     */
    public static Document getXML (URL url) {
        try {
            String doc = getXMLString (url);
            ByteArrayInputStream in2 = new ByteArrayInputStream (doc.getBytes ());

            return builder.parse (in2);
        } catch (SAXException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }

        return null;
    }

}
