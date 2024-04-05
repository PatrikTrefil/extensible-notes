package com.patriktrefil.java.plugins.noteExplorer;

import com.patriktrefil.java.PreferenceKeys;
import com.patriktrefil.java.plugins.texteditor.NewNoteAction;
import com.patriktrefil.java.plugins.texteditor.OpenNoteAction;
import com.patriktrefil.java.plugins.texteditor.SaveNoteAction;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import lombok.Getter;

/**
 * Singleton stateful class that represents the note explorer.
 */
class NoteExplorer {
    final File rootFile = new File(Preferences.userRoot().get(PreferenceKeys.BASE_DIR, "."));
    final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(new FileNode(rootFile));
    final DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
    @Getter
    static final NoteExplorer instance = new NoteExplorer();
    final JPanel parentPanel = new JPanel();
    JSplitPane parentSplitPane;

    private NoteExplorer() {
    }

    public void createGUI(final JFrame frame) {
        parentPanel.setBackground(java.awt.Color.LIGHT_GRAY);

        final var layout = new BoxLayout(parentPanel, BoxLayout.Y_AXIS);
        parentPanel.setLayout(layout);

        final var tree = createTree();

        final var treeModel = (DefaultTreeModel) tree.getModel();
        final var rootNode = (DefaultMutableTreeNode) treeModel.getRoot();
        final var rootFile = ((FileNode) rootNode.getUserObject()).file();

        final var headingPanel = new JPanel();
        final var headingLabel = new JLabel("Note explorer");
        final var refreshButton = createRefreshButton(rootNode, rootFile, treeModel);

        headingPanel.setBackground(Color.LIGHT_GRAY);
        headingPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headingPanel.add(headingLabel);
        headingPanel.add(refreshButton);
        headingPanel.setMaximumSize(headingPanel.getPreferredSize());

        final var treePanel = new JScrollPane(tree);

        parentPanel.add(headingPanel);
        parentPanel.add(treePanel);

        final Runnable fullRefresh = () -> {
            rootNode.removeAllChildren();
            (new ScanFileSystemRunnable(rootFile, rootNode)).run();
            treeModel.reload();
        };
        NewNoteAction.addAfterCreateHook(fullRefresh);
        SaveNoteAction.addAfterSaveHook(fullRefresh);

        parentSplitPane = (JSplitPane) frame.getContentPane().getComponent(0);
        parentSplitPane.add(parentPanel, JSplitPane.LEFT);
    }

    private JTree createTree() {
        final var tree = new JTree(this.treeModel);

        tree.setCellRenderer(new FileTreeCellRenderer());
        tree.setRootVisible(false);
        // Open note on selection
        tree.addTreeSelectionListener(e -> {
            var node = (DefaultMutableTreeNode)
                    tree.getLastSelectedPathComponent();
            if (node == null) return;
            var file = ((FileNode) node.getUserObject()).file();
            if (file.isFile()) {
                new OpenNoteAction(file).actionPerformed(null);
            }
        });
        tree.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    deleteHandler(tree);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });
        return tree;
    }

    private void deleteHandler(final JTree tree) {
        final var treePaths = tree.getSelectionPaths();
        if (treePaths == null) return;
        for (final var treePath : treePaths) {
            final var node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
            if (node == null) return;
            var file = ((FileNode) node.getUserObject()).file();
            if (file.isFile()) {
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + file.getName() + "?",
                        "Delete" +
                        " note", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    final var result = file.delete();
                    if (!result) {
                        JOptionPane.showMessageDialog(null, "Failed to delete note", "Note deletion failure",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    rootNode.remove(node);
                    treeModel.reload();
                }
            } else {
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this folder?",
                        "Delete folder", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    try {
                        FileUtils.deleteDirectory(file);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Failed to delete folder", "Folder deletion " +
                                        "failure",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    rootNode.remove(node);
                    treeModel.reload();
                }
            }
        }
    }

    private static JButton createRefreshButton(DefaultMutableTreeNode rootNode, File rootFile,
                                               DefaultTreeModel treeModel) {
        final var refreshButton = new JButton("⟳");
        refreshButton.setMargin(new Insets(0, 5, 0, 5));
        refreshButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rootNode.removeAllChildren();
                (new ScanFileSystemRunnable(rootFile, rootNode)).run();
                treeModel.reload();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        return refreshButton;
    }
}
