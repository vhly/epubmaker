package com.vhly.epubmaker;

import net.dratek.browser.util.StreamUtil;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.util.PDFText2HTML;

import java.awt.print.Printable;
import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-12-16
 * Email: vhly@163.com
 */
public class PDFConvertor {
    public static void main(String[] args) {
        int argc = args.length;
        if (argc == 2) {
            // TODO Convert PDF
            String sin, sout;
            sin = args[0];
            sout = args[1];
            File f = new File(sin);
            if (f.exists() && f.canRead()) {
                FileInputStream fin = null;
                FileOutputStream fout = null;
                OutputStreamWriter ow = null;
                try {
                    fin = new FileInputStream(f);
                    PDFParser parser = new PDFParser(fin);
                    parser.parse();
                    PDDocument pdf = parser.getPDDocument();
                    AccessPermission currentAccessPermission = pdf.getCurrentAccessPermission();
                    boolean canExtract = currentAccessPermission.canExtractContent();
                    boolean encrypted = pdf.isEncrypted();
                    if (!encrypted && canExtract) {
                        int npages = pdf.getNumberOfPages();
                        PDFText2HTML convert = new PDFText2HTML("UTF-8");
                        convert.setForceParsing(true);
                        convert.setStartPage(1);
                        convert.setEndPage(npages);


                        fout = new FileOutputStream(sout);
                        ow = new OutputStreamWriter(fout,"UTF-8");
                        convert.writeText(pdf,ow);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    StreamUtil.close(fin);
                    StreamUtil.close(ow);
                    StreamUtil.close(fout);
                }
            }
        } else {
            System.out.println("Usage PDFConvertor <input pdf file> <output epub file> ");
        }
    }
}
