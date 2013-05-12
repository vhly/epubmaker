package com.vhly.epubmaker;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 13-5-12
 * Email: vhly@163.com
 */
public class TextTrimmer {
    public static void main(String[] args) {
        int argc = args.length;
        if (argc == 2) {
            String inFileName = args[0];
            String outFileName = args[1];

            File inFile = new File(inFileName);
            if (inFile.exists() && inFile.canRead()) {
                File outFile = new File(outFileName);
                boolean bok = false;
                if (!outFile.exists()) {
                    File parent = outFile.getParentFile();
                    parent.mkdirs();
                    try {
                        outFile.createNewFile();
                        bok = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    bok = true;
                }
                if (bok) {
                    if (outFile.canWrite()) {
                        FileInputStream fin = null;
                        InputStreamReader ir = null;
                        BufferedReader br = null;
                        PrintWriter pw = null;
                        OutputStreamWriter ow = null;
                        FileOutputStream fout = null;

                        try {
                            fin = new FileInputStream(inFile);
                            ir = new InputStreamReader(fin, "UTF-8");
                            br = new BufferedReader(ir);

                            fout = new FileOutputStream(outFile);
                            ow = new OutputStreamWriter(fout, "UTF-8");
                            pw = new PrintWriter(ow);

                            String line = null;

                            while (true) {
                                line = br.readLine();
                                if (line == null) {
                                    break;
                                }
                                line = line.trim();
                                if (line.length() > 0) {
                                    pw.println(line);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (br != null) {
                                try {
                                    br.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (ir != null) {
                                try {
                                    ir.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (fin != null) {
                                try {
                                    fin.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (pw != null) {
                                try {
                                    pw.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            if (ow != null) {
                                try {
                                    ow.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            if (fout != null) {
                                try {
                                    fout.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }


                        }

                    }
                }
            }
        }
    }
}
