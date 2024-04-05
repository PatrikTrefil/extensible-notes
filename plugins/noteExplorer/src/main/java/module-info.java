import com.patriktrefil.java.plugins.noteExplorer.NoteExplorerPlugin;
import com.patriktrefil.java.plugins.noteExplorer.ToggleFileExplorerAction;

module fileExplorer {
    requires plugins;
    provides com.patriktrefil.java.plugins.Plugin with NoteExplorerPlugin;
    requires menubarInclude;
    provides com.patriktrefil.java.plugins.menubar.include.IncludeInMenubar with ToggleFileExplorerAction;
    requires commandPaletteInclude;
    provides com.patriktrefil.java.plugins.commandPalette.include.IncludeInCommandPalette with ToggleFileExplorerAction;
    requires java.desktop;
    requires textEditor;
    requires org.apache.commons.io;
    requires static lombok;
    requires core;
    requires java.prefs;
}