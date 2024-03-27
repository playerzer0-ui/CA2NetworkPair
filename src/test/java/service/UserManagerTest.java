package service;

import business.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private UserManager userManager;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        userManager = new UserManager();
        user1 = new User("user1", "password1", false);
        user2 = new User("user2", "password2", true);
        userManager.addUser(user1);
        userManager.addUser(user2);
    }

    @AfterEach
    void tearDown() {
        userManager = null;
        user1 = null;
        user2 = null;
    }

    @Test
    void addUser_normal() {
        User newUser = new User("newUser", "newPassword", true);
        assertTrue(userManager.addUser(newUser));
    }

    @Test
    void addUser_duplicate() {
        assertFalse(userManager.addUser(user1));
    }

    @Test
    void searchByUsername_existing() {
        assertEquals(user1, userManager.searchByUsername("user1"));
    }

    @Test
    void searchByUsername_notFound() {
        assertNull(userManager.searchByUsername("nonExistingUser"));
    }
}
