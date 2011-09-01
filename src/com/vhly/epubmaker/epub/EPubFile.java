package com.vhly.epubmaker.epub;

import java.io.File;
import java.io.IOException;
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
     */
    public String mimetype;

    /**
     * Constructor with File path
     * @param name File name
     * @throws IOException IOE
     */
    public EPubFile(String name) throws IOException {
        super(name);
    }
    /**
     * Constructor with File path
     * @param file File object
     * @param mode file operation mode
     * @throws IOException IOE
     */
    public EPubFile(File file, int mode) throws IOException {
        super(file, mode);
    }
    /**
     * Constructor with File path
     * @param file File object
     * @throws ZipException Zip Exception
     * @throws IOException IOE
     */
    public EPubFile(File file) throws ZipException, IOException {
        super(file);
    }
}
