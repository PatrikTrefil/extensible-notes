package com.patriktrefil.java;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * A file picker component that displays currently selected filepath and a browse button.
 */
class FilePicker extends JPanel {
    enum FilePickerMode {
        OPEN, SAVE
    }

    private final JLabel userProvidedLabel;
    private final String nothingSelectedText = "Nothing selected";
    private final JLabel selectedPathLabel = new JLabel(nothingSelectedText);
    private final JButton browseButton;
    @Getter
    private final JFileChooser fileChooser = new JFileChooser();
    @Getter
    private final FilePickerMode mode;
    private final JPanel innerPanel;

    public FilePicker(final String selectedPathLabelText, final String browserButtonLabel,
                      final Integer fileSelectionMode,
                      final FilePickerMode mode) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        userProvidedLabel = new JLabel(selectedPathLabelText);

        selectedPathLabel.setFont(selectedPathLabel.getFont().deriveFont(Font.PLAIN));

        this.mode = mode;
        fileChooser.setFileSelectionMode(fileSelectionMode);

        innerPanel = new JPanel();
        innerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerPanel.add(userProvidedLabel);
        innerPanel.add(selectedPathLabel);
        add(innerPanel);

        browseButton = new JButton(browserButtonLabel);
        browseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        browseButton.addActionListener(this::browserButtonActionHandler);
        add(browseButton);
    }

    private void browserButtonActionHandler(final ActionEvent e) {
        int choiceResult;
        switch (this.mode) {
            case OPEN -> {
                choiceResult = fileChooser.showOpenDialog(this);
            }
            case SAVE -> {
                choiceResult = fileChooser.showSaveDialog(this);
            }
            default -> {
                throw new RuntimeException("Unknown mode");
            }
        }

        switch (choiceResult) {
            case JFileChooser.APPROVE_OPTION -> {
                selectedPathLabel.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
            case JFileChooser.ERROR_OPTION -> {
                JOptionPane.showMessageDialog(null, "Failed to pick file", "File pick failure",
                        JOptionPane.ERROR_MESSAGE);
            }
            case JFileChooser.CANCEL_OPTION -> {
                System.out.println("User cancelled file pick");
            }
        }
    }
}
