package com.patriktrefil.java.plugins.noteExplorer;

import com.patriktrefil.java.plugins.Plugin;

import javax.swing.*;

/**
 * Plugin that creates a note explorer window
 */
public class NoteExplorerPlugin implements Plugin {
    private final NoteExplorer noteExplorer = NoteExplorer.getInstance();

    @Override
    public String getPluginName() {
        return "Note explorer";
    }

    @Override
    public void beforeFirstDraw(final JFrame frame) {
        noteExplorer.createGUI(frame);
    }

    @Override
    public void afterFirstDraw(final JFrame frame) {
        assert noteExplorer != null;
        new ScanFileSystemRunnable(noteExplorer.rootFile, noteExplorer.rootNode).run();
        noteExplorer.treeModel.reload();
    }


}