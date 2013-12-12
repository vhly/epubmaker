package com.vhly.cms.clients.bookuploader;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.representation.Form;

import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 13-12-8
 * Email: vhly@163.com
 */
public class Uploader {
    private static void createNewChapter(Client client, String bid, String title, long index, String content) {
        if (client != null) {
            if (title != null && bid != null) {
                Form data = new Form();
                data.putSingle("title", title);
                data.putSingle("index", Long.toString(index));
                data.putSingle("content", content);

                WebResource resource = client.resource("http://localhost:8082/api/book/" + bid + "/chapter");
                String ss = resource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).put(String.class, data);
                System.out.println("ss = " + ss);
            }
        }
    }

    public static void main(String[] args) {
        int argc = args.length;
        if (argc != 2) {
            System.out.println("Usage: Uploader <bid> <txt file>");
        } else {

            String bid = args[0];

            File file = new File(args[1]);
            if (file.exists() && file.canRead()) {
//                String name = file.getName();
                StringBuilder sb = new StringBuilder();

                FileReader fr = null;
                BufferedReader br = null;

                String line = null;

                Client client = Client.create();
                client.addFilter(new HTTPBasicAuthFilter("vhly", "GooD1234"));
//                Scanner scanner = new Scanner(System.in);

                try {
                    fr = new FileReader(file);
                    br = new BufferedReader(fr);
                    Pattern pattern = Pattern.compile("第.*章.*\\S");
                    Matcher matcher = pattern.matcher("abc");
                    Pattern pattern2 = Pattern.compile("千.*章.*\\S");
                    Matcher matcher2 = pattern2.matcher("abc");
                    int titleCount = 0;

                    String title = "Untitled";
                    while (true) {
                        line = br.readLine();
                        if (line == null) {
                            if (sb.length() > 0) {
                                String content = sb.toString();
                                createNewChapter(client, bid, title, titleCount, content);
                                sb.setLength(0);
                            }
                            break;
                        }
                        line = line.replaceAll(">", "&gt;");
                        line = line.replaceAll("<", "&lt;");
                        line = line.replaceAll("&", "&amp;");
                        line = line.trim();
                        if (line.length() > 0) {
                            matcher.reset(line);
                            if (matcher.find()) {
                                if (sb.length() > 0) {
                                    String content = sb.toString();
                                    createNewChapter(client, bid, title, titleCount, content);
                                    sb.setLength(0);
                                }
                                titleCount++;
                                int start = matcher.start();
                                int end = matcher.end();
                                title = line.substring(start, end);
                                sb.append(title).append('\n');
                            } else {
                                matcher2.reset(line);
                                if (matcher2.find()) {
                                    if (sb.length() > 0) {
                                        String content = sb.toString();
                                        createNewChapter(client, bid, title, titleCount, content);
                                        sb.setLength(0);
                                    }
                                    titleCount++;
                                    int start = matcher2.start();
                                    int end = matcher2.end();
                                    title = line.substring(start, end);
                                    sb.append(title).append('\n');
                                } else {
                                    sb.append(line).append('\n');
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fr != null) {
                        try {
                            fr.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }


    }

    private static void printFoot(StringBuilder pw) {
        if (pw != null) {
            pw.append("</body></html>");
        }
    }

    private static void printHead(StringBuilder pw, String title) {
        if (pw != null) {
            pw.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            pw.append("<!DOCTYPE html\n" +
                    "        PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
                    "        \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">");
            pw.append("<head>");
            if (title != null) {
                pw.append("<title>").append(title).append("</title>");
            }
            pw.append("<meta name=\"author\" content=\"ePubMaker(vhly)\"/>");
            pw.append("</head>");
            pw.append("<body>\n");
            pw.append("<h2>").append(title).append("</h2>");
        }
    }

    private static String toLongString(int index) {
        StringBuilder sb = new StringBuilder();
        String si = Integer.toString(index);
        int len = si.length();
        if (len < 5) {
            int cc = 5 - len;
            for (int i = 0; i < cc; i++) {
                sb.append('0');
            }
            sb.append(si);
        } else {
            sb.append(si);
        }
        String ret = sb.toString();
        sb = null;
        return ret;
    }

}
