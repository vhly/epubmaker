package com.vhly.epubmaker;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 13-12-11
 * Email: vhly@163.com
 */
public class TextEncodingConvert {
    public static void main(String[] args) {
        int argc = args.length;
        if (argc == 4) {
            String fn = args[0];
            String inEncoding = args[1];
            String outEncoding = args[2];
            String outPath = args[3];

            File inFile = new File(fn);
            if (inFile.exists()) {
                File outDir = new File(outPath);
                boolean bok = true;
                if (!outDir.exists()) {
                    bok = outDir.mkdirs();
                }
                bok = bok && outDir.canWrite();

                if (bok) {

                    String targetName = inFile.getName();
                    File outFile = new File(outDir, targetName);
                    if (!outFile.exists()) {
                        try {
                            bok = outFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (bok) {
                        FileInputStream fin = null;
                        InputStreamReader ir = null;
                        BufferedReader br = null;

                        FileOutputStream fout = null;
                        OutputStreamWriter ow = null;
                        PrintWriter pw = null;
                        try {
                            fin = new FileInputStream(inFile);
                            ir = new InputStreamReader(fin, inEncoding);
                            br = new BufferedReader(ir);

                            fout = new FileOutputStream(outFile);
                            ow = new OutputStreamWriter(fout, outEncoding);
                            pw = new PrintWriter(ow);

                            String line = null;
                            while (true) {
                                line = br.readLine();
                                if (line == null) {
                                    break;
                                }
                                pw.print(line);
                                pw.print("\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            StreamUtil.close(br);
                            StreamUtil.close(ir);
                            StreamUtil.close(fin);
                            br = null;
                            ir = null;
                            fin = null;

                            StreamUtil.close(pw);
                            StreamUtil.close(ow);
                            StreamUtil.close(fout);
                            pw = null;
                            ow = null;
                            fout = null;

                        }
                    }
                }
            } else {
                System.out.println("File not exists: " + inFile.getAbsolutePath());
            }
        } else {
            System.out.println("Usage: TextEncodingConvert <txtfile> <encoding> <target encoding> <target folder>");
        }
    }
}
