package com.vhly.epubmaker.reader;

import com.vhly.epubmaker.epub.EPubFile;
import com.vhly.epubmaker.epub.content.Chapter;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-18
 * Email: vhly@163.com
 */
public class EPubReader implements EPubContentHandler, TreeSelectionListener {
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

    private String tempStorePath;

    public EPubReader() {

        chapterList.addTreeSelectionListener(this);

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
                    ChapterTreeModel model = new ChapterTreeModel(currentFile);
                    chapterList.setModel(model);
                    chapterList.updateUI();
                }
            });
        }
    }

    /**
     * Notify host receive epub file contents temp store path
     *
     * @param path String, file system path,
     */
    public void updateTempStorePath(String path) {
        tempStorePath = path;
    }

    public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
        TreePath path = chapterList.getSelectionPath();
        Object o = path.getLastPathComponent();
        if(o != null && o instanceof DefaultMutableTreeNode){
            DefaultMutableTreeNode tn = (DefaultMutableTreeNode) o;
            Object uo = tn.getUserObject();
            if(uo != null && uo instanceof Chapter){
                Chapter ch = (Chapter) uo;
                contentShower.setText(ch.getPageContent());
            }
        }
    }
}
