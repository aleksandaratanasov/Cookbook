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

public class LoginController {

    private static final String PERSISTENCE_UNIT_NAME = "CookbookPU";
    private static EntityManagerFactory factory;

    User currentUser;

    public LoginController() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    /**
     *
     * @param username
     * @param password
     * @return True if user is a registered user
     */
    public boolean checkUserValid(String username, String password) {
        try {
            User user = UserController.findUserByName(username);

            if(password.equals(user.getPassword())) return true;
            else return false;
        }
        catch(Exception e) {
            return false;
        }
    }

    /**
     * Starts a session with the given username as attribute which is used for loading user-specific content in other views
     * @param username Name of the current user
     */
    public void startSession(String username) {
        // FIXME See why this one here is set to null. Maybe because it is called from within the constructor?
        VaadinSession.getCurrent().setAttribute("username", username);
    }

    /**
     * Create a new user and adds him to the database
     * @param username Name that the user will be using for login (has to be unique)
     * @param password Password that the user will be using for login
     * @param password_confirmation Password that has to be the same as the first password parameter
     * @return registration status (1="username taken", 2="password invalid", 3="exception")
     */
    public int createUser(String username, String password, String password_confirmation) {
        int regStatus = 0;
        if(checkValidUsername(username)) {
            if(checkValidPassword(password, password_confirmation)) {
                EntityManager em = factory.createEntityManager();
                try {
                    EntityTransaction entr = em.getTransaction();
                    entr.begin();
                        User user = new User(username, password);
                        em.persist(user);
                    entr.commit();
                }
                catch(Exception e) {
                    System.out.println(e.getMessage());
                    regStatus = 3;
                }
                finally {
                    em.close();
                }
            }
            else regStatus = 2; // Password mismatch
        }
        else regStatus = 1;     // User with selected username already present in DB

        return regStatus;
    }

    /**
     * Checks if a user with the specified username is already in the database.
     * Used in the registration form.
     * @param username Name that the user will be using for login
     * @return True if user is not present in the database
     */
    private boolean checkValidUsername(String username) {
        try {
            User user = UserController.findUserByName(username);
            return !username.equals(user.getUsername());            // changed from equalsIgnoreCase to equals to allow distinguishing between names such as "Name", "NAME", "NaMe" etc.
        }
        catch(Exception e) {
            return true;
        }
    }

    /**
     * Checks only if the given password is equal to the confirmation password.
     * Used in the registration form
     * @param password Password that the user will be using for login
     * @param password_confirmation Password that has to be the same as the first password parameter
     * @return
     */
    private boolean checkValidPassword(String password, String password_confirmation) {
        return password.equals(password_confirmation);
    }
}
