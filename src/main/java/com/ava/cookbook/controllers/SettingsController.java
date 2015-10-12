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
package com.ava.cookbook.controllers;

import com.ava.cookbook.models.User;
import com.vaadin.server.VaadinSession;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author redbaron
 */
public class SettingsController {

    private static final String PERSISTENCE_UNIT_NAME = "CookbookPU";
    private static EntityManagerFactory factory;

    private User currentUser;
    private UserController controller;

    public SettingsController() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        currentUser = controller.findUserByName((String)VaadinSession.getCurrent().getAttribute("username"));
    }

    public int changePassword(String currentPassword, String newPassword, String newPasswordConfirmation) {

        if(!newPassword.equals(newPasswordConfirmation)) return 1;          // Incorrect confirmation
        if(!currentPassword.equals(currentUser.getPassword())) return 2;    // Given current password doesn't match the one stored in the database for this user

        int resStatus = 0;
        EntityManager em  = factory.createEntityManager();
        try {
            EntityTransaction entr = em.getTransaction();
            entr.begin();
                currentUser.setPassword(newPassword);
            entr.commit();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            resStatus = 3;  // Exception
        }
        finally {
          em.close();
        }

        return resStatus;
    }

    public void changeAvatar() {

    }

    public void saveChanges(String currentPassword, String newPassword, String newPasswordConfirmation) {
        changePassword(currentPassword, newPassword, newPasswordConfirmation);
    }

    /**
     * Deletes the account of the current user. This method is triggered only after the user confirms (in the view) this action
     */
    public void deleteAccount() {
        EntityManager em  = factory.createEntityManager();
        try {
            EntityTransaction entr = em.getTransaction();
            entr.begin();
                User userEntity = em.getReference(User.class, currentUser.getId());
                em.remove(userEntity);
            entr.commit();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
          em.close();
        }
    }

    public String getCurrentUsername() {
        return (String)VaadinSession.getCurrent().getAttribute("username");
    }
}
