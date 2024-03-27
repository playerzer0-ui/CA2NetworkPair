package service;

import business.User;

import java.util.HashSet;
import java.util.Set;

public class UserManager {
    private Set<User> users = new HashSet<>();

    /**
     * a set data of admin user
     */
    public UserManager() {
        // Adding two initial admin users
        users.add(new User("admin1", "adminPassword1", true));
        users.add(new User("admin2", "adminPassword2", true));
    }

    /**
     * Adds a new user to the UserManager.
     *
     * @param user the user to add
     * @return true if the user is successfully added, false otherwise
     */
    public boolean addUser(User user) {
        return users.add(user);
    }

    /**
     * Searches for a user by username.
     *
     * @param username the username to search for
     * @return the user if found, null otherwise
     */
    public User searchByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
