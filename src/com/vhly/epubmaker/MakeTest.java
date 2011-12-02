package com.vhly.epubmaker;

import com.vhly.epubmaker.epub.EPubFile;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-12-2
 * Email: vhly@163.com
 */
public class MakeTest {
    public static void main(String[] args) {
        EPubFile file = new EPubFile();
        file.save("./MakeTest.epub");
    }
}
