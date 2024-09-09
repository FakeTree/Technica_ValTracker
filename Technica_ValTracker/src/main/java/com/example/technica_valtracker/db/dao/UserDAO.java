package com.example.technica_valtracker.db.dao;

import com.example.technica_valtracker.db.DbConnection;
import com.example.technica_valtracker.db.IUserData;
import com.example.technica_valtracker.db.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

// Need to restructure the interface
public class UserDAO implements IUserData {
    private Connection connection;

    public UserDAO() {
        connection = DbConnection.getInstance();
        createTable();
        insertSampleData();
    }

    private void insertSampleData() {
        try {
            // Clear before inserting
            Statement clearStatement = connection.createStatement();
            String clearQuery = "DELETE FROM user";
            clearStatement.execute(clearQuery);
            Statement insertStatement = connection.createStatement();
            String insertQuery = "INSERT INTO user (userId, email, password, riotId) VALUES "
                    + "('B_BHDZwmQBwqszCtpjEHiTq1zZrcQZicLGyGhbA3M8jCk8WFRGGqoAA_uUc0vMzaVRBt7nZ_i_UMhA', 'god_gamer@me.com', 'IAmTheBest1', 'Agurin#EUW'),"
                    + "('kHbpOkjpZHoWm9BWJLRGM0YcPQLxOsKJeQD2D6h9KMdF5lO26FjN0epddA15S6_F6Li9kqUxK9q9aw', 'senzawa1@hoopla.com', 'ILoveYellow0', 'senzawa#JP1'),"
                    + "('4g0SccskE_D1pX_03eVxJI3qhkWCTD8p2FwvlQS-aJcnEqVVMHIvQymqWyzKg8xfBzivnHpcniVOLg', 'flexgod21@lol.com', 'MeAndMyPal1', '')";
            insertStatement.execute(insertQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS user ("
                    + "userId TEXT PRIMARY KEY UNIQUE, "
                    + "email TEXT NOT NULL, "
                    + "password TEXT NOT NULL, "
                    + "riotId TEXT NOT NULL"
                    + ")";
            statement.execute(query);
        } catch(SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void addNewUser(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public User getUser(int userId) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }
}
