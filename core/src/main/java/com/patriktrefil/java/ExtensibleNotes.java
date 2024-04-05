package com.patriktrefil.java;

import com.patriktrefil.java.plugins.Plugin;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.prefs.Preferences;

public class ExtensibleNotes {
    final static Preferences prefs = Preferences.userRoot();

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(ExtensibleNotes::createGUI);
    }

    private static void createGUI() {
        final String baseDir = prefs.get(PreferenceKeys.BASE_DIR, null);
        if (baseDir == null) {
            createWelcomeGUI();
        } else {
            createMainGUI();
        }
    }

    private static void createWelcomeGUI() {
        final var initialFrame = new JFrame("Welcome to Extensible Notes");
        initialFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final var panel = new JPanel();
        panel.setBorder(new CompoundBorder(new EmptyBorder(20, 20, 20, 20), panel.getBorder()));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        initialFrame.getContentPane().add(panel);

        panel.add(Box.createVerticalGlue());

        final var headingLabel = new JLabel("Welcome to Extensible Notes!");
        headingLabel.setFont(headingLabel.getFont().deriveFont(Font.BOLD).deriveFont(30f));
        headingLabel.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 10, 0), headingLabel.getBorder()));
        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(headingLabel);

        final var paragraphLabel = new JLabel("Please select a base directory to store your notes in.");
        paragraphLabel.setFont(paragraphLabel.getFont().deriveFont(Font.PLAIN));
        paragraphLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(paragraphLabel);

        final var filePicker = new FilePicker("Base directory:", "Browse",
                JFileChooser.DIRECTORIES_ONLY,
                FilePicker.FilePickerMode.OPEN);
        filePicker.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(filePicker);

        final var finishButton = createFinishButton(filePicker, initialFrame);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(finishButton);

        panel.add(Box.createVerticalGlue());

        initialFrame.pack();
        initialFrame.setLocationRelativeTo(null);
        initialFrame.setVisible(true);
    }

    private static JButton createFinishButton(final FilePicker filePicker, final JFrame initialFrame) {
        final var finishButton = new JButton("Finish setup");
        finishButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final var selectedFile = filePicker.getFileChooser().getSelectedFile();
                if (selectedFile == null) {
                    JOptionPane.showMessageDialog(initialFrame, "Please select a base directory to continue.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                final var selectedFileAbsolutePath = selectedFile.getAbsolutePath();
                System.out.println("Selected base directory: " + selectedFileAbsolutePath);
                prefs.put(PreferenceKeys.BASE_DIR, selectedFileAbsolutePath);
                initialFrame.setVisible(false);
                createMainGUI();
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
        finishButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return finishButton;
    }

    private static void createMainGUI() {
        final var windowTitle = "Extensible Notes";
        final var mainFrame = new JFrame(windowTitle);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);

        final var plugins = loadPlugins(mainFrame);

        mainFrame.setVisible(true);

        for (final var plugin : plugins) {
            plugin.afterFirstDraw(mainFrame);
        }
    }

    private static List<Plugin> loadPlugins(final JFrame mainFrame) {
        System.out.println("Loading plugins");

        final ServiceLoader<Plugin> pluginLoader = ServiceLoader.load(Plugin.class);

        if (pluginLoader.stream().findAny().isEmpty()) {
            System.out.println("No plugins found");
            return List.of();
        }

        final var plugins =
                pluginLoader.stream().map(ServiceLoader.Provider::get).sorted(Comparator.comparing(Plugin::getPriority).reversed()).toList();
        for (final var plugin : plugins) {
            System.out.println("Initializing plugin: " + plugin.getPluginName());
            plugin.beforeFirstDraw(mainFrame);
        }

        return plugins;
    }
}