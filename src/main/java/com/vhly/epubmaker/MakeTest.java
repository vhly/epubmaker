package com.vhly.epubmaker;

import com.vhly.epubmaker.epub.EPubFile;
import com.vhly.epubmaker.epub.Item;
import com.vhly.epubmaker.epub.content.Chapter;
import com.vhly.epubmaker.epub.toc.NavPoint;
import com.vhly.browser.util.StreamUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-12-2
 * Email: vhly@163.com
 */
public class MakeTest {
    public static void main(String[] args) {
//        oldBookGen();
        int argc = args.length;
        if (argc == 0) {
            // TODO Show usage
            System.out.println("Usage: MakeTest <confxml path> <epub gen path>");
        } else if (argc == 2) {
            String confPath = args[0];
            File f = new File(confPath);
            if (f.exists() && f.canRead()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                try {
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document doc = builder.parse(f);
                    Element root = doc.getDocumentElement();
                    String nodeName = root.getNodeName();
                    if (nodeName.equals("epubmake")) {
                        String title, author, uuid, description, coverPath = null, coverType = null;
                        NodeList list = root.getElementsByTagName("title");
                        String s = null;
                        if (list != null && list.getLength() > 0) {
                            s = list.item(0).getTextContent();
                        }
                        title = s;

                        s = null;
                        list = root.getElementsByTagName("author");
                        if (list != null && list.getLength() > 0) {
                            s = list.item(0).getTextContent();
                        }
                        author = s;
                        s = null;
                        list = root.getElementsByTagName("uuid");
                        if (list != null && list.getLength() > 0) {
                            s = list.item(0).getTextContent();
                        }
                        uuid = s;

                        s = null;
                        list = root.getElementsByTagName("description");
                        if (list != null && list.getLength() > 0) {
                            s = list.item(0).getTextContent();
                        }
                        description = s;

                        s = null;
                        list = root.getElementsByTagName("cover");
                        if (list != null && list.getLength() > 0) {
                            Node item = list.item(0);
                            if (item instanceof Element) {
                                Element el = (Element) item;
                                coverPath = el.getAttribute("href");
                                coverType = el.getAttribute("type");
                            }
                        }
                        if (title != null && author != null && uuid != null) {
                            EPubFile file = new EPubFile();
                            file.setTitle(title);
                            file.setAuthor(author);
                            file.setUUID(uuid);
                            file.setDescript(description);
                            file.setPublisher("vhly [EPubMaker]");
                            if (coverPath != null && coverType != null) {
                                setCover(file, coverPath, coverType);
                            }

                            list = root.getElementsByTagName("chapter");
                            if (list != null && list.getLength() > 0) {
                                int size = list.getLength();
                                Node item;
                                Element el;
                                for (int i = 0; i < size; i++) {
                                    item = list.item(i);
                                    el = (Element) item;
                                    NodeList lst2 = el.getElementsByTagName("title");
                                    String ctitle = null;
                                    if (lst2 != null && lst2.getLength() > 0) {
                                        ctitle = lst2.item(0).getTextContent();
                                    }

                                    lst2 = el.getElementsByTagName("ename");
                                    String ename = null;
                                    if (lst2 != null && lst2.getLength() > 0) {
                                        ename = lst2.item(0).getTextContent();
                                    }

                                    lst2 = el.getElementsByTagName("path");
                                    String path = null;
                                    if (lst2 != null && lst2.getLength() > 0) {
                                        path = lst2.item(0).getTextContent();
                                    }

                                    if (path != null && ename != null) {
                                        Vector<NavPoint> nvps = new Vector<NavPoint>();

                                        parseNVP(el, nvps);

                                        addChapter(file, ctitle, ename, path, nvps);
                                    }

                                }
                            }

                            list = root.getElementsByTagName("resource");
                            if (list != null && list.getLength() > 0) {
                                int size = list.getLength();
                                Node nd;
                                Element el;
                                String path, href, type, id;
                                for (int i = 0; i < size; i++) {
                                    nd = list.item(i);
                                    el = (Element) nd;
                                    id = el.getAttribute("id");
                                    path = el.getAttribute("path");
                                    href = el.getAttribute("href");
                                    type = el.getAttribute("type");
                                    if(path != null && href != null && type != null){
                                        addResource(file,id, path, href, type);
                                    }
                                }
                            }

                            file.save(args[1]);
                        }
                    }
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void parseNVP(Element el, Vector<NavPoint> nvps) {
        NodeList lst2;
        lst2 = el.getElementsByTagName("nvp");
        if (lst2 != null && lst2.getLength() > 0) {
            int len = lst2.getLength();
            String nl = null, nu = null;
            Element ne;
            for (int j = 0; j < len; j++) {
                Node ni = lst2.item(j);
                ne = (Element) ni;
                NodeList lst3 = ne.getElementsByTagName("label");
                if (lst3 != null && lst3.getLength() > 0) {
                    nl = lst3.item(0).getTextContent();
                }

                lst3 = ne.getElementsByTagName("href");
                if (lst3 != null && lst3.getLength() > 0) {
                    nu = lst3.item(0).getTextContent();
                }
                if (nl != null && nu != null) {
                    NavPoint np = new NavPoint();
                    np.label = nl;
                    np.content = nu;
                    nvps.add(np);
                }
            }
        }
    }

    private static void oldBookGen() {
        EPubFile file = new EPubFile();
        file.setTitle("Test");
        file.setAuthor("vhly[FR]");
        file.setUUID("998482814");
        file.setDescript("This is a Test");

        setCover(file, "./cover.jpg", "image/jpeg");

        addResource(file, "todo", "./TODO", "TODO", "text/plain");

        addChapter(file, "第一章", "c001.xhtml", "./res/book1/c001.xhtml", null);

        addChapter(file, "考试要点-多个概率进行计算", "c002.xhtml", "./res/book1/c002.xhtml", null);

        addChapter(file, "概率的乘法法则", "c003.xhtml", "./res/book1/c003.xhtml", null);

        addChapter(file, "重要练习", "c004.xhtml", "./res/book1/c004.xhtml", null);

        addChapter(file, "随机变量", "c005.xhtml", "./res/book1/c005.xhtml", null);

        file.save("./MakeTest.epub");
    }

    private static void addChapter(EPubFile file, String title, String ename, String fpath, Vector<NavPoint> nvps) {
        // TODO In this implements, title must setting with ascii char, not support other char now.
        Chapter chb = loadChapter(title, ename, fpath);
        if (chb != null) {
            if (nvps != null && nvps.size() > 0) {
                chb.appendNVPS(nvps);
            }
            file.addChapter(chb);
        }
    }

    private static void addResource(EPubFile file, String id, String path, String href, String type) {
        FileInputStream fin = null;
        File f;

        // add other resource
        try {
            f = new File(path);
            fin = new FileInputStream(f);
            byte[] bytes = StreamUtil.readStream(fin);
            file.addResource(id, type, href, bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(fin);
        }
    }

    private static void setCover(EPubFile file, String path, String type) {
        File f = new File(path);
        if (f.exists() && f.canRead()) {
            FileInputStream fin = null;
            try {
                fin = new FileInputStream(f);
                byte[] bytes = StreamUtil.readStream(fin);
                file.setCover(f.getName(), bytes, type);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                StreamUtil.close(fin);
            }
        }
    }

    /**
     * load file from file sytem and set chapter
     *
     * @param title Chpater title
     * @param ename ename for epub zip entry
     * @param fPath real path
     * @return Chapter will create
     */
    private static Chapter loadChapter(String title, String ename, String fPath) {
        Chapter ret = null;
        if (title != null && ename != null && fPath != null) {
            File f = new File(fPath);
            if (f.exists() && f.canRead()) {
                FileInputStream fin = null;
                byte[] content = null;
                try {
                    fin = new FileInputStream(f);
                    content = StreamUtil.readStream(fin);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    StreamUtil.close(fin);
                }
                if (content != null && content.length > 0) {
                    ret = new Chapter();
                    ret.setTitle(title);
                    ret.setEntryName(ename);
                    ret.setContent(content);
                    Item chapterItem = new Item(ename, ename);
                    ret.setChapterItem(chapterItem);
                }
            }
        }
        return ret;
    }
}
