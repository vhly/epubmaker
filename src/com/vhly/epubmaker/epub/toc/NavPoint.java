package com.vhly.epubmaker.epub.toc;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-13
 * Email: vhly@163.com
 */

/**
 * NavPoint for NCX<br/>
 * ChangeList:<br/>
 * <ol>
 * <li>Add Sub NavPoint support</li>
 * </ol>
 */
public class NavPoint {
    public String id;
    public String playOrder;
    public String label;
    public String content;
    public int iplayOrder;

    private Vector<NavPoint> subs;

    public NavPoint() {
        subs = new Vector<NavPoint>();
    }

    public NavPoint(String id, String label, String content, int iplayOrder) {
        this.id = id;
        this.label = label;
        this.content = content;
        this.iplayOrder = iplayOrder;
        subs = new Vector<NavPoint>();
    }

    public String toXML() {
        String ret;
        StringBuffer sb = new StringBuffer();
        toXML(sb);
        ret = sb.toString();
        return ret;
    }

    private void toXML(StringBuffer sb) {
        if (sb != null) {
            sb.append("<navPoint class=\"chapter\" id=\"").append(id).append("\" playOrder=\"").append(iplayOrder).append("\">");
            sb.append("<navLabel><text>").append(label).append("</text></navLabel>");
            sb.append("<content src=\"").append(content).append("\"/>");
            for (NavPoint np : subs) {
                sb.append(np.toXML());
            }
            sb.append("</navPoint>");
        }
    }

    public String updateOrder(String it) {
        iplayOrder = Integer.parseInt(it);
        int dit = iplayOrder + 1;
        return Integer.toString(dit);
    }
}
