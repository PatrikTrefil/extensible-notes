package com.patriktrefil.java.plugins.texteditor;

import com.patriktrefil.java.PreferenceKeys;
import com.patriktrefil.java.plugins.commandPalette.include.IncludeInCommandPalette;
import com.patriktrefil.java.plugins.menubar.include.IncludeInMenubar;
import com.patriktrefil.java.plugins.menubar.include.MenubarCategories;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.prefs.Preferences;

/**
 * Running this action will open a note.
 */
public class OpenNoteAction extends AbstractAction implements IncludeInMenubar, IncludeInCommandPalette {
    private File fileToOpen;

    @Override
    public MenubarCategories group() {
        return MenubarCategories.FILE;
    }

    static final private String name = "Open";

    /**
     * Once the action is run, a file chooser dialog will be shown to the user.
     */
    public OpenNoteAction() {
        super(name);
    }

    /**
     * Providing a file to open will skip the file chooser dialog.
     */
    public OpenNoteAction(final File fileToOpen) {
        super(name);
        this.fileToOpen = fileToOpen;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (fileToOpen == null) {
            final var fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(Preferences.userRoot().get(PreferenceKeys.BASE_DIR, ".")));
            switch (fileChooser.showDialog(null, "Open")) {
                case JFileChooser.APPROVE_OPTION -> {
                    fileToOpen = fileChooser.getSelectedFile();
                }
                case JFileChooser.CANCEL_OPTION -> {
                    return;
                }
                case JFileChooser.ERROR_OPTION -> {
                    JOptionPane.showMessageDialog(null, "Opening of file failed", "Open file failure",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        System.out.println("Opening file: " + fileToOpen.getPath());
        final var textEditor = TextEditor.getInstance();
        if (textEditor.isDirty()) {
            System.out.println("File is dirty, saving before opening");
            final var currentFile = textEditor.getCurrentFile();
            if (currentFile == null) {
                final var confirmationResult = JOptionPane.showConfirmDialog(null, "Current file is not saved, save " +
                                "before " +
                                "opening?", "Save before open",
                        JOptionPane.YES_NO_OPTION);
                switch (confirmationResult) {
                    case JOptionPane.YES_OPTION -> {
                        new SaveNoteAction().actionPerformed(e);
                    }
                    case JOptionPane.NO_OPTION -> {
                        // Do nothing
                    }
                    case JOptionPane.CANCEL_OPTION -> {
                        return;
                    }
                }
            } else {
                new SaveNoteAction().actionPerformed(e);
            }

        }
        try {
            textEditor.editorPane.setText(
                    new String(Files.readAllBytes(fileToOpen.toPath())
                    ));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Failed to open file", "Open file failure", JOptionPane.ERROR_MESSAGE);
        }
        textEditor.setDirty(false);
        textEditor.setCurrentFile(fileToOpen);
    }

    @Override
    public String commandNameInCommandPalette() {
        return "Open note";
    }
}
