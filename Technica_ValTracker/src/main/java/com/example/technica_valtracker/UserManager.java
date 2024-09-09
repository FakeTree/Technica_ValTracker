package com.example.technica_valtracker;

import com.example.technica_valtracker.db.model.User;

import java.util.ArrayList;
import java.util.List;

// User manager class. Contains them in memory and interacts with them as a list
// Note: This is a singleton. Done so that JavaFX is easy to use with it

public class UserManager {
    // The single instance of UserManager
    private static UserManager instance;

    private List<User> userList;

    // Private constructor to prevent instantiation from outside
    private UserManager() {
        this.userList = new ArrayList<>();
    }

    // Public method to provide access to the single instance
    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    // Add a new user, ensuring unique RiotID (this is only local ATM)
    public boolean addUser(User user) {

        // Check if a user with the same RiotID already exists
        for (User existingUser : userList) {
            if (existingUser.getRiotID().equals(user.getRiotID())) {
                System.out.println("Error: RiotID has already been taken!");
                return false; // Don't add the user
            }
        }
        userList.add(user);
        return true; // User successfully added
    }

    // Remove a user by RiotID
    public void removeUserByRiotID(String riotID) {
        userList.removeIf(user -> user.getRiotID().equals(riotID));
    }

    // Get a user by RiotID
    public User getUserByRiotID(String riotID) {
        for (User user : userList) {
            if (user.getRiotID().equals(riotID)) {
                return user;
            }
        }
        return null; // Return null if user not found
    }

    // Get all users
    public List<User> getAllUsers() {
        return userList;
    }

    // Find a user by email AND password (This is for Login)
    public User findUserByCredentials(String email, String password) {
        for (User user : userList) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // No user found
    }

    // Debugging method to print all users (Be careful for when we inevitably get 3 million users)
    public void printAllUsers() {
        for (User user : userList) {
            System.out.println(user);
        }
    }
}