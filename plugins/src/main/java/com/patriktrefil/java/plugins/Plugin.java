package com.patriktrefil.java.plugins;

import javax.swing.*;
import java.util.List;

/**
 * All classes implementing this interface are considered plugins and are loaded using the
 * {@link java.util.ServiceLoader}.
 */
public interface Plugin {
    /**
     * Plugin name used for identification of the plugin in log messages.
     */
    String getPluginName();

    /**
     * This method is called during initialization of the notes application
     * before the first draw of the main frame. Use this method to add
     * contents to the UI for your plugin. If it is not necessary to run your code before
     * the first draw, consider using the {@link Plugin#beforeFirstDraw}.
     *
     * @param frame - frame of the application
     */
    default void beforeFirstDraw(JFrame frame) {
    }

    /**
     * This method is called during initialization of the notes application
     * after the first draw of the main frame. Use this to finish initialization
     * of your plugin. The implementation is expected not to make significant
     * changes to the UI, as the UI is already drawn. If it is necessary to make
     * significant changes to the UI, consider using the {@link Plugin#beforeFirstDraw}.
     *
     * @param frame - frame of the application
     */
    default void afterFirstDraw(JFrame frame) {
    }

    /**
     * Plugins are initialized in order of their priority. The default priority is 0.
     *
     * @return priority of the plugin
     */
    default int getPriority() {
        return 0;
    }
}
