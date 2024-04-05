package com.patriktrefil.java.plugins.menubar.include;

import java.util.Collections;
import java.util.List;

public enum MenubarCategories {
    FILE("File"),
    EDIT("Edit"),
    VIEW("View"),
    OTHER("Other"),
    HELP("Help");

    /**
     * The order of the categories in the menubar.
     */
    public final static List<MenubarCategories> ordering = Collections.unmodifiableList(List.of(FILE, EDIT, VIEW,
            OTHER, HELP));
    /**
     *
     */
    @lombok.Getter
    private final String name;

    MenubarCategories(String name) {
        this.name = name;
    }
}
