package com.vhly.epubmaker.epub.content;

import com.vhly.epubmaker.epub.Item;
import com.vhly.epubmaker.epub.MediaType;

import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-18
 * Email: vhly@163.com
 */
public class Chapter implements Content {

    public static Chapter createChapter(String href, byte[] pageContent) {
        Chapter ret = null;
        if (href != null && pageContent != null && pageContent.length > 0) {
            ret = new Chapter();
            ret.content = pageContent;
            ret.chapterItem = new Item();
            ret.chapterItem.href = href;
            ret.chapterItem.mediatype = MediaType.XHTML.toString();
            ret.chapterItem.id = href;
        }
        return ret;
    }

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

    private String pageContent;

    private Item chapterItem;

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
        if (data != null) {
            content = data;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Item getChapterItem() {
        return chapterItem;
    }

    public void setChapterItem(Item chapterItem) {
        this.chapterItem = chapterItem;
    }

    public String getPageContent() {
        if (pageContent == null && content != null) {
            try {
                pageContent = new String(content, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                pageContent = new String(content);
            }
        }
        return pageContent;
    }

    public void setPageContent(String pageContent) {
        this.pageContent = pageContent;
    }

    @Override
    public String toString() {
        return title != null ? title : "Chapter untitled";
    }
}
