package com.vhly.epubmaker;

import java.io.*;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 13-12-11
 * Email: vhly@163.com
 */
public class TextEncodingConvert {
    public static void main(String[] args) {
        String inputFolder = null;
        String outputFolder = null;
        String inEncoding = null;
        String outEncoding = null;
        int argc = args.length;
        switch (argc) {
            case 1:
                inputFolder = args[0];
                break;
            case 2:
                inputFolder = args[0];
                inEncoding = args[1];
                break;
            case 3:
                inputFolder = args[0];
                inEncoding = args[1];
                outputFolder = args[2];
                break;
            case 4:
                inputFolder = args[0];
                inEncoding = args[1];
                outputFolder = args[2];
                outEncoding = args[3];
                break;
        }
        String line = null;
        Scanner scanner = new Scanner(System.in);
        if (inputFolder == null) {
            System.out.println("Input Text files folder to convert");
            System.out.print(":");
            while (true) {
                line = scanner.nextLine();
                line = line.trim();
                if (line.length() > 0) {
                    inputFolder = line;
                    break;
                }
                System.out.print(":");
            }
        }

        if (inEncoding == null) {
            System.out.println("Input Text files Encoding");
            System.out.print(":");
            while (true) {
                line = scanner.nextLine();
                line = line.trim();
                if (line.length() > 0) {
                    inEncoding = line;
                    break;
                }
                System.out.print(":");
            }
        }

        if (outputFolder == null) {
            System.out.println("Input output file's folder");
            System.out.print(":");
            while (true) {
                line = scanner.nextLine();
                line = line.trim();
                if (line.length() > 0) {
                    outputFolder = line;
                    break;
                }
                System.out.print(":");
            }
        }

        if (outEncoding == null) {
            System.out.println("Input output file's Encoding");
            System.out.print(":");
            while (true) {
                line = scanner.nextLine();
                line = line.trim();
                if (line.length() > 0) {
                    outEncoding = line;
                    break;
                }
                System.out.print(":");
            }
        }

        File inF = new File(inputFolder);
        if (inF.exists() && inF.canRead()) {
            File outF = new File(outputFolder);
            boolean bok = true;
            if (!outF.exists()) {
                bok = outF.mkdirs();
            }
            bok = bok && outF.canWrite();
            if (bok) {
                File[] files = inF.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            String[] parameters = new String[4];
                            parameters[0] = file.getAbsolutePath();
                            parameters[1] = inEncoding;
                            parameters[2] = outEncoding;
                            parameters[3] = outputFolder;
                            convertEncoding(parameters);
                        }
                    }
                }
            } else {
                System.out.println("Output Folder is not exists or can't write!");
            }
        } else {
            System.out.println("Input Folder is not exists or can't read!");
        }
    }


    private static void convertWithInput() {
        Scanner scanner = new Scanner(System.in);

        String[] parameters = new String[4];
        String lastEncoding = "";
        String targetEncoding = "";
        String lastDir = "";
        while (true) {
            System.out.print("Input File('quit' for exit):");
            String inputFile = scanner.nextLine();
            if (inputFile.toLowerCase().trim().equals("quit")) {
                break;
            }

            String prefix = "Input File Encoding";
            System.out.print(prefix);
            if (lastEncoding.length() > 0) {
                String et = "[" + lastEncoding + "]";
                System.out.print(et);
                et = null;
            }
            System.out.print(":");
            lastEncoding = scanner.nextLine();

            prefix = "Input target Encoding";
            if (targetEncoding.length() > 0) {
                prefix = prefix + "[" + targetEncoding + "]";
            }
            prefix += ":";
            System.out.print(prefix);
            targetEncoding = scanner.nextLine();

            prefix = "Input target Folder";
            if (lastDir.length() > 0) {
                prefix = prefix + "[" + lastDir + "]";
            }
            prefix += ":";
            System.out.print(prefix);
            lastDir = scanner.nextLine();

            parameters[0] = inputFile;
            parameters[1] = lastEncoding;
            parameters[2] = targetEncoding;
            parameters[3] = lastDir;
            convertEncoding(parameters);
        }
    }

    private static void convertEncoding(String[] args) {
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
                                line = line.trim();
                                if (line.length() > 0) {
                                    pw.print(line);
                                    pw.print("\n");
                                }
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
