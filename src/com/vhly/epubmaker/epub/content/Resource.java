package com.vhly.epubmaker.epub.content;

import com.vhly.epubmaker.epub.Item;
import com.vhly.epubmaker.epub.MediaType;
import com.vhly.epubmaker.epub.ZIPContent;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-12-9
 * Email: vhly@163.com
 */
public class Resource implements Content, ZIPContent {
    private byte[] data;

    private String type;

    private String url;

    private Item resItem;

    private MediaType mtype;

    private String en;

    public Resource(String type, String url) {
        Item it = new Item();
        it.href = url;
        it.mediatype = type;
        init(it);
    }

    public Resource(Item it) {
        init(it);
    }

    private void init(Item it) {
        resItem = it;
        url = it.href;
        type = it.mediatype;
        type = type.toLowerCase();
        if (type.equals("image/jpeg")) {
            mtype = MediaType.JPEG;
        } else if (type.equals("image/png")) {
            mtype = MediaType.PNG;
        } else if (type.equals("text/css")) {
            mtype = MediaType.CSS;
        } else if (type.equals("font/opentype")) {
            mtype = MediaType.FONT;
        } else if (type.equals("image/gif")) {
            mtype = MediaType.GIF;
        }
    }

    /**
     * Return content
     *
     * @return byte[]
     */
    public byte[] getContent() {
        return data;
    }

    /**
     * Return content media type
     *
     * @return MediaType
     * @see com.vhly.epubmaker.epub.MediaType
     */
    public MediaType getMediaType() {
        return mtype;
    }

    /**
     * Set content data
     *
     * @param data byte[] data
     */
    public void setContent(byte[] data) {
        this.data = data;
    }

    public void setId(String id) {
        if (id != null) {
            resItem.id = id;
        }
    }

    /**
     * Set entry name for zip file.
     *
     * @param ename Entry name
     */
    public void setEntryName(String ename) {
        en = ename;
    }

    /**
     * If model implement this method, return entry name in .epub file
     *
     * @return String entryname or null
     */
    public String getEntryName() {
        if (en == null) {
            en = "OEBPS/" + resItem.href;
        }
        return en;
    }
}
