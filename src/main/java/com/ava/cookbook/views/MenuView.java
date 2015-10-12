/*
 * Copyright (C) 2015 redbaron
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.ava.cookbook.views;

import com.ava.cookbook.controllers.CookbookController;
import com.ava.cookbook.controllers.MenuController;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;

/**
 *
 * @author redbaron
 */
public class MenuView extends CustomComponent {

    private MenuController controller;

    public MenuView() {
        controller = new MenuController();
        setSizeUndefined();

        HorizontalLayout layout = new HorizontalLayout();
        final Label title = new Label("Cookbook", ContentMode.HTML);
        final Label greeting = new Label("Hello, " + (String)VaadinSession.getCurrent().getAttribute("username") + "!", ContentMode.HTML);

        MenuBar menu = new MenuBar();
        MenuBar.MenuItem recipes = menu.addItem("Recipes", null, null);
        recipes.addSeparator();
        MenuBar.MenuItem settings = menu.addItem("Settings", new MenuBar.Command() {

            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                getUI().getNavigator().navigateTo(CookbookController.SETTINGS_VIEW);
            }
        });
        MenuBar.MenuItem logout = menu.addItem("Logout", new MenuBar.Command() {

            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                controller.endSession();
                getUI().getNavigator().navigateTo(CookbookController.LOGIN_VIEW);
            }
        });

        layout.addComponents(title, greeting, menu);
        setCompositionRoot(layout);
    }

}
