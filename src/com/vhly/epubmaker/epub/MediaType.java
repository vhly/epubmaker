package com.vhly.epubmaker.epub;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-14
 * Email: vhly@163.com
 */
public enum MediaType {
    PACKAGE_XML("application/oebps-package+xml"),
    NCX("application/x-dtbncx+xml"),
    CSS("text/css"),
    XHTML("application/xhtml+xml"),
    FONT("font/opentype"),
    JPEG("image/jpeg"),
    GIF("image/gif"),
    PNG("image/png"),;

    private String type;

    MediaType(String s) {
        type = s;
    }


    @Override
    public String toString() {
        return type;
    }
}
