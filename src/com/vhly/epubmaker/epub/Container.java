package com.vhly.epubmaker.epub;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-6
 * Email: vhly@163.com
 */

import net.dratek.browser.util.StreamUtil;
import net.dratek.browser.xml.XPathUtil;
import org.kxml2_orig.io.KXmlParser;
import org.kxml2_orig.kdom.Document;
import org.kxml2_orig.kdom.Element;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * EPub File's Container define<br/>
 * Default file contain an oepbs-package+xml file
 */
public class Container implements ZIPContent, ContentParser {
    private OPF packageFile;

    public Container() {
        packageFile = new OPF();
    }

    /**
     * Get PackageFile (OPF instance)
     *
     * @return OPF
     */
    public OPF getPackageFile() {
        return packageFile;
    }

    //////// ZIPContent implements //////////

    /**
     * Set entry name for zip file.
     *
     * @param ename Entry name
     */
    public void setEntryName(String ename) {
        // Not set entry name, because opf file locate in META-INF/ always.
    }

    /**
     * If model implement this method, return entry name in .epub file
     *
     * @return String entryname or null
     */
    public String getEntryName() {
        return "META-INF/container.xml";
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
            KXmlParser parser = new KXmlParser();
            ByteArrayInputStream bin = null;
            Document dom = null;
            try {
                bin = new ByteArrayInputStream(buf);
                parser.setInput(bin, null);
                dom = new Document();
                dom.parse(parser);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                StreamUtil.close(bin);
            }

            if (dom != null) {

                Object node = XPathUtil.query(dom, "/container/rootfiles/rootfile");
                if (node != null && node instanceof Element) {
                    Element en = (Element) node;
                    String fullpath = en.getAttributeValue(null, "full-path");
                    String mediatype = en.getAttributeValue(null, "media-type");
                    if (mediatype != null && mediatype.equals("application/oebps-package+xml")) {
                        packageFile = new OPF();
                        packageFile.setEntryName(fullpath);
                    }
                    bret = true;
                }
            }
        }
        return bret;
    }

    public void save(DataOutputStream dout) throws IOException {
        dout.writeBytes(toXML());
    }

    public String toXML() {
        String ret;
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\"?>\n");
        sb.append("<container version=\"1.0\" xmlns=\"urn:oasis:names:tc:opendocument:xmlns:container\">");
        sb.append("<rootfiles><rootfile full-path=\"");
        sb.append(packageFile.getEntryName());
        sb.append("\" media-type=\"application/oebps-package+xml\"/></rootfiles>");
        sb.append("</container>");
        ret = sb.toString();
        return ret;
    }
}
