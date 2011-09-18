package com.vhly.epubmaker.reader;

import com.vhly.epubmaker.epub.EPubFile;

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
    }
}
