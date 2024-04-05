module core {
    requires plugins;
    uses com.patriktrefil.java.plugins.Plugin;
    requires java.desktop;
    requires static lombok;
    exports com.patriktrefil.java;
    requires java.prefs;
}