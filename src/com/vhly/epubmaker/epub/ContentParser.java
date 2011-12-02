package com.vhly.epubmaker.epub;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-6
 * Email: vhly@163.com
 */

/**
 * Content Parser for all epub elements.
 */
public interface ContentParser {
    /**
     * Parse data
     *
     * @param buf byte[]
     * @return parse ok?
     */
    public abstract boolean parse(byte[] buf);
}
