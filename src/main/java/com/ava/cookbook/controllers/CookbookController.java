package com.ava.cookbook.controllers;

import com.ava.cookbook.views.LoginView;
import com.ava.cookbook.views.MainView;
import com.ava.cookbook.views.RegistrationView;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 *
 */
@Theme("cookbooktheme")
@Widgetset("com.ava.cookbook.controller.CookbookAppWidgetset")
public class CookbookController extends UI {
    
    public Navigator nav;
    
    // Views
    public static final String MAIN_VIEW = "main";
    public static final String REGISTRATION_VIEW = "registration";
    public static final String CREATE_RECIPE_VIEW = "new recipe";
    public static final String SETTINGS_VIEW = "settings";
    public static final String ABOUT_VIEW = "about";
    
    public CookbookController() {
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeFull();
        setContent(layout);
        
        ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(layout);
        nav = new Navigator(UI.getCurrent(), viewDisplay);
        
        nav.addView("", new LoginView());
        nav.addView(REGISTRATION_VIEW, new RegistrationView());
        nav.addView(MAIN_VIEW, new MainView());
    }
    
    @Override
    public void detach() {
        super.detach();
    }

    @WebServlet(urlPatterns = "/*", name = "CookbookUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CookbookController.class, productionMode = false)
    public static class CookbookUIServlet extends VaadinServlet {
    }

}
