package com.patriktrefil.java.plugins.menubar;

import com.patriktrefil.java.plugins.Plugin;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import com.patriktrefil.java.plugins.menubar.include.IncludeInMenubar;
import com.patriktrefil.java.plugins.menubar.include.MenubarCategories;

public class MenubarPlugin implements Plugin {
    @Override
    public String getPluginName() {
        return "Menubar";
    }

    @Override
    public void beforeFirstDraw(final JFrame frame) {
        var menuItemsForCategories = new HashMap<MenubarCategories, List<JMenuItem>>();

        var includedInMenubar = ServiceLoader.load(IncludeInMenubar.class);
        for (IncludeInMenubar item : includedInMenubar) {
            System.out.printf("Adding action to menubar '%s' with name %s%n", item.group(), item);
            var group = menuItemsForCategories.get(item.group());
            if (group == null) {
                menuItemsForCategories.put(item.group(), List.of(new JMenuItem(item)));
            } else {
                var groupCopy = new ArrayList<>(group);
                groupCopy.add(new JMenuItem(item));
                menuItemsForCategories.put(item.group(), groupCopy);
            }
        }

        var menubar = new JMenuBar();
        for (var category : MenubarCategories.ordering) {
            final var menuItemsList = menuItemsForCategories.get(category);
            if (menuItemsList == null) continue;

            var menu = new JMenu(category.getName());
            for (var item : menuItemsList) {
                menu.add(item);
            }
            menubar.add(menu);
        }

        frame.setJMenuBar(menubar);
    }
}