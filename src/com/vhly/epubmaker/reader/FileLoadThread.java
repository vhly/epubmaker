package com.vhly.epubmaker.reader;

import com.vhly.epubmaker.epub.EPubFile;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-18
 * Email: vhly@163.com
 */
public class FileLoadThread extends Thread {
    private File targetFile;

    private EPubContentHandler handler;

    /**
     * FileLoadThread for epub files parse and notify for handler.
     * @param targetFile File choosed file in File Chooser.
     * @param handler EPubContentHandler
     */
    public FileLoadThread(File targetFile, EPubContentHandler handler) {
        this.targetFile = targetFile;
        this.handler = handler;
    }

    @Override
    public void run() {
        if(targetFile != null){
            if(targetFile.exists() && targetFile.canRead()){
                EPubFile epubFile = null;
                try {
                    epubFile = new EPubFile(targetFile);
                    if(epubFile.load() && handler != null){
                        handler.updateEPubFile(epubFile);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
