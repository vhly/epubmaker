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
    /**
     * toc href
     */
    public String toc;
    /**
     * ItemRef store.
     */
    private Vector<ItemRef> refs;

    public Spine() {
        refs = new Vector<ItemRef>();
    }

    /**
     * dealloc, release all resources.
     */
    public void dealloc() {
        refs.clear();
        refs = null;
        toc = null;
    }

    /**
     * Add a ItemRef obj;
     *
     * @param ref ItemRef
     */
    public void addRef(ItemRef ref) {
        if (ref != null) {
            refs.add(ref);
        }
    }

    /**
     * Get all ItemRef s for item load.
     *
     * @return ItemRef[]
     */
    public ItemRef[] getAllItemRefs() {
        ItemRef[] ret = null;
        if (!refs.isEmpty()) {
            int size = refs.size();
            ret = new ItemRef[size];
            refs.copyInto(ret);
        }
        return ret;
    }

    /**
     * Parse &lt;spine&gt; element.
     *
     * @param espine Element
     */
    public void parse(Element espine) {
        if (espine != null) {
            toc = espine.getAttributeValue(null, "toc");
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

    public String toXML() {
        String ret;
        StringBuffer sb = new StringBuffer();
        sb.append("<spine toc=\"ncx\">");
        for (ItemRef ref : refs) {
            sb.append(ref.toXML());
        }
        sb.append("</spine>");
        ret = sb.toString();
        return ret;
    }
}
