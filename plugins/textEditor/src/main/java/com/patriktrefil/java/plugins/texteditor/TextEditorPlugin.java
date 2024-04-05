package com.patriktrefil.java.plugins.texteditor;

import com.patriktrefil.java.plugins.Plugin;

import javax.swing.*;
import java.util.List;

/**
 * Plugin that creates a text editor window
 */
public class TextEditorPlugin implements Plugin {
    @Override
    public String getPluginName() {
        return "Text editor";
    }

    @Override
    public void beforeFirstDraw(final JFrame frame) {
        var textEditor = TextEditor.getInstance();
        textEditor.init(frame);
    }

    @Override
    public int getPriority() {
        return 10;
    }
}