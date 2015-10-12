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
import com.ava.cookbook.controllers.LoginController;
import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author redbaron
 */
public class RegistrationView extends VerticalLayout implements View {

    private LoginController controller;

    public RegistrationView() {
        controller = new LoginController();
        setSizeFull(); // Ensure that the view fills the whole page
        setSpacing(true);

        /**
         * Form:
         * +-----------------------------------------------+
         * |+--avatar--+ +--------inputFields-------------+|-------------horizontal (form)
         * ||          | |                                ||
         * ||          | | +----------username----------+ |--------------vertical
         * ||          | | +----------password----------+ ||
         * ||          | | +----password_confirmation---+ ||
         * ||          | |                                ||
         * ||  Image   | | +-----------buttons-----------+||
         * ||          | | |+--register--+  +--cancel--+ |---------------horizontal
         * ||          | | +-----------------------------+||
         * ||          | |                                ||
         * ||          | | +-----------error-------------+||
         * ||          | |                                ||
         * |+----------+ +--------------------------------+|
         * +-----------------------------------------------+
         */
        final Label title = new Label("<center><h1>Registration</h1></center>", ContentMode.HTML);
        title.setSizeUndefined();
        addComponent(title);
        setComponentAlignment(title, Alignment.TOP_CENTER);
        final HorizontalLayout form = new HorizontalLayout();
        form.setSizeUndefined();
        ThemeResource resource = new ThemeResource("default_avatar.png");
        Image avatar = new Image("", resource);
        avatar.setSizeUndefined();
        avatar.setWidth("160px");
        avatar.setHeight("160px");
        avatar.addClickListener(new MouseEvents.ClickListener() {

            @Override
            public void click(MouseEvents.ClickEvent event) {
                // Click on image, open file explorer, select image, display it and set it for upload as avatar if registration is successful
                Notification.show("Choose avatar");
            }
        });

        final TextField username = new TextField("Username: ");
        username.setDescription("Enter a unique username. The system will automatically check if it is already used by someone else");
        final PasswordField password = new PasswordField("Password: ");
        final PasswordField passwordConfirmation = new PasswordField("Password (confirmation): ");
        final Label error = new Label("&nbsp", ContentMode.HTML);
        error.setVisible(false);
        final Button registration = new Button("Register", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                int regResult = controller.createUser(
                        username.getValue(),
                        password.getValue(),
                        passwordConfirmation.getValue());
                switch(regResult) {
                    case 1:
                        // Username already taken
                        error.setCaption("<font color='red'>User with specified username already exists!</font>");
                        error.setVisible(true);
                        break;
                    case 2:
                        // Password doesn't match password confirmation
                        error.setCaption("<font color='red'>Password mismatch!</font>");
                        error.setVisible(true);
                        break;
                    case 3:
                        // Exception
                        error.setCaption("<font color='red'>Failed to create user!</font>");
                        error.setVisible(true);
                        break;
                    case 0:
                        // Go to the main view in case of a successful registration
                        error.setVisible(false);
                        LoginController.startSession(username.getValue());
                        getUI().getNavigator().navigateTo(CookbookController.MAIN_VIEW);
                }
            }
        });
        registration.setStyleName("link");
        final Button cancel = new Button("Cancel", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                // Return to login view
                getUI().getNavigator().navigateTo(CookbookController.LOGIN_VIEW);
            }
        });
        cancel.setStyleName("link");

        final Label copyright = new Label("\u00a9 2015 Aleksandar Vladimirov Atanasov");

        final VerticalLayout inputFields = new VerticalLayout();
        inputFields.setSizeUndefined();
        inputFields.addComponent(username);
        inputFields.addComponent(password);
        inputFields.addComponent(passwordConfirmation);
        final HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSizeUndefined();
        buttons.addComponents(registration, cancel);
        inputFields.addComponent(buttons);
        inputFields.setComponentAlignment(buttons, Alignment.BOTTOM_CENTER);
        form.addComponents(avatar, inputFields);
        form.setComponentAlignment(avatar, Alignment.TOP_LEFT);
        addComponent(form);
        setComponentAlignment(form, Alignment.MIDDLE_CENTER);

        addComponent(copyright);
        copyright.setSizeUndefined();
        setComponentAlignment(copyright, Alignment.BOTTOM_RIGHT);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}
