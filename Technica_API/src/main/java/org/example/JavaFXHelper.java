package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JavaFXHelper {

    private static final String URL = "jdbc:sqlite:ConnectionM.db";

    public static String getChampionIconUrlFromDB(String championId) throws Exception {
        String iconUrl = null;

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT icon_url FROM champions WHERE id='" + championId + "'")) {

            if (rs.next()) {
                iconUrl = rs.getString("icon_url");
            }
        }

        return iconUrl;
    }
}
