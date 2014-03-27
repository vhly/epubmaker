package com.vhly.epubmaker.epub;

import org.kxml2_orig.kdom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-13
 * Email: vhly@163.com
 */
public class ItemRef {
    public String idref;
    public boolean linear;

    public void parse(Element el) {
        if (el != null) {
            idref = el.getAttributeValue(null, "idref");
            String l = el.getAttributeValue(null, "linear");
            if (l != null) {
                linear = l.equals("yes");
            }
        }
    }

    public String toXML() {
        String ret;
        StringBuffer sb = new StringBuffer();
        sb.append("<itemref idref=\"");
        sb.append(idref);
        sb.append("\" ");
        if (linear) {
            sb.append("linear=\"yes\" ");
        }
        sb.append("/>");
        ret = sb.toString();
        return ret;
    }
}
