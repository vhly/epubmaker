package com.vhly.epubmaker.epub;

import net.dratek.browser.xml.XMLUtil;
import org.kxml2_orig.kdom.Element;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-6
 * Email: vhly@163.com
 */
public class Manifest {
    /**
     * All items store.
     */
    private Hashtable<String, Item> items;

    public Manifest() {
        items = new Hashtable<String, Item>();
    }

    /**
     * Add a parsed Item object.
     *
     * @param item Item
     */
    public void addItem(Item item) {
        if (item != null) {
            String id = item.id;
            if (id != null) {
                items.put(id, item);
            }
        }
    }

    /**
     * Get Item by id<br/>
     * id is parsed by parse method
     *
     * @param id Item's id.
     * @return Item
     */
    public Item getItem(String id) {
        Item ret = null;
        if (id != null) {
            ret = items.get(id);
        }
        return ret;
    }

    /**
     * Dealloc, release all resources.
     */
    public void dealloc() {
        items.clear();
        items = null;
    }

    /**
     * Parse &lt;manifest&gt; element.
     *
     * @param emanifest Element
     */
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

    /**
     * Get Items count.
     *
     * @return int
     */
    public int size() {
        return items.size();
    }

    /**
     * Get Item s by MediaType
     *
     * @param type MediaType
     * @return Item[]
     */
    public Item[] getItemsByMediaType(MediaType type) {
        Item[] ret = null;
        if (!items.isEmpty() && type != null) {
            Vector<Item> v = new Vector<Item>();
            Collection<Item> values = items.values();
            Iterator<Item> iterator = values.iterator();
            Item item;
            String st = type.toString();
            while (iterator.hasNext()) {
                item = iterator.next();
                if (st.equals(item.mediatype)) {
                    v.add(item);
                }
            }
            if (!v.isEmpty()) {
                int len = v.size();
                ret = new Item[len];
                v.copyInto(ret);
            }
            v.clear();
        }
        return ret;
    }

    public String toXML() {
        String ret;
        StringBuffer sb = new StringBuffer();
        sb.append("<manifest>");
        Collection<Item> values = items.values();
        Iterator<Item> iterator = values.iterator();
        Item it;
        while (iterator.hasNext()) {
            it = iterator.next();
            sb.append(it.toXML());
        }
        sb.append("</manifest>");
        ret = sb.toString();
        return ret;
    }

    public Item findByType(String type) {
        Item ret = null;
        if(type != null){
            Collection<Item> values = items.values();
            Iterator<Item> iterator = values.iterator();
            while (iterator.hasNext()){
                Item it = iterator.next();
                if(type.equals(it.mediatype)){
                    ret = it;
                    break;
                }
            }
        }
        return ret;
    }
}
