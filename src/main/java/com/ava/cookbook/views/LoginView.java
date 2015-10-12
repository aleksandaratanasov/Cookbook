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
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author redbaron
 */
public class LoginView extends VerticalLayout implements View {

    private LoginController controller;

    public LoginView() {
        controller = new LoginController();
        setSizeFull(); // Ensure that the view fills the whole page
        setSpacing(true);

        final VerticalLayout form = new VerticalLayout();
        form.setSizeUndefined();
        final Label title = new Label("<center><h1>Cookbook</h1></center>", ContentMode.HTML);
        title.setSizeUndefined();
        final TextField username = new TextField("Username: ");
        final PasswordField password = new PasswordField("Password: ");
        final Label error = new Label("<font color='red'>Incorrect username or password!</font>", ContentMode.HTML);
        error.setVisible(false);

        final HorizontalLayout logReg = new HorizontalLayout();
        logReg.setSizeUndefined();
        final Button login = new Button("Login", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if(controller.checkUserValid(username.getValue(), password.getValue())) {
                    error.setVisible(false);
                    controller.startSession(username.getValue());
                    getUI().getNavigator().navigateTo(CookbookController.MAIN_VIEW);
                }
                else
                    error.setVisible(true);
            }
        });
        login.setStyleName("link");

        final Button register = new Button("Register", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().getNavigator().navigateTo(CookbookController.REGISTRATION_VIEW);
            }
        });
        register.setStyleName("link");

        final Label copyright = new Label("\u00a9 2015 Aleksandar Vladimirov Atanasov");

        logReg.addComponent(login);
        logReg.addComponent(register);

        addComponent(title);
        setComponentAlignment(title, Alignment.TOP_CENTER);
        form.addComponent(username);
        form.setComponentAlignment(username, Alignment.TOP_CENTER);
        form.addComponent(password);
        form.setComponentAlignment(password, Alignment.MIDDLE_CENTER);
        form.addComponent(logReg);
        form.setComponentAlignment(logReg, Alignment.BOTTOM_CENTER);
        form.addComponent(error);
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
