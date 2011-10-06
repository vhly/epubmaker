package com.vhly.epubmaker.reader;

import com.vhly.epubmaker.epub.EPubFile;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-18
 * Email: vhly@163.com
 */
public interface EPubContentHandler {
    /**
     * Notify host receive new epub file object.
     * @param file EPubFile
     */
    public abstract void updateEPubFile(EPubFile file);

    /**
     * Notify host receive epub file contents temp store path
     * @param path String, file system path,
     */
    public abstract void updateTempStorePath(String path);

}
