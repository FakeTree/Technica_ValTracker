package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class ChampionDataHandler {

    private static final String URL = "jdbc:sqlite:ConnectionM.db";

    public static void storeChampionsInDB(Map<String, Object> champions) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO champions (id, name, title, blurb, icon_url) VALUES (?, ?, ?, ?, ?)")) {

            for (Map.Entry<String, Object> entry : champions.entrySet()) {
                @SuppressWarnings("unchecked")
                Map<String, Object> championDetails = (Map<String, Object>) entry.getValue();
                pstmt.setString(1, (String) championDetails.get("id"));
                pstmt.setString(2, (String) championDetails.get("name"));
                pstmt.setString(3, (String) championDetails.get("title"));
                pstmt.setString(4, (String) championDetails.get("blurb"));
                pstmt.setString(5, "https://ddragon.leagueoflegends.com/cdn/13.1.1/img/champion/" + championDetails.get("id") + ".png");
                pstmt.executeUpdate();
            }
        }
    }
}
