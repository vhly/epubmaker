package com.vhly.epubmaker.epub;

import net.dratek.browser.xml.XMLUtil;
import org.kxml2_orig.kdom.Element;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-13
 * Email: vhly@163.com
 */
public class Guide {
    private Vector<GReference> references;

    public Guide() {
        references = new Vector<GReference>();
    }

    public String toXML() {
        String ret;
        StringBuffer sb = new StringBuffer();
        sb.append("<guide>");
        for (GReference ref : references) {
            sb.append(ref.toXML());
        }
        sb.append("</guide>");
        ret = sb.toString();
        return ret;
    }

    public void parse(Element element) {
        if (element != null) {
            Element[] elements = XMLUtil.getElementsByName(element, "reference");
            if (elements != null) {
                for (Element el : elements) {
                    GReference ref = new GReference();
                    ref.href = el.getAttributeValue(null, "href");
                    ref.title = el.getAttributeValue(null, "title");
                    ref.type = el.getAttributeValue(null, "type");
                    references.add(ref);
                }
            }
        }
    }
}
