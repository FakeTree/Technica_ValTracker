package com.example.technica_valtracker.db.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.technica_valtracker.db.DbConnection;

public class FriendsDAO {

    // Method to create the Friends table
    public void createFriendsTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Friends ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "user_id INT, "
                + "friend_id INT, "
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                + "FOREIGN KEY (user_id) REFERENCES Users(id), "
                + "FOREIGN KEY (friend_id) REFERENCES Users(id)"
                + ");";

        try (Connection conn = DbConnection.getInstance();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createTableSQL);
            System.out.println("Friends table created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add a friend
    public void addFriend(int userId, int friendId) {
        String query = "INSERT INTO Friends (user_id, friend_id) VALUES (?, ?)";
        try (Connection conn = DbConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, friendId);
            stmt.executeUpdate();
            System.out.println("Friend added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to check if a friendship exists
    public boolean isFriend(int userId, int friendId) {
        String query = "SELECT * FROM Friends WHERE user_id = ? AND friend_id = ?";
        try (Connection conn = DbConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, friendId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();  // Returns true if a friendship exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
