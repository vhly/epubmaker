package com.vhly.epubmaker.epub;

import org.kxml2_orig.kdom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-13
 * Email: vhly@163.com
 */
public class Item {
    public String id;
    public String href;
    public String mediatype;

    public Item() {
    }

    public Item(String id, String href) {
        this(id,href,MediaType.XHTML.toString());
    }

    public Item(String id, String href, String mediatype) {
        this.id = id;
        this.href = href;
        this.mediatype = mediatype;
    }

    public void parse(Element el) {
        if (el != null) {
            id = el.getAttributeValue(null, "id");
            href = el.getAttributeValue(null, "href");
            mediatype = el.getAttributeValue(null, "media-type");
        }
    }

    public String toXML() {
        String ret;
        StringBuffer sb = new StringBuffer();
        sb.append("<item href=\"");
        sb.append(href);
        sb.append("\" id=\"");
        sb.append(id);
        sb.append("\" media-type=\"");
        sb.append(mediatype);
        sb.append("\"/>\n");
        ret = sb.toString();
        return ret;
    }
}
