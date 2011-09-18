package com.vhly.epubmaker.epub.content;

import com.vhly.epubmaker.epub.MediaType;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-18
 * Email: vhly@163.com
 */
public class Chapter implements Content{
    /**
     * Chapter's title
     */
    private String title;
    /**
     * Chapter's content.
     */
    private byte[] content;
    /**
     * Chapter's mediatype, default is xhtml
     */
    private MediaType mediaType = MediaType.XHTML;

    // Content implements.

    /**
     * Return content
     *
     * @return byte[]
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * Return content media type
     *
     * @return MediaType
     * @see com.vhly.epubmaker.epub.MediaType
     */
    public MediaType getMediaType() {
        return mediaType;
    }

    /**
     * Set content data
     *
     * @param data byte[] data
     */
    public void setContent(byte[] data) {
        if(data != null){
            content = data;
        }
    }
}
