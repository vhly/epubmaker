package com.vhly.epubmaker;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 13-12-11
 * Email: vhly@163.com
 */
public final class StreamUtil {
    public static void close(Object stream) {
        if (stream != null) {
            try {
                if (stream instanceof InputStream) {
                    ((InputStream) stream).close();
                } else if (stream instanceof OutputStream) {
                    ((OutputStream) stream).close();
                } else if (stream instanceof Reader) {
                    ((Reader) stream).close();
                } else if (stream instanceof Writer) {
                    ((Writer) stream).close();
                }
            } catch (Exception ex) {

            }
        }
    }
}
