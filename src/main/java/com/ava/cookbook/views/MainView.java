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
import com.ava.cookbook.controllers.MainController;
import com.ava.cookbook.controllers.UserController;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author redbaron
 */
public class MainView extends VerticalLayout implements View {

    private MainController controller;

    public MainView() {
        controller = new MainController();
        setSizeUndefined();

        addComponent(new MenuView());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This check is to prevent copy-pasting URL for this view and entering it as a non-registered user
        if(!UserController.validUser()) getUI().getNavigator().navigateTo(CookbookController.LOGIN_VIEW);
    }

}
