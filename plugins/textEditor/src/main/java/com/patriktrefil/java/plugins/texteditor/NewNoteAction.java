package com.patriktrefil.java.plugins.texteditor;

import com.patriktrefil.java.PreferenceKeys;
import com.patriktrefil.java.plugins.commandPalette.include.IncludeInCommandPalette;

import java.util.prefs.Preferences;

import com.patriktrefil.java.plugins.menubar.include.IncludeInMenubar;
import com.patriktrefil.java.plugins.menubar.include.MenubarCategories;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Running this action will create a new note.
 */
public class NewNoteAction extends AbstractAction implements IncludeInMenubar, IncludeInCommandPalette {
    private static final String defaultNoteExtension = ".html";
    private static final List<Runnable> afterCreateHooks = new ArrayList<>();

    /**
     * Add a hook that will be run after a new note is created.
     */
    public static void addAfterCreateHook(final Runnable hook) {
        afterCreateHooks.add(hook);
    }

    @Override
    public MenubarCategories group() {
        return MenubarCategories.FILE;
    }

    public NewNoteAction() {
        super("New");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Creating new note");
        try {
            final var noteName = JOptionPane.showInputDialog(null, "New note name", "New note",
                    JOptionPane.QUESTION_MESSAGE);
            if (noteName == null) {
                System.out.println("User cancelled note creation");
                return;
            }
            final var newNote =
                    new File("%s/%s%s".formatted(
                            Preferences.userRoot().get(PreferenceKeys.BASE_DIR, "./"),
                            noteName,
                            defaultNoteExtension
                    ));

            System.out.println("New noted will be created at " + newNote.getAbsolutePath());

            var alreadyExists = !(newNote.createNewFile());

            if (alreadyExists) {
                JOptionPane.showMessageDialog(null, "Failed to create a new note, because it already exists",
                        "Note creation failure", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Failed to create a new note", "Note creation failure",
                    JOptionPane.ERROR_MESSAGE);
        }

        afterCreateHooks.forEach(Runnable::run);
    }

    @Override
    public String commandNameInCommandPalette() {
        return "New note";
    }
}
