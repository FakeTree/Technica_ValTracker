package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String DATABASE_URL = "jdbc:sqlite:src/main/resources/champion.db";

    // Method to establish a connection
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
        return conn;
    }

    // Method to initialize the database (create tables if not exist)
    public static void initializeDatabase() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                // Example table creation for champions (adjust according to your needs)
                String createChampionTable = "CREATE TABLE IF NOT EXISTS champions ("
                        + "id TEXT PRIMARY KEY, "
                        + "name TEXT NOT NULL, "
                        + "title TEXT, "
                        + "blurb TEXT, "
                        + "icon_url TEXT);";
                stmt.execute(createChampionTable);
                System.out.println("Database initialized successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error initializing the database: " + e.getMessage());
        }
    }
}
