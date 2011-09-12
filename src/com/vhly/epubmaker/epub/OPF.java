package com.vhly.epubmaker.epub;

import net.dratek.browser.util.StreamUtil;
import net.dratek.browser.xml.XMLUtil;
import net.dratek.browser.xml.XPathUtil;
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

    private Metadata metadata;

    private Manifest manifest;

    private Spine spine;

    private Guide guide;

    private NCX toc;

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
            if (dom != null) {
//                Element root = dom.getRootElement();
//                Element[] metadatas = XMLUtil.getElementsByName(root, "metadata");
//                if(metadatas != null && metadatas.length > 0){
//                    Element metadata = metadatas[0];
//                    parseMetadata(metadata);
//                }

                parseMetadata(dom);

                parseManifest(dom);

                parseSpine(dom);

                parseGuide(dom);
            }
        }
        return bret;
    }

    public String getTocHref(){
        String ret = null;
        if(spine != null && manifest != null){
            String id = spine.toc;
            if(id != null){
                Item item = manifest.getItem(id);
                if(item != null){
                    ret = item.href;
                }
            }
        }
        return ret;
    }

    public void setToc(NCX t){
        toc = t;
    }

    private void parseGuide(Document dom) {
        Object or = XPathUtil.query(dom, "/package/guide/reference");
        if(or != null && or instanceof Element){
            guide = new Guide();
            Element eor = (Element) or;
            guide.title = eor.getAttributeValue(null,"title");
            guide.href = eor.getAttributeValue(null,"href");
            guide.type = eor.getAttributeValue(null,"type");
        }
    }

    private void parseSpine(Document dom) {
        Object ospine = XPathUtil.query(dom, "/package/spine");
        if(ospine != null && ospine instanceof Element){
            Element espine = (Element) ospine;
            spine = new Spine();
            spine.parse(espine);
        }
    }

    private void parseManifest(Document dom) {
        // TODO Parse manifest element
        Object omanifest = XPathUtil.query(dom, "/package/manifest");
        if (omanifest != null && omanifest instanceof Element) {
            manifest = new Manifest();
            Element emanifest = (Element) omanifest;
            manifest.parse(emanifest);
        }
    }

    private void parseMetadata(Document dom) {
        metadata = new Metadata();

        Object id = XPathUtil.query(dom, "/package/metadata/dc:identifier/#text");
        if (id != null && id instanceof String) {
            metadata.setIdentifier((String) id);
        }

        Object title = XPathUtil.query(dom, "/package/metadata/dc:title/#text");
        System.out.println("title = " + title);
        if (title != null && title instanceof String) {
            metadata.setTitle((String) title);
        }

        Object rights = XPathUtil.query(dom, "/package/metadata/dc:rights/#text");
        System.out.println("rights = " + rights);
        if (rights != null && rights instanceof String) {
            metadata.setRights((String) rights);
        }

        Object publisher = XPathUtil.query(dom, "/package/metadata/dc:publisher/#text");
        System.out.println("publisher = " + publisher);
        if (publisher != null && publisher instanceof String) {
            metadata.setPublisher((String) publisher);
        }

        Object subject = XPathUtil.query(dom, "/package/metadata/dc:subject/#text");
        System.out.println("subject = " + subject);
        if (subject != null && subject instanceof String) {
            metadata.setSubject((String) subject);
        }

        Object date = XPathUtil.query(dom, "/package/metadata/dc:date/#text");
        System.out.println("date = " + date);
        if (date != null && date instanceof String) {
            metadata.setDate((String) date);
        }

        Object description = XPathUtil.query(dom, "/package/metadata/dc:description/#text");
        System.out.println("description = " + description);
        if (description != null && description instanceof String) {
            metadata.setDescription((String) description);
        }

        Object creator = XPathUtil.query(dom, "/package/metadata/dc:creator/#text");
        System.out.println("creator = " + creator);
        if (creator != null && creator instanceof String) {
            metadata.setCreator((String) creator);
        }

        Object language = XPathUtil.query(dom, "/package/metadata/dc:language/#text");
        System.out.println("language = " + language);
        if (language != null && language instanceof String) {
            metadata.setLanguage((String) language);
        }

        Object meta = XPathUtil.query(dom, "/package/metadata/meta");
        System.out.println("meta = " + meta);
        if (meta != null && meta instanceof Element) {
            Element emeta = (Element) meta;
            String name = emeta.getAttributeValue(null, "name");
            String content = emeta.getAttributeValue(null, "content");
            if (name != null) {
                if (name.equals("cover")) {
                    metadata.setCover_image(content); // This field is not url, it's a ref
                }
            }
        }
    }
}
