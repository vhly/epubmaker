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

    public void parse(Element el) {
        if(el != null){
            id = el.getAttributeValue(null,"id");
            href = el.getAttributeValue(null,"href");
            mediatype = el.getAttributeValue(null,"media-type");
        }
    }
}