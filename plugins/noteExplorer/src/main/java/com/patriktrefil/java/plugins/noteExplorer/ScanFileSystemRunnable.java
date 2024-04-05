package com.patriktrefil.java.plugins.noteExplorer;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

/**
 * Runnable that recursively scans the file system and updates the tree model.
 * Does *not* reload the tree view.
 */
class ScanFileSystemRunnable implements Runnable {
    private final DefaultMutableTreeNode treeRoot;
    private final File fileRoot;

    public ScanFileSystemRunnable(final File fileRoot,
                                  final DefaultMutableTreeNode root) {
        this.fileRoot = fileRoot;
        this.treeRoot = root;
    }

    @Override
    public void run() {
        System.out.println("Scanning file system");
        createChildren(fileRoot, treeRoot);
    }

    private void createChildren(final File fileRoot,
                                final DefaultMutableTreeNode node) {
        var files = fileRoot.listFiles();
        if (files == null) return;

        for (var file : files) {
            var childNode =
                    new DefaultMutableTreeNode(new FileNode(file));
            node.add(childNode);
            if (file.isDirectory()) {
                createChildren(file, childNode);
            }
        }
    }

}
