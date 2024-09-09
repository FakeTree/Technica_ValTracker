package com.example.technica_valtracker.db.dao;

import com.example.technica_valtracker.db.DbConnection;
import com.example.technica_valtracker.db.IUserData;

import java.sql.Connection;

public class UserDAO implements IUserData {
    private Connection connection;

    public UserDAO() {
        connection = DbConnection.getInstance();
    }

    public void createTable() {}

    @Override
    public void addNew(Object object) {

    }

    @Override
    public Object get(int userId) {
        return null;
    }
}
