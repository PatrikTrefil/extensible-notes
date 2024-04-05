package com.patriktrefil.java.plugins.texteditor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.html.HTMLDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import lombok.Getter;
import lombok.Setter;

/**
 * Singleton class that represents the text editor.
 * This class is implemented as a singleton so that the actions that rely on the text editor
 * all work with the same instance. This is important because the text editor is a stateful object.
 */
class TextEditor {
    public final JEditorPane editorPane = new JEditorPane();
    @Getter
    @Setter
    private boolean isDirty = false;
    @Getter
    private File currentFile = null;

    public void setCurrentFile(File newFile) {
        headingLabel.setText(newFile.getName());
        this.currentFile = newFile;
    }

    final JLabel headingLabel = new JLabel("New note");
    @Getter
    private static final TextEditor instance = new TextEditor();
    final DocumentListener updateDirtyListener = new DocumentListener() {

        @Override
        public void insertUpdate(DocumentEvent e) {
            setDirty(true);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            setDirty(true);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            setDirty(true);
        }
    };

    private TextEditor() {
        editorPane.setEditable(true);
        editorPane.setContentType("text/html");
        editorPane.setText("<html><body><h1>New note</h1><p>note content</p></body></html>");
        editorPane.getDocument().addDocumentListener(updateDirtyListener);
    }

    public void init(final JFrame frame) {
        createShortcuts(frame);

        final var scrollTextPane = new JScrollPane(editorPane);
        scrollTextPane.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        final var splitPane = new JSplitPane();
        final var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        headingLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        panel.add(headingLabel);
        panel.add(scrollTextPane);

        splitPane.add(panel, JSplitPane.RIGHT);

        frame.getContentPane().add(splitPane);
    }

    public void toggleHtmlRendering() {
        System.out.println("Toggling HTML rendering");

        final var prevDoc = editorPane.getDocument();
        StringWriter writer = new StringWriter();
        try {
            editorPane.getEditorKit().write(writer, prevDoc, 0, prevDoc.getLength());
        } catch (IOException | BadLocationException ex) {
            throw new RuntimeException(ex);
        }

        if (editorPane.getContentType().equals("text/html")) {
            editorPane.setContentType("text/plain");
        } else {
            editorPane.setContentType("text/html");
        }

        editorPane.setText(writer.toString());

        editorPane.getDocument().addDocumentListener(updateDirtyListener);
    }

    void createShortcuts(final JFrame frame) {
        frame.getRootPane().registerKeyboardAction(new OpenNoteAction(), KeyStroke.getKeyStroke("control O"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );
        frame.getRootPane().registerKeyboardAction(new SaveNoteAction(), KeyStroke.getKeyStroke("control S"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );
        frame.getRootPane().registerKeyboardAction(new NewNoteAction(), KeyStroke.getKeyStroke("control N"),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        frame.getRootPane().registerKeyboardAction(new ToggleHtmlRenderingAction(),
                KeyStroke.getKeyStroke("control M"), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
}

