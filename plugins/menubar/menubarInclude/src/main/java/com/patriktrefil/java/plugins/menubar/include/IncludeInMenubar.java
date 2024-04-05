package com.patriktrefil.java.plugins.menubar.include;

import javax.swing.Action;

/**
 * Classes implementing this interface will be loaded by {@link java.util.ServiceLoader} and added to the menubar.
 */
public interface IncludeInMenubar extends Action {
    /**
     * Name of the category under which the action will be listed in the menubar.
     */
    default MenubarCategories group() {
        return MenubarCategories.OTHER;
    }
}
