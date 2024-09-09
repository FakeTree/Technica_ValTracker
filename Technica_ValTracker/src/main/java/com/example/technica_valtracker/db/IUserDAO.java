package com.example.technica_valtracker.db;

import com.example.technica_valtracker.db.model.User;

import java.util.List;

/**
 * Interface for the User Data Access Object that handle
 * CRUD database operations for the User table.
 */
public interface IUserDAO {
    /**
     * Add a new user.
     * @param user The user to add.
      */
    public void addNewUser(User user);

    /**
     * Updates an existing user in the database.
     * @param user The user to update.
     */
    public void updateUser(User user);

    /**
     * Retrieves a user by their id.
     * @param userId The userId of the user to retrieve.
     * @return The user with the given id.
     */
    public User getUser(int userId);

    /**
     * Retrieves all users from the database.
     * @return A list of all users in the database.
     */
    public List<User> getAllUsers();
}
