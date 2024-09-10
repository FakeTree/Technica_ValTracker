package com.example.technica_valtracker.db.model;

public class League {
    private int userId;
    private String leagueId;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;
    private String queueType;

    public int getUserId() {
        return userId;
    }

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

    // public void setQueueId() { if queueType=... }
}