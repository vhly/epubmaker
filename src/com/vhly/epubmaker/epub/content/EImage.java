package com.vhly.epubmaker.epub.content;

import com.vhly.epubmaker.epub.MediaType;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-12-4
 * Email: vhly@163.com
 */
public class EImage implements Content{
    /**
     * Return content
     *
     * @return byte[]
     */
    public byte[] getContent() {
        return new byte[0];
    }

    /**
     * Return content media type
     *
     * @return MediaType
     * @see com.vhly.epubmaker.epub.MediaType
     */
    public MediaType getMediaType() {
        return null;
    }

    /**
     * Set content data
     *
     * @param data byte[] data
     */
    public void setContent(byte[] data) {
    }
}
