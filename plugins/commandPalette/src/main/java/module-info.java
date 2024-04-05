module commandPalette {
    provides com.patriktrefil.java.plugins.commandPalette.include.IncludeInCommandPalette with com.patriktrefil.commandPalette.CommandPaletteHelpAction;
    uses com.patriktrefil.java.plugins.commandPalette.include.IncludeInCommandPalette;
    requires plugins;
    provides com.patriktrefil.java.plugins.Plugin with com.patriktrefil.commandPalette.CommandPalettePlugin;
    requires java.desktop;
    requires commandPaletteInclude;
    requires menubarInclude;
    provides com.patriktrefil.java.plugins.menubar.include.IncludeInMenubar with com.patriktrefil.commandPalette.CommandPaletteAction;
}