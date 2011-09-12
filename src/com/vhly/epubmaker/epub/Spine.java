package com.vhly.epubmaker.epub;

import net.dratek.browser.xml.XMLUtil;
import org.kxml2_orig.kdom.Element;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-6
 * Email: vhly@163.com
 */
public class Spine {
    public String toc;

    private Vector<ItemRef> refs;

    public Spine() {
        refs = new Vector<ItemRef>();
    }

    public void dealloc(){
        refs.clear();
        refs = null;
        toc = null;
    }

    public void addRef(ItemRef ref){
        if(ref != null){
            refs.add(ref);
        }
    }

    public void parse(Element espine) {
        if(espine != null){
            toc = espine.getAttributeValue(null,"toc");
            Element[] items = XMLUtil.getElementsByName(espine, "itemref");
            if (items != null) {
                int len = items.length;
                Element el;
                ItemRef itr;
                for (int i = 0; i < len; i++) {
                    itr = new ItemRef();
                    el = items[i];
                    itr.parse(el);
                    addRef(itr);
                }
            }
        }
    }
}
