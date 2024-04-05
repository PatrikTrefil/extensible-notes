package com.patriktrefil.java.plugins.texteditor;

import com.patriktrefil.java.plugins.commandPalette.include.IncludeInCommandPalette;
import com.patriktrefil.java.plugins.menubar.include.IncludeInMenubar;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ToggleHtmlRenderingAction extends AbstractAction implements IncludeInMenubar, IncludeInCommandPalette {
    private final TextEditor textEditor = TextEditor.getInstance();

    public ToggleHtmlRenderingAction() {
        super("Toggle HTML rendering");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        textEditor.toggleHtmlRendering();
    }

    @Override
    public String commandNameInCommandPalette() {
        return "Toggle HTML rendering";
    }
}
