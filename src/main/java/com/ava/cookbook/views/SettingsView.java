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

import com.ava.cookbook.components.MenuComponent;
import com.ava.cookbook.controllers.CookbookController;
import com.ava.cookbook.controllers.SettingsController;
import com.ava.cookbook.controllers.UserController;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author redbaron
 */
public class SettingsView extends VerticalLayout implements View{

    private SettingsController controller;
    private MenuComponent menu;
    private boolean trigger;

    public SettingsView() {
        trigger = true;
        controller = new SettingsController();
        setSizeUndefined();

        final PasswordField currentPassword = new PasswordField("Current password: ");
        final PasswordField newPassword = new PasswordField("New password: ");
        final PasswordField newPasswordConfirmation = new PasswordField("New password (confirmation): ");
        final Button save = new Button("Save changes", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                controller.saveChanges(currentPassword.getValue(), newPassword.getValue(), newPasswordConfirmation.getValue());
            }
        });
        final Button cancel = new Button("Cancel", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().getNavigator().navigateTo(CookbookController.MAIN_VIEW);
            }
        });
        final HorizontalLayout buttons = new HorizontalLayout(save, cancel);

        final Button delete = new Button("Delete account", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                final Window confirmation = new Window("Are you sure you want to delete your account?", null);
                final HorizontalLayout content = new HorizontalLayout();
                Button confirmDelete = new Button("Yes", new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        controller.deleteAccount();
                    }
                });
                Button cancelDelete = new Button("No", new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        confirmation.close();
                    }
                });
                content.addComponents(confirmDelete, cancelDelete);

                confirmation.setContent(content);
                confirmation.center();
                confirmation.setWindowMode(WindowMode.NORMAL);
                confirmation.setWidth(-1, Unit.PIXELS);
                confirmation.setHeight(-1, Unit.PIXELS);
                confirmation.setModal(true);
                getUI().addWindow(confirmation);
                confirmation.focus();
            }
        });

        addComponents(currentPassword, newPassword, newPasswordConfirmation, buttons);
        addComponent(delete);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if(!UserController.validUser()) getUI().getNavigator().navigateTo(CookbookController.LOGIN_VIEW);

        if(trigger) {
            menu = new MenuComponent(controller.getCurrentUsername());
            addComponent(menu);
            trigger = false;
        }
    }

}
