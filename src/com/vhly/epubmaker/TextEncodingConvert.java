package com.vhly.epubmaker;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 13-12-11
 * Email: vhly@163.com
 */
public class TextEncodingConvert {

    private static HashMap<String, String> rechars;

    static {
        rechars = new HashMap<String, String>();
        rechars.put("sao", "骚");
        rechars.put("sāo", "骚");
        rechars.put("se", "色");
        rechars.put("sè", "色");
        rechars.put("nai", "奶");
        rechars.put("yan", "艳");
        rechars.put("bang", "棒");
        rechars.put("bāng", "棒");
        rechars.put("tian", "舔");
        rechars.put("netbsp;", "");
        rechars.put("xùe", "穴");
        rechars.put("féi", "肥");
        rechars.put("fù", "妇");
        rechars.put("you", "诱");
        rechars.put("yòu", "诱");
        rechars.put("比门g", "比蒙");
        rechars.put("zou光", "走光");
        rechars.put("rǔ", "乳");
        rechars.put("yīn", "阴");
        rechars.put("máo", "毛");
        rechars.put("chōu", "抽");
        rechars.put("shè", "射");
        rechars.put("bī", "逼");
        rechars.put("�", "");
        rechars.put("xiǎo", "小");
        rechars.put("www.WenXueMi.CoM", "");
        rechars.put("Qisuu．ｃｏｍ", "");
        rechars.put("ｗＷｗ．Qisuu．ｃｏＭ", "");
        rechars.put("☆奇书网のWww.Qisuu.Com★", "");
        rechars.put("cào", "操");
        rechars.put("cao", "操");
        rechars.put("chuáng", "床");
        rechars.put("nv", "女");
        rechars.put("</div>", "");
        rechars.put("<div>", "");
        rechars.put("<hr/>", "");
        rechars.put("dàng", "荡");
        rechars.put("xiōng", "胸");
        rechars.put("mō", "摸");
        rechars.put("1ù", "露");
        rechars.put("xìng", "性");
        rechars.put("jīng", "精");
        rechars.put("nòng", "弄");
        rechars.put("méng", "蒙");
        rechars.put("hún", "混");
        rechars.put("mén", "门");
        rechars.put("tǐng", "挺");
        rechars.put("huā", "花");
        rechars.put("shì", "士");
        rechars.put("tuǐ", "腿");
        rechars.put("太zi", "太子");
        rechars.put("yin", "阴");
        rechars.put("jiān", "奸");
        rechars.put("sī", "私");
        rechars.put("jiāo", "交");
        rechars.put("luàn", "乱");
        rechars.put("jī", "激");
        rechars.put("kù", "裤");
        rechars.put("mí", "迷");
        rechars.put("&quot;", "");
        rechars.put("rou", "肉");
        rechars.put("chūn", "春");
        rechars.put("fen", "粉");
        rechars.put("luo", "裸");
        rechars.put("yu", "欲");
        rechars.put("quan家", "全家");
        rechars.put("缠mian", "缠绵");
        rechars.put("han", "含");
        rechars.put("dao", "倒");
        rechars.put("guang", "光");
        rechars.put("小荷作文网www.zww.cn", "");
        rechars.put("暧mei", "暧昧");
        rechars.put("shen", "身");
        rechars.put("guang", "光");
        rechars.put("qing", "情");
        rechars.put("róu", "揉");
        rechars.put("tún", "臀");
        rechars.put("wěn", "吻");
    }

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
                            String name = file.getName();
                            if (name.endsWith(".txt")) {
                                String[] parameters = new String[4];
                                parameters[0] = file.getAbsolutePath();
                                parameters[1] = inEncoding;
                                parameters[2] = outEncoding;
                                parameters[3] = outputFolder;
                                convertEncoding(parameters);
                            }
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
                            int count = 0;
                            while (true) {
                                bok = true;
                                line = br.readLine();
                                if (line == null) {
                                    break;
                                }
                                line = line.trim();
                                if (line.length() > 0) {
                                    count++;
                                    if (count < 10) {
                                        if (line.startsWith("声明:本书")) {
                                            bok = false;
                                        }
                                    }
                                    if (bok) {
                                        if (line.contains("[W")) {
                                            int index = line.indexOf("[W");
                                            int i2 = line.indexOf("]", index);
                                            String s1 = line.substring(0, index);
                                            line = s1 + line.substring(i2 + 1);
                                        } else if (line.contains("[奇书网")) {
                                            int index = line.indexOf("[奇书网");
                                            int i2 = line.indexOf("]", index);
                                            String s1 = line.substring(0, index);
                                            line = s1 + line.substring(i2 + 1);
                                        } else if (line.contains("[Q")) {
                                            int index = line.indexOf("[Q");
                                            int i2 = line.indexOf("]", index);
                                            String s1 = line.substring(0, index);
                                            line = s1 + line.substring(i2 + 1);
                                        }
                                        line = replaceWith(line);
                                        pw.print(line);
                                        pw.print("\n");
                                    }
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

    private static String replaceWith(String line) {
        if (line != null) {
            Set<String> keys = rechars.keySet();
            for (String key : keys) {
                String value = rechars.get(key);
                line = line.replaceAll(key, value);
            }
        }
        return line;
    }
}
