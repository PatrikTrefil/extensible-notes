package com.patriktrefil.java.plugins.noteExplorer;

import com.patriktrefil.java.plugins.commandPalette.include.IncludeInCommandPalette;
import com.patriktrefil.java.plugins.menubar.include.IncludeInMenubar;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Action that toggles the visibility of the note explorer
 */
public class ToggleFileExplorerAction extends AbstractAction implements IncludeInMenubar, IncludeInCommandPalette {
    private final NoteExplorer noteExplorer = NoteExplorer.getInstance();

    public ToggleFileExplorerAction() {
        super("Toggle file explorer");
    }

    @Override
    public String commandNameInCommandPalette() {
        return "Toggle file explorer";
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        System.out.println("Toggling file explorer");
        if (noteExplorer.parentSplitPane.getLeftComponent().isVisible()) {
            hideFileExplorer();
        } else {
            showFileExplorer();
        }
    }

    private void hideFileExplorer() {
        noteExplorer.parentSplitPane.getLeftComponent().setVisible(false);
        noteExplorer.parentSplitPane.setDividerSize(0);
    }

    private void showFileExplorer() {
        noteExplorer.parentSplitPane.getLeftComponent().setVisible(true);
        noteExplorer.parentSplitPane.setDividerLocation(noteExplorer.parentSplitPane.getLastDividerLocation());
        noteExplorer.parentSplitPane.setDividerSize((Integer) UIManager.get("SplitPane.dividerSize"));
    }
}
