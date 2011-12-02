package com.vhly.epubmaker.epub;

import com.vhly.epubmaker.epub.toc.NavPoint;
import net.dratek.browser.util.StreamUtil;
import net.dratek.browser.xml.XMLUtil;
import net.dratek.browser.xml.XPathUtil;
import org.kxml2_orig.io.KXmlParser;
import org.kxml2_orig.kdom.Document;
import org.kxml2_orig.kdom.Element;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-6
 * Email: vhly@163.com
 */
public class NCX implements ZIPContent, ContentParser {

    private String docTitle;

    private Vector<NavPoint> map;

    private String entryName = "OEBPS/toc.ncx";

    public NCX() {
        map = new Vector<NavPoint>();
    }

    public void dealloc() {
        map.clear();
        map = null;
        docTitle = null;
    }

    public void addNavPoint(NavPoint np) {
        if (np != null) {
            map.add(np);
        }
    }

    //////// ZIPContent implements //////////

    /**
     * Set entry name for zip file.
     *
     * @param ename Entry name
     */
    public void setEntryName(String ename) {
        entryName = ename;
    }

    /**
     * If model implement this method, return entry name in .epub file
     *
     * @return String entryname or null
     */
    public String getEntryName() {
        return entryName;
    }

    //////// ContentParser implements //////////

    /**
     * Parse data
     *
     * @param buf byte[]
     * @return parse ok?
     */
    public boolean parse(byte[] buf) {
        boolean bret = false;
        if (buf != null) {
            ByteArrayInputStream bin = new ByteArrayInputStream(buf);
            KXmlParser parser = new KXmlParser();
            Document dom = null;
            try {
                parser.setInput(bin, null);
                dom = new Document();
                dom.parse(parser);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                StreamUtil.close(bin);
            }
            if (dom != null) {
                Object obj = XPathUtil.query(dom, "/ncx/docTitle/text/#text");
                if (obj != null && obj instanceof String) {
                    docTitle = (String) obj;
                }
                obj = XPathUtil.query(dom, "/ncx/navMap");
                if (obj != null && obj instanceof Element) {
                    Element el = (Element) obj;
                    Element[] navPoints = XMLUtil.getElementsByName(el, "navPoint");
                    if (navPoints != null) {
                        int len = navPoints.length;
                        Element ee;
                        for (int i = 0; i < len; i++) {
                            ee = navPoints[i];
                            parseNavPoint(ee);
                        }
                    }
                }
                bret = true;
            }
        }
        return bret;
    }

    private void parseNavPoint(Element el) {
        if (el != null) {
            NavPoint np = new NavPoint();
            String id = el.getAttributeValue(null, "id");
            String playOrder = el.getAttributeValue(null, "playOrder");
            np.id = id;
            np.playOrder = playOrder;
            Element navLabel = XMLUtil.getElementByName(el, "navLabel");
            if (navLabel != null) {
                Element text = XMLUtil.getElementByName(navLabel, "text");
                if (text != null) {
                    np.label = XMLUtil.getElementTextContent(text);
                }
            }
            Element content = XMLUtil.getElementByName(el, "content");
            if (content != null) {
                np.content = content.getAttributeValue(null, "src");
            }

            addNavPoint(np);

            Element[] navPoints = XMLUtil.getElementsByName(el, "navPoint");

            if (navPoints != null) {
                Element ee;
                int len = navPoints.length;
                for (int i = 0; i < len; i++) {
                    ee = navPoints[i];
                    parseNavPoint(ee);
                }
            }
        }
    }

    public void save(DataOutputStream dout) throws IOException {
        dout.writeBytes(toXML());
    }

    private String toXML() {
        String ret;
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version='1.0' encoding='utf-8'?>\n");
        sb.append("<ncx xmlns=\"http://www.daisy.org/z3986/2005/ncx/\" version=\"2005-1\">\n");
        sb.append("</ncx>");
        ret = sb.toString();
        return ret;
    }
}
