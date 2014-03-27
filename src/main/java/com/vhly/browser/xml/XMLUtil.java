package com.vhly.browser.xml;

import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-5-4
 */
public final class XMLUtil {
    /**
     * Get Element's children Text content.<br/>
     * All content will append. If element has other type node,<br/>
     * will throw Exception
     *
     * @param element Element
     * @return String
     * @throws IllegalArgumentException If element has other type node.
     */
    public static String getElementTextContent(Element element) throws IllegalArgumentException {
        String ret = null;
        boolean valid = true;
        if (element != null) {
            int cc = element.getChildCount();
            if (cc > 0) {
                StringBuffer sb = new StringBuffer();
                Object child;
                for (int i = 0; i < cc; i++) {
                    child = element.getChild(i);
                    if (child instanceof String) {
                        sb.append((String) child);
                    }else {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    ret = sb.toString();
                }
                //noinspection UnusedAssignment
                sb = null;
            }
        }
        return ret;
    }

    /**
     * Get All directly children element by name
     *
     * @param parent Element parent
     * @param ename  String name to find
     * @return Element[]
     */
    public static Element[] getElementsByName(Element parent, String ename) {
        Element[] ret = null;
        if (parent != null && ename != null) {
            int childCount = parent.getChildCount();
            if (childCount > 0) {
//                Vector<Element> els = new Vector<Element>();
                Vector els = new Vector();
                Object child;
                Element el;
                String en;
                for (int i = 0; i < childCount; i++) {
                    child = parent.getChild(i);
                    if (child instanceof Element) {
                        el = (Element) child;
                        en = el.getName();
                        if (ename.equals(en)) {
                            els.addElement(el);
                        }
                    }
                }
                if (!els.isEmpty()) {
                    ret = new Element[els.size()];
                    els.copyInto(ret);
                }
                //noinspection UnusedAssignment
                els = null;
            }
        }
        return ret;
    }

    /**
     * Create Element with name and append to parent element
     *
     * @param parent Element parent
     * @param name   Element name
     * @return created Element
     */
    public static Element createAndAppend(Element parent, String name) {
        Element ret = null;
        if (parent != null && name != null) {
            ret = parent.createElement(null, name);
            parent.addChild(Node.ELEMENT, ret);
        }
        return ret;
    }

    /**
     * Get all child string,
     *
     * @param parent Element
     * @return String
     */
    public static String getAllText(Element parent) {
        String ret = null;
        if (parent != null) {
            int count = parent.getChildCount();
            if (count > 0) {
                StringBuffer sb = new StringBuffer();
                Object child;
                for (int i = 0; i < count; i++) {
                    child = parent.getChild(i);
                    if (child instanceof String) {
                        sb.append((String) child);
                    }
                }
                ret = sb.toString();
            }
        }
        return ret;
    }

    /**
     * Get First element which has eleName in parent.
     *
     * @param parent  Element
     * @param eleName find name
     * @return Element or null
     */
    public static Element getElementByName(Element parent, String eleName) {
        Element ret = null;
        if (parent != null && eleName != null) {
            int count = parent.getChildCount();
            String ename;
            Object child;
            for (int i = 0; i < count; i++) {
                child = parent.getChild(i);
                if(child instanceof Element){
                    Element el = (Element) child;
                    ename = el.getName();
                    if(eleName.equals(ename)){
                        ret = el;
                        break;
                    }
                }
            }
        }
        return ret;
    }
}
