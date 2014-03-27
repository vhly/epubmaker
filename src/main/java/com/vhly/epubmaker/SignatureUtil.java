package com.vhly.epubmaker;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Collections;

/**
 * Created by vhly[FR] on 14-3-27.
 * Project : epubmaker
 *
 * @author vhly[FR]
 *         Email: vhly@163.com
 */
public class SignatureUtil {

    public static Element signXML(String xml, PrivateKey privateKey, PublicKey publicKey) {
        Element ret = null;
        if (xml != null && privateKey != null && publicKey != null) {
            ByteArrayInputStream bin = null;
            try {
                byte[] bytes = xml.getBytes("UTF-8");
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                bin = new ByteArrayInputStream(bytes);
                Document document = builder.parse(bin);
                ret = sign(document.getDocumentElement(), privateKey, publicKey);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                StreamUtil.close(bin);
                bin = null;
            }

        }
        return ret;
    }

    public static Element sign(Element el, PrivateKey privateKey, PublicKey publicKey) {
        Element ret = null;
        try {
            // Create a DOM XMLSignatureFactory that will be used to
// generate the enveloped signature.
            XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

// Create a Reference to the enveloped document (in this case,
// you are signing the whole document, so a URI of "" signifies
// that, and also specify the SHA1 digest algorithm and
// the ENVELOPED Transform.
            Reference ref = fac.newReference(
                    "",
                    fac.newDigestMethod(DigestMethod.SHA1, null),
                    Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)),
                    null,
                    null);

// Create the SignedInfo.
            SignedInfo si = fac.newSignedInfo(
                    fac.newCanonicalizationMethod(
                            CanonicalizationMethod.INCLUSIVE,
                            (C14NMethodParameterSpec) null),
                    fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
                    Collections.singletonList(ref));

            KeyInfoFactory keyInfoFactory = fac.getKeyInfoFactory();
            KeyInfo keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(keyInfoFactory.newKeyValue(publicKey)));

            XMLSignature xmlSignature = fac.newXMLSignature(si, keyInfo);
            xmlSignature.sign(new DOMSignContext(privateKey, el));

            NodeList nl =
                    el.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
            if (nl.getLength() == 0) {
                throw new Exception("Cannot find Signature element");
            }
//            ret = (Element) nl.item(0);
            ret = el;
//            DOMValidateContext validateContext = new DOMValidateContext(publicKey, ret);
//            XMLSignature xmlSignature1 = fac.unmarshalXMLSignature(validateContext);
//            boolean validate = xmlSignature1.validate(validateContext);
//            System.out.println("validate = " + validate);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ret;
    }
}
