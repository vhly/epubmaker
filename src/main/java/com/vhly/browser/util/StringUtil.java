package com.vhly.browser.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.TimeZone;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 2010-6-10
 * Time: 18:17:14
 */
public final class StringUtil {

    private static Calendar cal;

    private StringUtil() {

    }

    /**
     * Convert string content to html &#xxxxxx; support, for remove file encoding dependce.
     *
     * @param str which convert.
     * @return String for HTML content
     */
    public static String convertToHTML(String str) {
        String ret = str;
        if (str != null) {
            char[] chs = str.toCharArray();
            if (chs != null) {
                int len = chs.length;
                if (len > 0) {
                    char ch;
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < len; i++) {
                        ch = chs[i];
                        int iv;
                        iv = (int) ch;
                        if (iv > 0x7F) {
                            sb.append("&#").append(iv).append(';');
                        } else {
                            sb.append(ch);
                        }
                    }
                    ret = sb.toString();
                }
            }
        }
        return ret;
    }

    /**
     * Convert html encoding string content to real.
     *
     * @param str which convert.
     * @return String for real content
     */
    public static String convertFromHTML(String str) {
        String ret = str;
        if (str != null) {
            char[] chs = str.toCharArray();
            if (chs != null) {
                int len = chs.length;
                if (len > 0) {
                    char ch;
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < len; i++) {
                        ch = chs[i];
                        int iv;

                        if (ch == '&') {
                            char ch1 = chs[i + 1];
                            if (ch1 == '#') {
                                StringBuffer sb2 = new StringBuffer();
                                int count = 0;
                                for (int j = i + 2; j < len; j++) {
                                    count++;
                                    if (count > 6) {
                                        throw new RuntimeException("HTML encoded character can't parse");
                                    }
                                    if (chs[j] == ';') {
                                        i = j + 1;
                                        break;
                                    }
                                    i = j;
                                    sb2.append(chs[j]);
                                }
                                String s = sb2.toString();
                                iv = Integer.parseInt(s);
                                ch = (char) iv;
                                sb.append(ch);
                            } else {
                                sb.append(ch);
                            }
                        } else {
                            sb.append(ch);
                        }
                    }
                    ret = sb.toString();
                }
            }
        }
        return ret;
    }

    /**
     * Check String is null or empty<br/>
     * This method is for Server version<br/>
     * but I think if string is "  " this method will return false, good or bad?
     *
     * @param str String
     * @return isEmpty?
     */
    public static boolean isEmpty(String str) {
        boolean ret = false;
        if (str == null || str.length() == 0) {
            ret = true;
        }
        return ret;
    }

    /**
     * Check String is valid?<br/>
     * if str not is null and length > 0, is true<br/>
     * Same to isEmpty, the string " " or "  " is valid or empty??
     *
     * @param str String
     * @return isValid?
     */
    public static boolean isValid(String str) {
        boolean ret = true;
        if (str == null || str.length() == 0) {
            ret = false;
        }
        return ret;
    }

    public static Hashtable<String, String> splitURLParameters(String s) {
        Hashtable<String, String> ret = new Hashtable<String, String>();
        if (s != null) {
            s = s.trim();
            if (s.length() > 0) {
                String[] split = split(s, '&');
                if (split != null) {
                    int count = split.length;
                    if (count > 0) {
                        int index;
                        String k, v;
                        for (String s1 : split) {
                            if (s1.length() > 0) {
                                index = s1.indexOf('=');
                                k = null;
                                v = null;
                                if (index == -1) {
                                    k = s1.substring(0, index);
                                    if (index + 1 >= s1.length()) {
                                        v = "";
                                    } else {
                                        v = s1.substring(index + 1);
                                    }
                                } else {
                                    k = s1;
                                    v = "";
                                }
                                if (k != null) {
                                    try {
                                        k = URLDecoder.decode(k, "UTF-8");
                                        v = URLDecoder.decode(v, "UTF-8");
                                    } catch (UnsupportedEncodingException e) {

                                    }
                                    ret.put(k, v);
                                }
                            }
                        }
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Split string with char
     *
     * @param s     String
     * @param split char to split
     * @return String[] splited array. if s is "" 0 length, will return new String[0]
     */
    public static String[] split(String s, char split) {
        String[] sa = null;
        if (s != null) {
            int len = s.length();
            if (len > 0) {
                char[] chs = s.toCharArray();
                char ch;
                int num = 0;
                for (int i = 0; i < len; i++) {
                    ch = chs[i];
                    if (ch == split) {
                        num++;
                    }
                }
                num++;
                sa = new String[num];
                if (num == 1) {
                    sa[0] = s;
                } else {
                    int start = 0, index;
                    int is = 0;
                    while (start < len) {
                        index = s.indexOf(split, start);
                        if (index != -1) {
                            sa[is++] = s.substring(start, index);
                            start = index + 1;
                        } else {
                            sa[is++] = s.substring(start);
                            start = len;
                        }
                    }
                    if (is < num) {
                        sa[is] = "";
                    }
                }
            } else {
                sa = new String[0];
            }
        }
        return sa;
    }

    /**
     * Pase Date string, format is: Sun, 13 Jun 2010 13:56:08 GMT
     *
     * @param str date str
     * @return long times from 1970
     */
    public static long parseDateGMT(String str) {
        long lret = System.currentTimeMillis();
        if (str != null) {
            try {
                int si, di;
                si = str.indexOf(',');
                String zone = "";
                String time = "";
                String date;
                // No use for week
//                String week;
//                if (si != -1) {
//                    week = str.substring(0, si);
//                }
                di = si;
                si = str.lastIndexOf(' ');
                if (si != -1) {
                    zone = str.substring(si + 1);
                } else {
                    si = str.length() - 1;
                }
                int ti = str.lastIndexOf(' ', si - 1);
                if (ti != -1) {
                    time = str.substring(ti, si).trim();
                }
                date = str.substring(di + 1, ti).trim();
                if (date.length() > 0) {
                    if (cal == null) {
                        cal = Calendar.getInstance();
                    }
                    if (zone != null) {
                        cal.setTimeZone(TimeZone.getTimeZone(zone));
                    }
                    String[] ds = split(date, ' ');
                    if(ds.length <= 1){
                        ds = split(date,'-');
                    }
                    int day = -1;
                    int month = -1;
                    int year = -1;
                    if (ds != null && ds.length > 0) {
//                        day = Integer.parseInt(ds[0]);
//                        month = parseMonth(ds[1]);
//                        year = Integer.parseInt(ds[2]);
                        int cc = 0;
                        String s;
                        for (int i = 0; i < ds.length; i++) {
                            s = ds[i];
                            if (s.length() == 0) {
                                continue;
                            }
                            s = s.trim();
                            switch (cc) {
                                case 0:
                                    day = Integer.parseInt(s);
                                    break;
                                case 1:
                                    month = parseMonth(s);
                                    break;
                                case 2:
                                    year = Integer.parseInt(s);
                                    break;
                            }
                            cc++;
                        }
                    } else {
                        day = Integer.parseInt(date.substring(0, 2));
                        month = parseMonth(date.substring(3, 6));
                        year = Integer.parseInt(date.substring(7));
                    }
                    year = year < 100 ? year + 1900 : year;
                    int h = 0, m = 0, s = 0;
                    if (time.length() > 0) {
                        String[] ts = split(time, ':');
                        if (ts != null && ts.length > 0) {
//                            h = Integer.parseInt(ts[0]);
//                            m = Integer.parseInt(ts[1]);
//                            s = Integer.parseInt(ts[2]);
                            int cc = 0;
                            String ss;
                            for (int i = 0; i < ts.length; i++) {
                                ss = ts[i];
                                if (ss.length() == 0) {
                                    continue;
                                }
                                ss = ss.trim();
                                switch (cc) {
                                    case 0:
                                        h = Integer.parseInt(ss);
                                        break;
                                    case 1:
                                        m = Integer.parseInt(ss);
                                        break;
                                    case 2:
                                        s = Integer.parseInt(ss);
                                        break;
                                }
                                cc++;
                            }
                        } else {
                            h = Integer.parseInt(time.substring(0, 2));
                            m = Integer.parseInt(time.substring(3, 5));
                            s = Integer.parseInt(time.substring(6, 8));
                        }
                    }
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.DATE, day);
                    cal.set(Calendar.HOUR_OF_DAY, h);
                    cal.set(Calendar.MINUTE, m);
                    cal.set(Calendar.SECOND, s);
                    lret = cal.getTime().getTime();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return lret;
    }

    /**
     * Parse method with english to int
     *
     * @param smonth Month english
     * @return int with Calendar
     */
    private static int parseMonth(String smonth) {
        int ret = -1;
        if (smonth != null) {
            smonth = smonth.toLowerCase();
            if (smonth.startsWith("jan")) {
                ret = Calendar.JANUARY;
            } else if (smonth.startsWith("feb")) {
                ret = Calendar.FEBRUARY;
            } else if (smonth.startsWith("mar")) {
                ret = Calendar.MARCH;
            } else if (smonth.startsWith("apr")) {
                ret = Calendar.APRIL;
            } else if (smonth.startsWith("may")) {
                ret = Calendar.MAY;
            } else if (smonth.startsWith("jun")) {
                ret = Calendar.JUNE;
            } else if (smonth.startsWith("jul")) {
                ret = Calendar.JULY;
            } else if (smonth.startsWith("aug")) {
                ret = Calendar.AUGUST;
            } else if (smonth.startsWith("sep")) {
                ret = Calendar.SEPTEMBER;
            } else if (smonth.startsWith("oct")) {
                ret = Calendar.OCTOBER;
            } else if (smonth.startsWith("nov")) {
                ret = Calendar.NOVEMBER;
            } else if (smonth.startsWith("dec")) {
                ret = Calendar.DECEMBER;
            } else if (smonth.startsWith("mon")) {
                ret = Calendar.MONDAY;
            } else if (smonth.startsWith("tue")) {
                ret = Calendar.TUESDAY;
            } else if (smonth.startsWith("wed")) {
                ret = Calendar.WEDNESDAY;
            } else if (smonth.startsWith("thu")) {
                ret = Calendar.THURSDAY;
            } else if (smonth.startsWith("fri")) {
                ret = Calendar.FRIDAY;
            } else if (smonth.startsWith("sat")) {
                ret = Calendar.SATURDAY;
            } else if (smonth.startsWith("sun")) {
                ret = Calendar.SUNDAY;
            }
        }
        return ret;
    }

    /**
     * test system support encoding ?
     *
     * @param encoding encoding will check
     * @return support?
     */
    public static boolean isSupportEncoding(String encoding) {
        boolean bret = false;
        try {
            byte[] bytes = "中文".getBytes(encoding);
            String s = new String(bytes, encoding);
            bret = "中文".equals(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bret;
    }
}
