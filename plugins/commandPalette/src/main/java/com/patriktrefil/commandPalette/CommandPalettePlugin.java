package com.patriktrefil.commandPalette;

import javax.swing.*;
import java.util.List;

public class CommandPalettePlugin implements com.patriktrefil.java.plugins.Plugin {

    @Override
    public String getPluginName() {
        return "Command palette";
    }

    @Override
    public void beforeFirstDraw(final JFrame frame) {
        frame.getRootPane().registerKeyboardAction(new CommandPaletteAction(), KeyStroke.getKeyStroke("control P"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }
}