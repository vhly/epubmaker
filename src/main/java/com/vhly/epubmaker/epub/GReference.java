package com.vhly.epubmaker.epub;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-12-2
 * Email: vhly@163.com
 */
public class GReference {
    public String title;
    public String href;
    public String type;

    public String toXML() {
        String ret;
        StringBuffer sb = new StringBuffer();
        sb.append("<reference href=\"").append(href).append("\" type=\"").append(type).append("\" title=\"").append(title).append("\"/>");
        ret = sb.toString();
        return ret;
    }
}
