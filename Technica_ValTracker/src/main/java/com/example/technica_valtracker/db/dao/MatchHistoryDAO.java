package com.example.technica_valtracker.db.dao;

import com.example.technica_valtracker.db.DbConnection;
import com.example.technica_valtracker.db.model.Match;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MatchHistoryDAO implements IMatchHistoryDAO {
    private Connection connection;

    public MatchHistoryDAO() {
        connection = DbConnection.getInstance();
        createTable();
    }

    // TODO: createTable() - Better error handling
    public void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS match_history ("
                    + "matchId TEXT PRIMARY KEY, "
                    + "date INTEGER NOT NULL, "
                    + "queueId INTEGER NOT NULL, "
                    + "gameDuration INTEGER NOT NULL, "
                    + "championId INTEGER NOT NULL, "
                    + "championName TEXT NOT NULL, "
                    + "teamPosition TEXT NOT NULL, "
                    + "kills INTEGER NOT NULL, "
                    + "deaths INTEGER NOT NULL, "
                    + "assists INTEGER NOT NULL, "
                    + "creepScore INTEGER NOT NULL, "
                    + "win TEXT NOT NULL, "
                    + "userId TEXT NOT NULL UNIQUE, "
                    + "FOREIGN KEY(userId) REFERENCES user (userId)"
                    + ")";
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void addNewMatch(Match match) {

    }

    @Override
    public List<Match> getAllMatchesById(int userId) {
        return List.of();
    }

    @Override
    public List<Match> getAllMatches() {
        return List.of();
    }
}
