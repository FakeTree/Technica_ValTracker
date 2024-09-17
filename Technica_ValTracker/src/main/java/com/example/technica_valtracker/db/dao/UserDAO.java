package com.example.technica_valtracker.db.dao;

import com.example.technica_valtracker.db.DbConnection;
import com.example.technica_valtracker.db.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;

// TODO restructure the interface
// TODO replace printStackTrace with better error handling protocol
public class UserDAO implements IUserDAO {
    private Connection connection;

    public UserDAO() {
        try {
            connection = DbConnection.getInstance();
            connection.setAutoCommit(true); // Enable auto-commit
        } catch (SQLException e) {
            e.printStackTrace();
        }

        createTable();
        //insertSampleData();
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

    // TODO: createTable() - Better error handling
    public void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS user ("
                    + "userId TEXT PRIMARY KEY UNIQUE, "
                    + "email TEXT NOT NULL, "
                    + "password TEXT NOT NULL, "
                    + "riotId TEXT NOT NULL,"
                    + "region TEXT"
                    + ")";
            statement.execute(query);
        } catch(SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void addNewUser(User user) {
        String insertQuery = "INSERT INTO user (userId, email, password, riotId, region) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getRiotID());
            pstmt.setString(5, user.getRegion());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        String updateQuery = "UPDATE user SET email = ?, password = ?, riotId = ?, region = ? WHERE userId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRiotID());
            pstmt.setString(4, user.getRegion()); // Add this line for the new field
            pstmt.setString(5, user.getUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUser(String userId) {
        String query = "SELECT * FROM user WHERE userId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("userId"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("riotId"),
                        rs.getString("region") // Add this line for the new field
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No user found
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                User user = new User(
                        rs.getString("userId"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("riotId"),
                        rs.getString("region") // Add this line for the new field
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /// This is a preliminary to prevent duplicates before the PUUID is found
    public boolean isRiotIDTaken(String riotId) {
        String query = "SELECT 1 FROM user WHERE riotId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, riotId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true; // RiotID exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // RiotID not found
    }
}
