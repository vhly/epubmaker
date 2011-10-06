package com.vhly.epubmaker.reader;

import com.vhly.epubmaker.epub.EPubFile;
import net.dratek.browser.util.StreamUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;

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
     *
     * @param targetFile File choosed file in File Chooser.
     * @param handler    EPubContentHandler
     */
    public FileLoadThread(File targetFile, EPubContentHandler handler) {
        this.targetFile = targetFile;
        this.handler = handler;
    }

    @Override
    public void run() {
        if (targetFile != null) {
            if (targetFile.exists() && targetFile.canRead()) {
                EPubFile epubFile = null;
                try {
                    epubFile = new EPubFile(targetFile);
                    if (epubFile.load() && handler != null) {
                        handler.updateEPubFile(epubFile);

                        File fcurrent = new File(".");
                        File tmp = new File(fcurrent, "tmp");
                        boolean bok = true;
                        if (!tmp.exists()) {
                            bok = tmp.mkdirs();
                        }

                        if (bok) {
                            String tn = targetFile.getName();
                            fcurrent = new File(tmp, tn);
                            if (!fcurrent.exists()) {
                                bok = fcurrent.mkdirs();
                            }
                        }
                        if (bok) {
                            Enumeration<? extends ZipEntry> entries = epubFile.entries();
                            File target = null;
                            while (entries.hasMoreElements()) {
                                ZipEntry entry = entries.nextElement();
                                String ename = entry.getName();
                                System.out.println("ename = " + ename);
                                byte[] buf = epubFile.readEntryData(entry);
                                if (buf != null && buf.length > 0) {
                                    target = new File(fcurrent, ename);
                                    File pf = target.getParentFile();
                                    if (!pf.exists()) {
                                        bok = pf.mkdirs();
                                    }
                                    if (bok) {
                                        FileOutputStream fout = null;
                                        try {
                                            fout = new FileOutputStream(target);
                                            fout.write(buf);
                                        } catch (IOException ioe) {

                                        } finally {
                                            StreamUtil.close(fout);
                                        }
                                    }
                                }
                            }
                            handler.updateTempStorePath(fcurrent.getPath());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
