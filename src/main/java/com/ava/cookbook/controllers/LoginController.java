/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ava.cookbook.controllers;

import com.ava.cookbook.models.User;
import com.vaadin.server.VaadinSession;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class LoginController {

    private static final String PERSISTENCE_UNIT_NAME = "CookbookPU";
    private static EntityManagerFactory factory;

    User currentUser;

    public LoginController() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public boolean checkUserValid(String username) {
        boolean isValid = false;

        // Check if user present in DB

        return isValid;
    }

    public void setCurrentUser(String username) {
        VaadinSession.getCurrent().setAttribute("username", username);
    }

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
     * @return
     */
    private boolean checkValidUsername(String username) {
        EntityManager em = factory.createEntityManager();
        Query q = em.createQuery("SELECT u FROM User u WHERE u.username = :login")
            .setParameter("login", username);

        try {
            User user = (User)q.getSingleResult();
            return !username.equalsIgnoreCase(user.getUsername());
        }
        catch(Exception e) {
            return false;
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
        return true;
    }
}
