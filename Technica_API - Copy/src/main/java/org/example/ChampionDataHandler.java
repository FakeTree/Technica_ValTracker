package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ChampionDataHandler {

    public static void storeChampionsInDB(List<Champion> champions) {
        String sql = "INSERT OR IGNORE INTO champions (id, name, title, blurb, icon_url) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Champion champion : champions) {
                pstmt.setString(1, champion.getId());
                pstmt.setString(2, champion.getName());
                pstmt.setString(3, champion.getTitle());
                pstmt.setString(4, champion.getBlurb());
                pstmt.setString(5, champion.getIconUrl());
                pstmt.addBatch();
            }

            pstmt.executeBatch(); // Execute all batched insertions at once
            System.out.println("Champions successfully stored in the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
