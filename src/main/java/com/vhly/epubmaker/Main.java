package com.vhly.epubmaker;

import com.vhly.epubmaker.epub.EPubFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: vhly
 * Date: 2011-9-1
 * Time: 23:47:04
 */
public class Main {
    public static void main(String[] args) {
        int argc = args.length;
        if (argc == 1) {
            String path = args[0];
            File file = new File(path);
            if (file.exists() && file.canRead()) {
                EPubFile epub = null;
                try {
                    epub = new EPubFile(file);
                    epub.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
