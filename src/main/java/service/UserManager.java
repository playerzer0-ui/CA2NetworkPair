package service;

import business.User;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, User> users = new HashMap<>();

    public UserManager() {
        // Adding two initial admin users
        users.put("admin1", new User("admin1", "adminPassword1", true));
        users.put("admin2", new User("admin2", "adminPassword2", true));
    }

    /**
     * Adds a new user to the UserManager.
     *
     * @param user the user to add
     * @return true if the user is successfully added, false otherwise
     */
    public boolean addUser(User user) {
        if (user == null || users.containsKey(user.getUsername())) {
            return false; // User already exists or user is null
        }
        users.put(user.getUsername(), user);
        return true;
    }

    /**
     * Searches for a user by username.
     *
     * @param username the username to search for
     * @return the user if found, null otherwise
     */
    public User searchByUsername(String username) {
        return users.get(username);
    }
}
