package service;

import business.User;

import java.util.HashSet;
import java.util.Set;

public class UserManager {
    private Set<User> users = new HashSet<>();

    /**
     * @param user the user to add
     * @return true if successfully added, false otherwise (if user already exists)
     */
    public boolean addUser(User user) {
        return users.add(user);
    }

    /**
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
