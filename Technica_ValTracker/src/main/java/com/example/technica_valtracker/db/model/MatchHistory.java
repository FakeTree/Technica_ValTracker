package com.example.technica_valtracker.db.model;

public class MatchHistory {
    private int userId;
    private String matchId;
    private long date;
    private String gameMode;

    private int queueId;
    private long gameDuration;

    private int championId;
    private int championName;
    private String position;

    private int kills;
    private int deaths;
    private int assists;

    private float creepScore;
    private boolean win;

    public int getUserId() { return userId; }

    public String getMatchId() {
        return matchId;
    }
    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }

    public String getGameMode() {
        return gameMode;
    }
    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public int getQueueId() {
        return queueId;
    }
    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public long getGameDuration() {
        return gameDuration;
    }
    public void setGameDuration(long gameDuration) {
        this.gameDuration = gameDuration;
    }

    public int getChampionId() {
        return championId;
    }
    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public int getChampionName() {
        return championName;
    }
    public void setChampionName(int championName) {
        this.championName = championName;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public int getKills() {
        return kills;
    }
    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }
    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAssists() {
        return assists;
    }
    public void setAssists(int assists) {
        this.assists = assists;
    }

    public float getCreepScore() {
        return creepScore;
    }
    public void setCreepScore(float creepScore) {
        this.creepScore = creepScore;
    }

    public boolean isWin() {
        return win;
    }
    public void setWin(boolean win) {
        this.win = win;
    }
}
