package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.net.URISyntaxException;

public class SummonerDataHandler {

    private Connection connectToDB() throws URISyntaxException, SQLException {
        // Access the summoner.db inside the resources folder
        String dbPath = getClass().getResource("/summoner.db").toURI().getPath();
        return DriverManager.getConnection("jdbc:sqlite:" + dbPath);
    }

    public void storeSummonerRankInDB(String summonerName, String rank, String rankIconUrl) {
        String sql = "INSERT INTO summoners (name, rank, rank_icon_url) VALUES (?, ?, ?)";
        try (Connection connection = connectToDB(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, summonerName);
            pstmt.setString(2, rank);
            pstmt.setString(3, rankIconUrl);
            pstmt.executeUpdate();
        } catch (SQLException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
