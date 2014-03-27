package com.vhly.epubmaker.epub.content;

import com.vhly.epubmaker.epub.MediaType;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-15
 * Email: vhly@163.com
 */
public interface Content {
    /**
     * Return content
     *
     * @return byte[]
     */
    public abstract byte[] getContent();

    /**
     * Return content media type
     *
     * @return MediaType
     * @see MediaType
     */
    public abstract MediaType getMediaType();

    /**
     * Set content data
     *
     * @param data byte[] data
     */
    public abstract void setContent(byte[] data);
}
