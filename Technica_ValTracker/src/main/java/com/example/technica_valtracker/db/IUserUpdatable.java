package com.example.technica_valtracker.db;

/**
 * Extension of the IUserData DAO interface that allows for
 * update operations to the database.
 */
public interface IUserUpdatable extends IUserData {
    /**
     * Updates an existing object in the table based on the userId.
     * @param object The object to update.
     */
    public void update(Object object);
}
