package com.example.technica_valtracker;

import com.example.technica_valtracker.db.dao.UserDAO;
import com.example.technica_valtracker.db.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserManagerTest {

    private UserManager userManager;
    private UserDAO mockUserDAO;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        // Create a mock UserDAO object
        mockUserDAO = mock(UserDAO.class);

        // Inject mock DAO into UserManager (note: assuming UserManager has a way to set mock DAO)
        userManager = UserManager.getInstance();

        // Clear the singleton instance state for testing
        userManager.getAllUsers().clear();
        userManager.setCurrentUser(null);

        // Mock some users -currently error'ing-
        //user1 = new User("user1@example.com", "password1", "RiotID1", "region1");
        //user2 = new User("user2@example.com", "password2", "RiotID2", "region2");

        // Mock behavior for UserDAO methods
        when(mockUserDAO.getAllUsers()).thenReturn(new ArrayList<>());
        //when(mockUserDAO.addNewUser(any(User.class))).thenReturn(true); -currently error'ing-
    }

    @Test
    void testSingletonInstance() {
        UserManager instance1 = UserManager.getInstance();
        UserManager instance2 = UserManager.getInstance();
        assertSame(instance1, instance2, "UserManager should return the same instance");
    }

    @Test
    void testAddUserSuccessfully() {
        assertTrue(userManager.addUser(user1), "User should be added successfully");
        assertEquals(1, userManager.getAllUsers().size(), "User list size should be 1 after adding a user");
        assertTrue(userManager.getAllUsers().contains(user1), "User list should contain the added user");
    }

    @Test
    void testAddUserWithDuplicateRiotID() {
        userManager.addUser(user1);
        assertFalse(userManager.addUser(user1), "User with duplicate RiotID should not be added");
        assertEquals(1, userManager.getAllUsers().size(), "User list size should remain 1 after trying to add duplicate");
    }

    @Test
    void testRemoveUserByRiotID() {
        userManager.addUser(user1);
        userManager.removeUserByRiotID("RiotID1");
        assertEquals(0, userManager.getAllUsers().size(), "User list size should be 0 after removing the user");
    }

    @Test
    void testGetUserByRiotID() {
        userManager.addUser(user1);
        User foundUser = userManager.getUserByRiotID("RiotID1");
        assertNotNull(foundUser, "User should be found by RiotID");
        assertEquals(user1, foundUser, "Found user should match the added user");
    }

    @Test
    void testGetUserByRiotIDNotFound() {
        User foundUser = userManager.getUserByRiotID("NonExistentRiotID");
        assertNull(foundUser, "No user should be found for a non-existent RiotID");
    }

    @Test
    void testFindUserByCredentials() {
        userManager.addUser(user1);
        User foundUser = userManager.findUserByCredentials("user1@example.com", "password1");
        assertNotNull(foundUser, "User should be found by correct credentials");
        assertEquals(user1, foundUser, "Found user should match the added user");
    }

    @Test
    void testFindUserByInvalidCredentials() {
        userManager.addUser(user1);
        User foundUser = userManager.findUserByCredentials("invalid@example.com", "wrongpassword");
        assertNull(foundUser, "No user should be found for incorrect credentials");
    }

    @Test
    void testSetCurrentUser() {
        userManager.setCurrentUser(user1);
        assertEquals(user1, userManager.getCurrentUser(), "Current user should be set correctly");
    }
}