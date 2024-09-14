package com.example.technica_valtracker.db.model;

import com.fasterxml.jackson.annotation.*;
import static java.lang.Math.round;

@JsonIgnoreProperties(ignoreUnknown = true)
public class League {
    private String leagueId;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;
    private String queueType;
    @JsonIgnore
    private int winrate;

    public League() {}

    // Getters and setters

    public String getLeagueId() { return leagueId; }
    public void setLeagueId(String leagueId) { this.leagueId = leagueId; }

    public String getTier() {
        return tier;
    }
    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getLeaguePoints() {
        return leaguePoints;
    }
    public void setLeaguePoints(int leaguePoints) {
        this.leaguePoints = leaguePoints;
    }

    public int getWins() {
        return wins;
    }
    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }
    public void setLosses(int losses) {
        this.losses = losses;
    }

    public String getQueueType() {
        return queueType;
    }
    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public int getWinrate() {
        return winrate;
    }

    public void setWinrate() {
        int totalGames = wins + losses;
        winrate = round(((float) wins / totalGames) * 100);
    }

    @Override public String toString() {
        return "\nQueue - " + queueType +
                "\nTier - " + tier +
                "\nLP - " + leaguePoints;
    }
}