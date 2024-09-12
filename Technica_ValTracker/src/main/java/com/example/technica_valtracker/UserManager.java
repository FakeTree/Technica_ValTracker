package com.example.technica_valtracker;

import com.example.technica_valtracker.db.model.User;
import com.example.technica_valtracker.db.dao.UserDAO;


import java.util.ArrayList;
import java.util.List;

// User manager class. Contains them in memory and interacts with them as a list
// Note: This is a singleton. Done so that JavaFX is easy to use with it

public class UserManager {

    /// Fields
    // The single instance of UserManager
    private static UserManager instance;
    private UserDAO userDAO;

    // Others
    private List<User> userList;


    // Private constructor to prevent instantiation from outside
    private UserManager() {
        this.userList = new ArrayList<>();
        this.userDAO = new UserDAO();

        // Placeholder to load users on launch
        loadUsersFromDB();
        System.out.println("Reached");
        // Have a looksies
        printAllUsers();
    }

    // Public method to provide access to the single instance
    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    // Add a new user, ensuring unique RiotID
    // Note: This originally checked both DB and Memory but after a bug and some consideration
    // the DB check was removed. It makes more sense to do entire DB loads at meaningful times
    // and check memory, then constant individual small DB checks.
    // If constant DB checks are desired in the future again, then the RiotIDcheck in userDAO needs repairing
    public boolean addUser(User user) {

        // Check if a user with the same RiotID already exists both locally and in the DB
        for (User existingUser : userList) {
            if (existingUser.getRiotID().equals(user.getRiotID())) {
                return false; // Don't add the user
            }
        }
        // Add user to both DB and Memory
        userList.add(user);
        userDAO.addNewUser(user);
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

    /// Pull users from DB VIA UserDAO
    private void loadUsersFromDB() {
        List<User> usersFromDB = userDAO.getAllUsers();  // Retrieve users from the DB
        if (usersFromDB != null) {
            userList.addAll(usersFromDB);  // Sync with in-memory list
        }
    }

    /// Send users from memory to DB via UserDAO
    // Note: Make sure that duplicates cannot occur
    // This method likely wont get used
    private void saveUsersToDB() {
        for (User user : userList) {
            userDAO.addNewUser(user);  // Persist each user to the database
        }
    }
}