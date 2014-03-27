package com.vhly.epubmaker;

import junit.framework.TestCase;
import org.w3c.dom.Element;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by vhly[FR] on 14-3-27.
 * Project : epubmaker
 *
 * @author vhly[FR]
 *         Email: vhly@163.com
 */
public class SignatureUtilTest extends TestCase {

    private PublicKey publicKey;
    private PrivateKey privateKey;
    private String xml;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair keyPair = kpg.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();

        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<books>\n\n\n<book><title> T1  </title></book></books>";

    }

    public void testSignXML() {


        Element element = SignatureUtil.signXML(xml, privateKey, publicKey);
        TransformerFactory factory = TransformerFactory.newInstance();
        try {
            Transformer transformer = factory.newTransformer();
            StreamResult outputTarget = new StreamResult(System.out);
            DOMSource xmlSource = new DOMSource(element);
            transformer.transform(xmlSource, outputTarget);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
