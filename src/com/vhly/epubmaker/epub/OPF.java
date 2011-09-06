package com.vhly.epubmaker.epub;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-6
 * Email: vhly@163.com
 */
public class OPF implements ZIPContent,ContentParser{

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
        return false;
    }
}
