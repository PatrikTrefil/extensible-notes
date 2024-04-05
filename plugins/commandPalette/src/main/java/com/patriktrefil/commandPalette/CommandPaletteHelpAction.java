package com.patriktrefil.commandPalette;

import com.patriktrefil.java.plugins.commandPalette.include.IncludeInCommandPalette;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.stream.Collectors;

/**
 * Running this command in the command palette will show all available commands.
 */
public class CommandPaletteHelpAction extends AbstractAction implements IncludeInCommandPalette {
    @Override
    public void actionPerformed(final ActionEvent e) {
        final var message =
                "Available commands:\n%s".formatted(
                        CommandPaletteAction.registeredActions.stream()
                                .map(IncludeInCommandPalette::commandNameInCommandPalette)
                                .collect(Collectors.joining("\n"))
                );
        JOptionPane.showMessageDialog(null, message, "Command palette", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public String commandNameInCommandPalette() {
        return "Help";
    }
}
