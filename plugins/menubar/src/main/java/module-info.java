import com.patriktrefil.java.plugins.menubar.MenubarPlugin;

module menubar {
    requires plugins;
    requires java.desktop;
    requires menubarInclude;

    uses com.patriktrefil.java.plugins.menubar.include.IncludeInMenubar;

    provides com.patriktrefil.java.plugins.Plugin with MenubarPlugin;
}