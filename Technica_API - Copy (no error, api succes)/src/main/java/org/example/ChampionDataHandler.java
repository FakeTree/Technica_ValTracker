package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ChampionDataHandler {

    private final Connection connection;

    public ChampionDataHandler(Connection connection) {
        this.connection = connection;
    }

    public void storeChampionsInDB(List<Champion> champions) {
        String insertSQL = "INSERT OR REPLACE INTO champions (id, name, title, blurb, icon_url, rank) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            for (Champion champion : champions) {
                pstmt.setString(1, champion.getId());
                pstmt.setString(2, champion.getName());
                pstmt.setString(3, champion.getTitle());
                pstmt.setString(4, champion.getBlurb());
                pstmt.setString(5, champion.getIconUrl());
                pstmt.setString(6, champion.getRank());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("Champions data successfully stored in the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
