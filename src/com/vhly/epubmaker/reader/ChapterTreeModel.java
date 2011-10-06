package com.vhly.epubmaker.reader;

import com.vhly.epubmaker.epub.EPubFile;
import com.vhly.epubmaker.epub.content.Chapter;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * Created by IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 11-9-18
 * Email: vhly@163.com
 */
public class ChapterTreeModel extends DefaultTreeModel {

    public ChapterTreeModel(EPubFile file) {
        super(new DefaultMutableTreeNode("Chapters"));
        processEPubFile(file);
    }

    /**
     * Process epub's chapters
     *
     * @param file EPubFile
     */
    private void processEPubFile(EPubFile file) {
        if (file != null) {
            Chapter[] chapters = file.getChapters();
            if (chapters != null) {
                int index = 0;
                String title;
                StringBuffer sb = new StringBuffer();
                DefaultMutableTreeNode r = (DefaultMutableTreeNode) root;

                for (Chapter ch : chapters) {
                    title = ch.getTitle();
                    index++;
                    if (title == null) {
                        sb.setLength(0);
                        sb.append("Chapter ").append(index);
                        title = sb.toString();
                    }
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(ch);

                    r.add(node);
                }
            }
        }
    }
}
