package com.vhly.browser.util;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 2010-6-14
 * Time: 8:31:10
 * Email: vhly@163.com
 */

/**
 * Stream Util<br/>
 * some stream tool
 */
public final class StreamUtil {
    /**
     * close stream
     *
     * @param stream stream will close, stream could be InputStream, OutputStream
     */
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
                ex.printStackTrace();
            }
        }
    }

    /**
     * Get InputStream from package's path(app jar package)
     *
     * @param path jar's path
     * @return InputStream
     */
    public static InputStream openPackageResource(String path) {
        InputStream ret = null;
        // J2ME and J2SE
        if (path != null) {
            ret = Runtime.getRuntime().getClass().getResourceAsStream(path);
        }

        // Android
        // 1. getActivity or get MIDlet with Polish Adapte
        // 2. activity.getAssert.open(path) // path must not has / prefix

        return ret;
    }

    /**
     * Read Stream to buffer(byte[])
     *
     * @param in Stream will read
     * @return byte[] stream data
     */
    public static byte[] readStream(InputStream in) {
        byte[] ret = null;
        if (in != null) {
            byte[] buf = new byte[16];
            ByteArrayOutputStream bout = null;
            int len;
            try {
                bout = new ByteArrayOutputStream();
                while (true) {
                    len = in.read(buf);
                    if (len == -1) {
                        break;
                    }
                    bout.write(buf, 0, len);
                }
                ret = bout.toByteArray();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                close(bout);
            }
        }
        return ret;
    }

    /**
     * Convert content to InputStream for read.<br/>
     * It will return a ByteArrayInputStream for instance.
     * Content will convert to buffer with default vm's charset encoding.
     *
     * @param content String
     * @return InputStream
     * @throws java.io.UnsupportedEncodingException uee
     */
    public static InputStream convertForInput(String content) throws UnsupportedEncodingException {
        return convertForInput(content, null);
    }

    /**
     * Convert content to InputStream for read.<br/>
     * It will return a ByteArrayInputStream for instance.
     * Content will convert to buffer with default vm's charset encoding.
     *
     * @param content String
     * @param charset String charset for String convert
     * @return InputStream
     * @throws java.io.UnsupportedEncodingException uee
     */
    public static InputStream convertForInput(String content, String charset) throws UnsupportedEncodingException {
        InputStream ret = null;
        if (content != null && content.length() > 0) {
            byte[] buf;
            if (charset != null) {
                buf = content.getBytes(charset);
            } else {
                buf = content.getBytes();
            }
            ret = new ByteArrayInputStream(buf);
        }
        return ret;
    }
}
