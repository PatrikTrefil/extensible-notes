package com.patriktrefil.commandPalette;

import com.patriktrefil.java.plugins.commandPalette.include.IncludeInCommandPalette;
import com.patriktrefil.java.plugins.menubar.include.IncludeInMenubar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Running this action will show a command palette dialog where the user can enter a command. The commands are
 * all implementations of {@link IncludeInCommandPalette} loaded by {@link java.util.ServiceLoader}.
 */
public class CommandPaletteAction extends AbstractAction implements IncludeInMenubar {
    static final List<IncludeInCommandPalette> registeredActions =
            ServiceLoader.load(IncludeInCommandPalette.class).stream().map(ServiceLoader.Provider::get).toList();

    public CommandPaletteAction() {
        super("Command palette");
        for (var action : registeredActions)
            System.out.println("Adding command to command palette: " + action.commandNameInCommandPalette());
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final String command = JOptionPane.showInputDialog(null, "Enter command", "Command palette",
                JOptionPane.QUESTION_MESSAGE);

        if (command == null || command.isBlank()) {
            System.out.println("No command entered");
            return;
        }

        System.out.println("Received command: " + command);

        registeredActions.stream()
                .filter(a -> a.commandNameInCommandPalette().equals(command)) // Find complete match
                .findFirst()
                .ifPresentOrElse(
                        a -> {
                            System.out.println("Executing found: " + a.commandNameInCommandPalette());
                            a.actionPerformed(e);
                        },
                        () -> {
                            System.out.println("Command not found: " + command);
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Command not found: %s".formatted(command),
                                    "Command palette",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        });
    }
}
