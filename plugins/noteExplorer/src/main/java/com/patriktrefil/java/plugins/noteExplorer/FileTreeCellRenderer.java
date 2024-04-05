package com.patriktrefil.java.plugins.noteExplorer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * Custom tree cell renderer for the file tree.
 * Same as the default cell renderer, but it displays empty directories with folder icon.
 */
class FileTreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel,
                                                  final boolean expanded,
                                                  final boolean leaf, final int row, final boolean hasFocus) {
        // Keep most rendering as default
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        if (value instanceof DefaultMutableTreeNode node) {
            final var userObject = node.getUserObject();
            if (userObject instanceof FileNode fileNode) {
                // Display empty directories with folder icon
                if (fileNode.file().isDirectory() && !node.children().hasMoreElements())
                    setIcon(UIManager.getIcon("Tree.closedIcon"));
            } else {
                throw new RuntimeException("Unexpected user object in tree: " + userObject.getClass().getName());
            }
        } else {
            throw new RuntimeException("Unexpected tree node type: " + value.getClass().getName());
        }

        return this;
    }

}
