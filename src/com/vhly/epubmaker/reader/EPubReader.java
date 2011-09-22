package com.vhly.epubmaker.reader;

import com.vhly.epubmaker.epub.EPubFile;
import com.vhly.epubmaker.epub.content.Chapter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-18
 * Email: vhly@163.com
 */
public class EPubReader implements EPubContentHandler {
    private JButton openButton;
    private JTree chapterList;
    private JEditorPane contentShower;
    /**
     * Root Panel for gui
     */
    private JPanel gui;

    /**
     * Current EPubFile
     */
    private EPubFile currentFile;

    public EPubReader() {
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser chooser = new JFileChooser("Open EPub");
                chooser.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        boolean accept = false;
                        if (file != null) {
                            if (!file.isHidden()) {
                                if (file.isDirectory()) {
                                    accept = true;
                                } else {
                                    String path = file.getPath();
                                    path = path.toLowerCase();
                                    if (path.endsWith(".epub") && file.canRead()) {
                                        accept = true;
                                    }
                                }
                            }
                        }
                        return accept;
                    }

                    @Override
                    public String getDescription() {
                        return "EPub Files";
                    }
                });
                chooser.setMultiSelectionEnabled(false);
                int selectType = chooser.showOpenDialog(gui);
                if (selectType == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = chooser.getSelectedFile();
                    if (selectedFile != null) {
                        // TODO EPubFile load.
                        FileLoadThread thread = new FileLoadThread(selectedFile, EPubReader.this);
                        thread.start();
                    }
                }
            }
        });
        JFrame frame = new JFrame("EPub Reader");
        frame.getContentPane().add(gui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Notify host receive new epub file object.
     *
     * @param file EPubFile
     */
    public void updateEPubFile(EPubFile file) {
        // In background thread call this.
        if (file != null) {
            currentFile = file;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    chapterList.removeAll();
                    ChapterTreeModel model = new ChapterTreeModel(currentFile);
                    chapterList.setModel(model);
                    chapterList.updateUI();
                }
            });
        }
    }
}
