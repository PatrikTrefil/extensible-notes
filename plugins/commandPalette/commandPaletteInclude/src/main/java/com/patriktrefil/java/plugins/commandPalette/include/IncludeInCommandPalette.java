package com.patriktrefil.java.plugins.commandPalette.include;

import javax.swing.Action;

/**
 * Classes implementing this interface will be loaded by {@link java.util.ServiceLoader} and added to the command
 * palette.
 */
public interface IncludeInCommandPalette extends Action {
    String commandNameInCommandPalette();
}
