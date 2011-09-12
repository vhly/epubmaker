package com.vhly.epubmaker.epub;

import net.dratek.browser.xml.XMLUtil;
import org.kxml2_orig.kdom.Element;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-6
 * Email: vhly@163.com
 */
public class Manifest {
    private Hashtable<String, Item> items;

    public Manifest() {
        items = new Hashtable<String, Item>();
    }

    public void addItem(Item item) {
        if (item != null) {
            String id = item.id;
            if (id != null) {
                items.put(id, item);
            }
        }
    }

    public Item getItem(String id){
        Item ret = null;
        if(id != null){
           ret = items.get(id);
        }
        return ret;
    }

    public void dealloc() {
        items.clear();
        items = null;
    }

    public void parse(Element emanifest) {
        if (emanifest != null) {
            Element[] items = XMLUtil.getElementsByName(emanifest, "item");
            if (items != null) {
                int len = items.length;
                Element el;
                Item it;
                for (int i = 0; i < len; i++) {
                    it = new Item();
                    el = items[i];
                    it.parse(el);
                    addItem(it);
                }
            }
        }
    }
}
