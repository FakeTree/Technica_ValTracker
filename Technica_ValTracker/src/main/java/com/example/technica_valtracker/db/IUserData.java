package com.example.technica_valtracker.db;

/**
 * Interface for the <> Data Access Objects that handles
 * CRUD database operations for each respective DAO.
 * The database is composed of several tables which are all linked
 * by a single foreign key, 'userId'.
 */
public interface IUserData {
    /**
     * Add a new object to the table.
     * @param object The object to add.
      */
    public void addNew(Object object);
    /**
     * Updates an existing object in the table based on the userId.
     * @param object The object to update.
     */
    public void update(Object object);
    /**
     * Retrieves a database entry.
     * @param userId The userId of the given object to retrieve.
     * @returns The object with the given id.
     */
    public Object get(int userId);
}
