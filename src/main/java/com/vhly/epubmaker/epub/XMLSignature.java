package com.vhly.epubmaker.epub;

/**
 * Created by vhly[FR] on 14-3-27.
 * Project : epubmaker
 *
 * @author vhly[FR]
 *         Email: vhly@163.com
 */
public class XMLSignature implements ZIPContent, ContentParser {

    private String entryName = "META-INF/signatures.xml";

    @Override
    public boolean parse(byte[] buf) {
        return false;
    }

    @Override
    public void setEntryName(String ename) {
        entryName = ename;
    }

    @Override
    public String getEntryName() {
        return entryName;
    }
}
