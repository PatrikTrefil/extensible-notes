module textEditor {
    requires plugins;
    requires java.desktop;
    requires menubarInclude;
    requires commandPaletteInclude;
    requires static lombok;
    requires java.prefs;
    requires core;
    provides com.patriktrefil.java.plugins.Plugin with com.patriktrefil.java.plugins.texteditor.TextEditorPlugin;
    provides com.patriktrefil.java.plugins.menubar.include.IncludeInMenubar with com.patriktrefil.java.plugins.texteditor.SaveNoteAction
            , com.patriktrefil.java.plugins.texteditor.NewNoteAction,
            com.patriktrefil.java.plugins.texteditor.OpenNoteAction,
            com.patriktrefil.java.plugins.texteditor.ToggleHtmlRenderingAction;
    provides com.patriktrefil.java.plugins.commandPalette.include.IncludeInCommandPalette with com.patriktrefil.java.plugins.texteditor.ToggleHtmlRenderingAction;
    exports com.patriktrefil.java.plugins.texteditor;
}