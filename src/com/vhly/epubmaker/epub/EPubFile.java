package com.vhly.epubmaker.epub;

import net.dratek.browser.util.StreamUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * EMail: vhly@163.com
 * Date: 2011-9-2
 * Time: 0:11:53
 */

/**
 * EPub File, .epub file is a zip file actually.
 */
public class EPubFile extends ZipFile {

    /**
     * MIME Type
     * Default type is application/epub+zip
     */
    private String mimetype = "application/epub+zip";

    /**
     * Content container, all enter pointer defines in here.
     */
    private Container container;

    /**
     * Constructor with File path
     *
     * @param name File name
     * @throws IOException IOE
     */
    public EPubFile(String name) throws IOException {
        super(name);
    }

    /**
     * Constructor with File path
     *
     * @param file File object
     * @param mode file operation mode
     * @throws IOException IOE
     */
    public EPubFile(File file, int mode) throws IOException {
        super(file, mode);
    }

    /**
     * Constructor with File path
     *
     * @param file File object
     * @throws ZipException Zip Exception
     * @throws IOException  IOE
     */
    public EPubFile(File file) throws ZipException, IOException {
        super(file);
    }

    /**
     * Get MIME Type<br/>
     * If no set this field, return default mimetype
     *
     * @return String
     */
    public String getMimetype() {
        return mimetype;
    }

    /**
     * Set MIME type<br/>
     * Default MIME type is application/epub+zip
     *
     * @param mimetype String
     */
    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    /**
     * Load all content from self zip entries
     *
     * @return boolean load ok?
     */
    public boolean load() {
        boolean bret = false;
        Enumeration<? extends ZipEntry> entries = entries();
        if (entries != null) {
            ZipEntry entry = null;
            String ename;
            while (entries.hasMoreElements()) {
                entry = entries.nextElement();
                ename = entry.getName();
                System.out.println("ename = " + ename);
                if (ename.equals("mimetype")) {
                    // load mimetype file content.
                    String s = readMIMEType(entry);
                    if(s != null){
                        mimetype = s;
                    }
                }else if(ename.equals("META-INF/container.xml")){
                    readContainer(entry);
                }
            }
        }
        return bret;
    }

    /**
     * Read container entry and return it.
     * @param entry ZipEntry in self
     * @return Container
     */
    private Container readContainer(ZipEntry entry) {
        return null;
    }

    /**
     * Find mimetype entry and load
     *
     * @param entry entry which name is mimetype
     * @return String mimetype
     */
    private String readMIMEType(ZipEntry entry) {
        String ret = null;
        if (entry != null) {
            InputStream inputStream = null;
            try {
                inputStream = getInputStream(entry);
                byte[] buf = StreamUtil.readStream(inputStream);
                if(buf != null && buf.length > 0){
                    ret = new String(buf,"UTF-8");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                StreamUtil.close(inputStream);
            }

        }
        return ret;
    }
}
