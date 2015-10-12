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
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author redbaron
 */
public class UserController {

    private static final String PERSISTENCE_UNIT_NAME = "CookbookPU";
    private static EntityManagerFactory factory;

    /**
     * Retrieves a user by a given id
     * @param id A valid id representing a user present in the database
     * @return User object; else null
     */
    public static User findUserById(long id) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        User user = null;
        Query q = em.createQuery("SELECT u FROM User u WHERE u.id = :id");
        q.setParameter("id", id);

        try{
            user =(User) q.getSingleResult();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        finally {
            em.close();
        }

        return user;
    }

    /**
     * Retrieves a user by a given username
     * @param username A valid username present in the database
     * @return User object; else null
     */
    public static User findUserByName(String username) {
        if(username == null) return null;
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        User user = null;

        try{
            Query q = em.createQuery("SELECT u FROM User u WHERE u.username = :username");
            q.setParameter("username", username);
            user = (User)q.getSingleResult();

        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        finally {
            em.close();
        }

        return user;
    }

    /**
     * Retrieves a user from the currently active Vaadin session
     * @return Currently logged in user or null if no user is currently logged in
     */
    public static User getCurrentUser() {
        return findUserByName((String)VaadinSession.getCurrent().getAttribute("username"));
    }

    /**
     * Check if the URL was loaded by a logged in user or not
     * @return True if the user is a logged in user; else null
     */
    public static boolean validUser() {
        return (getCurrentUser() != null);
    }
}
