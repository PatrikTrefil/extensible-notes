package com.patriktrefil.java.plugins.texteditor;

import com.patriktrefil.java.PreferenceKeys;
import com.patriktrefil.java.plugins.commandPalette.include.IncludeInCommandPalette;
import com.patriktrefil.java.plugins.menubar.include.IncludeInMenubar;
import com.patriktrefil.java.plugins.menubar.include.MenubarCategories;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class SaveNoteAction extends AbstractAction implements IncludeInMenubar, IncludeInCommandPalette {
    private File saveContentsHere;
    private static final List<Runnable> afterSaveHooks = new ArrayList<>();

    /**
     * Add a hook that will be run after a new note is created.
     */
    public static void addAfterSaveHook(Runnable hook) {
        afterSaveHooks.add(hook);
    }

    @Override
    public MenubarCategories group() {
        return MenubarCategories.FILE;
    }

    public SaveNoteAction() {
        super("Save as");
    }

    public SaveNoteAction(File saveContentsHere) {
        super("Save");
        this.saveContentsHere = saveContentsHere;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        System.out.println("Saving file called");
        if (saveContentsHere == null) {
            final var fileChooser = new JFileChooser();
            final var htmlFilter = new FileNameExtensionFilter("html file", "html");
            fileChooser.setFileFilter(htmlFilter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setCurrentDirectory(new File(Preferences.userRoot().get(PreferenceKeys.BASE_DIR, ".")));
            switch (fileChooser.showDialog(null, "Save as")) {
                case JFileChooser.APPROVE_OPTION -> {
                    final var selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile.getName().endsWith(".html"))
                        this.saveContentsHere = selectedFile;
                    else
                        this.saveContentsHere = new File(selectedFile.getAbsolutePath() + ".html");

                }
                case JFileChooser.CANCEL_OPTION -> {
                    return;
                }
                case JFileChooser.ERROR_OPTION -> {
                    JOptionPane.showMessageDialog(null, "Failed to save file here", "Failed to save file",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        System.out.println("Saving file: " + saveContentsHere.getAbsolutePath());
        final var textEditor = TextEditor.getInstance();
        final var editorPane = textEditor.editorPane;
        try {
            try (final var outputStream = new FileOutputStream(saveContentsHere)) {
                editorPane.getEditorKit().write(outputStream, editorPane.getDocument(), 0,
                        editorPane.getDocument().getLength());
            }
        } catch (IOException | BadLocationException ex) {
            JOptionPane.showMessageDialog(null, "Failed to save file", "Failed to save file",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        textEditor.setDirty(false);
        textEditor.setCurrentFile(saveContentsHere);

        afterSaveHooks.forEach(Runnable::run);
    }

    @Override
    public String commandNameInCommandPalette() {
        return "Save note";
    }
}
