package com.vhly.browser.xml;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-4-29
 */

import com.vhly.browser.util.StringUtil;
import org.kxml2.kdom.Document;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

/**
 * XPath tools
 * Query element, Element, Node.
 */
public final class XPathUtil {

    /**
     * Get node's name, Use Object parameter for kXML,<br/>
     * If in W3C DOM system, all content is Node, <br/>
     * but in kXML some child is String object.<br/>
     * So if node is a String object, this method return #TEXT
     *
     * @param node Object, Element or children.
     * @return String node's name.
     */
    public static String getNodeName(Object node) {
        String ret = null;
        if (node != null) {
            if (node instanceof String) {
                ret = "#TEXT";
            } else if (node instanceof Element) {
                Element el = (Element) node;
                ret = el.getName();
            } else if (node instanceof Document) {
                Document doc = (Document) node;
                ret = doc.getName();
            }
        }
        return ret;
    }

    /**
     * Get Node's root node. some times is Document<br/>
     * When call this method, will return Document obj,<br/>
     * If you want get Root Element, call Document's method.
     *
     * @param nd Node
     * @return Node
     */
    public static Node getRoot(Node nd) {
        Node ret = null;
        while (nd != null) {
            if (nd instanceof Element) {
                ret = ((Element) nd).getParent();
            } else if (nd instanceof Document) {
                ret = nd;
                break;
            } else {
                throw new RuntimeException("In this implement, can't support other type object.");
            }
            nd = ret;
        }
        return ret;
    }

    /**
     * Query Element with xpath<br/>
     * In this implement, we only support queryPath startsWith /, it mean absolue path<br/>
     * for exampe:<br/>
     * <code>
     * <pre>
     *     &lt;bookstore&gt;
     *        &lt;book&gt;
     *          &lt;title lang="eng"&gt;Harry Potter&lt;/title&gt;
     *          &lt;price&gt;29.99&lt;/price&gt;
     *        &lt;/book&gt;
     *     &lt;/bookstore&gt;
     * </pre>
     * </code><br/>
     * If you want get title elements, then queryPath is "/bookstore/book/title" and return all title elements<br/>
     * <font color="red">Support query format:</font><br/>
     * <ol>
     * <li>/node1/node2/node3 get first node3 with first node2</li>
     * <li>/node1/node2/@attribute get first node2's attribute</li>
     * <li>/node1/node2/#text get first node2's Text content </li>
     * <li>/node1/node2/node3[n] get first node2's the nth node3 element.</li>
     * </ol><br/>
     * <font color="red">ChangeList:</font><br/>
     * <ol>
     * <li>Refactor this method, call query with element, first check document info, and call query element for code size shake</li>
     * </ol>
     *
     * @param dom       Element
     * @param queryPath String xpath query string
     * @return Object found result, Element or String or Element[]
     */
    public static Object query(Document dom, String queryPath) {
        Object ret = null;
        Element root;
        if (queryPath != null && dom != null) {
            root = dom.getRootElement();
            int len = queryPath.length();
            if (len > 1) {
                char ch = queryPath.charAt(0);
                if (ch == '/') {
                    queryPath = queryPath.substring(1);
                }
            }
            int index = queryPath.indexOf('/');
            if(index != -1){
                String rn = root.getName();
                String qrn = queryPath.substring(0,index);
                if(qrn.equals(rn)){
                    queryPath = queryPath.substring(index + 1);
                }
            }
            ret = query(root, queryPath);
        }

        return ret;
    }

    /**
     * Query Element with xpath<br/>
     * In this implement, we support queryPath is relative, it mean query element's sub element or attributes.<br/>
     * for exampe:<br/>
     * <code>
     * <pre>
     *     &lt;bookstore&gt;
     *        &lt;book&gt;
     *          &lt;title lang="eng"&gt;Harry Potter&lt;/title&gt;
     *          &lt;price&gt;29.99&lt;/price&gt;
     *        &lt;/book&gt;
     *     &lt;/bookstore&gt;
     * </pre>
     * </code><br/>
     * If you want get title elements, then queryPath is "/bookstore/book/title" and return all title elements<br/>
     * If the element parameter is book(Element), queryPath should be "title" will get title element.
     * <font color="red">Support query format:</font><br/>
     * <ol>
     * <li>/subnode1/node2/node3 get first node3 with first node2</li>
     * <li>/subnode1/node2/@attribute get first node2's attribute</li>
     * <li>/subnode1/node2/#text get first node2's Text content </li>
     * <li>/subnode1/node2/node3[n] get first node2's the nth node3 element.</li>
     * </ol>
     *
     * @param element   Element
     * @param queryPath String xpath query string
     * @return Object found result, Element or String or Element[]
     */
    public static Object query(Element element, String queryPath) {
        Object ret = null;
        if (element != null && queryPath != null) {
            int len = queryPath.length();
            if (len > 1) {
                char ch = queryPath.charAt(0);
                if (ch == '/') {
                    // Parse absolute
                    queryPath = queryPath.substring(1);
                }
            }
            String[] paths = StringUtil.split(queryPath, '/');
            len = paths.length;
            if (len > 0) {
                String path;

                Element current = element;
                // set find element or find attribute or text, if findElement is true,
                //     ret is Element.
                boolean findElement = true;
                for (int i = 0; i < len; i++) {
                    path = paths[i];
                    if (path.length() > 0) {
                        String sub = null;
                        int num = 0;
                        if (path.startsWith("@")) {
                            // get attribute
                            path = path.substring(1);
                            ret = current.getAttributeValue(null, path);
                            findElement = false;
                            break;
                        } else if (path.startsWith("#")) {
                            // get special content.
                            path = path.toLowerCase();
                            findElement = false;
                            if (path.equals("#TEXT")) {
                                ret = XMLUtil.getAllText(current);
                            }
                            break;
                        } else {
                            // Get Element
                            int sindex = path.indexOf('['); // check for element choose.
                            if (sindex != -1) {
                                sub = path.substring(sindex + 1);
                                // use substring not use substring(int, int) for standard Java and Android
                                path = path.substring(0, sindex);
                            }
                        }

                        Element[] els = XMLUtil.getElementsByName(current, path);
                        if (els != null) {
                            // found so check.
                            if (sub != null) {
                                int eindex = sub.indexOf(']');
                                if (eindex != -1) {
                                    sub = sub.substring(0, eindex);
                                    // check function
                                    int dindex = sub.indexOf('(');
                                    if (dindex == -1) {
                                        // only have index
                                        try {
                                            num = Integer.parseInt(sub);
                                            num--;
                                            if (num < 0) {
                                                num = 0;
                                            }
                                        } catch (NumberFormatException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        // TODO This branch is need to parse
                                        if (sub.equals("last()")) {
                                            // if els lenth more than one, set last element
                                            // if only have one element, num default is 0.
                                            if (els.length > 1) {
                                                num = els.length - 1;
                                            }
                                        }
                                    }
                                }
                            }
                            // in here, path have modified by check.

                            if (num >= 0 && num < els.length) {
                                current = els[num];
                            }
                        } else {
                            // no found.
                            findElement = false;
                            // return null directly
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (findElement) {
                    ret = current;
                }
            }
        }
        return ret;
    }

    // //////// XPath function /////////////

//    private static Object
}
