package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseInitializer {

    private static final String DATABASE_URL = "jdbc:sqlite:src/main/resources/champion.db"; // Adjust path if necessary

    public static Connection getChampionDBConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static void initializeChampionDB() {
        try (Connection connection = getChampionDBConnection()) {
            if (connection != null) {
                System.out.println("Champion database initialized successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to initialize champion database.");
            e.printStackTrace();
        }
    }
}
