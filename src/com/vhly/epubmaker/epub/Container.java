package com.vhly.epubmaker.epub;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-6
 * Email: vhly@163.com
 */

/**
 * EPub File's Container define<br/>
 * Default file contain an oepbs-package+xml file
 */
public class Container implements ZIPContent, ContentParser{
    private OPF packageFile;

    //////// ZIPContent implements //////////

    /**
     * Set entry name for zip file.
     *
     * @param ename Entry name
     */
    public void setEntryName(String ename) {
        // Not set entry name, because opf file locate in META-INF/ always.
    }

    /**
     * If model implement this method, return entry name in .epub file
     *
     * @return String entryname or null
     */
    public String getEntryName() {
        return "META-INF/container.xml";
    }


    //////// ContentParser implements //////////

    /**
     * Parse data
     *
     * @param buf byte[]
     * @return parse ok?
     */
    public boolean parse(byte[] buf) {
        boolean bret = false;
        if(buf != null){

        }
        return bret;
    }
}
