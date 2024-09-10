package com.example.technica_valtracker.db.dao;

import com.example.technica_valtracker.db.DbConnection;
import com.example.technica_valtracker.db.model.Summoner;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SummonerDAO implements ISummonerDAO {
    private Connection connection;

    public SummonerDAO() {
        connection = DbConnection.getInstance();
         createTable();
    }

    // TODO: createTable() - Better error handling
    public void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS summoner ("
                    + "puuid TEXT PRIMARY KEY, "
                    + "accountId TEXT NOT NULL UNIQUE, "
                    + "summonerId TEXT NOT NULL UNIQUE, "
                    + "region VARCHAR(4) NOT NULL, "
                    + "gameName VARCHAR(16) NOT NULL, "
                    + "tagLine VARCHAR(5) NOT NULL, "
                    + "summonerLevel INTEGER NOT NULL, "
                    + "profileIconId INTEGER NOT NULL, "
                    + "profileImageLink TEXT, "
                    + "revisionDate INTEGER NOT NULL, "
                    + "userId TEXT NOT NULL UNIQUE, "
                    + "FOREIGN KEY(userId) REFERENCES user (userId)"
                    + ")";
            statement.execute(query);
        } catch(SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void addNewSummoner(Summoner summoner) {

    }

    @Override
    public void updateSummoner(Summoner summoner) {

    }

    @Override
    public Summoner getSummoner(String userId) {
        return null;
    }

    @Override
    public List<Summoner> getAllSummoners() {
        return List.of();
    }
}
