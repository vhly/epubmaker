package com.vhly.epubmaker.epub;

import net.dratek.browser.util.StreamUtil;
import net.dratek.browser.xml.XMLUtil;
import org.kxml2_orig.io.KXmlParser;
import org.kxml2_orig.kdom.Document;
import org.kxml2_orig.kdom.Element;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-6
 * Email: vhly@163.com
 */
public class OPF implements ZIPContent, ContentParser {

    private String entryName = "OEBPS/content.opf";

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
            if(dom != null){
                Element root = dom.getRootElement();
                Element[] metadatas = XMLUtil.getElementsByName(root, "metadata");
                if(metadatas != null && metadatas.length > 0){
                    Element metadata = metadatas[0];
                    parseMetadata(metadata);
                }

            }
        }
        return bret;
    }

    private void parseMetadata(Element metadata) {

    }
}
