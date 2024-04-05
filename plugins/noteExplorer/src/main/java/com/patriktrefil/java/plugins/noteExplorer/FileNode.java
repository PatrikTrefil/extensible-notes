package com.patriktrefil.java.plugins.noteExplorer;

import java.io.File;

/**
 * Represents a file in the file system with custom string representation.
 * To be used as a user object in a {@link javax.swing.JTree}.
 */
record FileNode(File file) {

    @Override
    public String toString() {
        return file.getName();
    }
}
