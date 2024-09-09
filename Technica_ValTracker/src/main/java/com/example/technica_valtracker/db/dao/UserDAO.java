package com.example.technica_valtracker.db.dao;

import com.example.technica_valtracker.db.DbConnection;
import com.example.technica_valtracker.db.IUserData;
import com.example.technica_valtracker.db.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

// Need to restructure the interface
public class UserDAO implements IUserData {
    private Connection connection;

    public UserDAO() {
        connection = DbConnection.getInstance();
    }

    public void createTable() {}

    @Override
    public void addNew(User user) {
        // Create a PreparedStatement to insert values into user table
    }

    @Override
    public Object get(int userId) {
        return null;
    }
}
