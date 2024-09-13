package com.example.technica_valtracker.utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.example.technica_valtracker.UserManager;
import com.example.technica_valtracker.db.model.User;

public class UserManagerTest {

    private UserManager userManager;

    @BeforeEach
    public void setUp() {
        // Get the singleton instance of UserManager
        userManager = UserManager.getInstance();
        // Clear the user list before each test
        userManager.getAllUsers().clear();
    }

    @Test
    public void testAddUser_SuccessfulRegistration() {
        User newUser = new User("user1", "test@example.com", "password123", "uniqueRiotID", "NA");
        boolean result = userManager.addUser(newUser);
        assertTrue(result, "User should be registered successfully with a unique RiotID.");
    }

    @Test
    public void testAddUser_DuplicateRiotID() {
        User user1 = new User("user1", "user1@example.com", "password123", "duplicateRiotID", "EU");
        User user2 = new User("user2", "user2@example.com", "password456", "duplicateRiotID", "NA");

        userManager.addUser(user1);
        boolean result = userManager.addUser(user2);

        assertFalse(result, "User registration should fail if RiotID is already taken.");
    }

    @Test
    public void testAddUser_InvalidInput() {
        User invalidUser = new User("", "invalidEmail", "pwd", "", "NA");
        boolean result = userManager.addUser(invalidUser);
        assertFalse(result, "User registration should fail with invalid input.");
    }
}