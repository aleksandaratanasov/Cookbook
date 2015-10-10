/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ava.cookbook.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;

/**
 *
 * @author redbaron
 */
public class MainView extends HorizontalLayout implements View {

    public MainView() {
        
    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Main");
    }
    
}
