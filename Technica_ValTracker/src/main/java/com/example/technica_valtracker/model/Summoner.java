package com.example.technica_valtracker.model;

public class Summoner {
    private int userId;
    private String puuid;
    private String accountId;
    private String summonerId;

    private String region;
    private String gameName;
    private String tagLine;

    private int summonerLevel;
    private int profileIconId;
    private long revisionDate;

    public int getUserId() {
        return userId;
    }

    public String getPuuid() {
        return puuid;
    }
    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSummonerId() {
        return summonerId;
    }
    public void setSummonerId(String summonerId) {
        this.summonerId = summonerId;
    }

    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }

    public String getGameName() {
        return gameName;
    }
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getTagLine() {
        return tagLine;
    }
    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public int getSummonerLevel() {
        return summonerLevel;
    }
    public void setSummonerLevel(int summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public int getProfileIconId() {
        return profileIconId;
    }
    public void setProfileIconId(int profileIconId) {
        this.profileIconId = profileIconId;
    }

    public long getRevisionDate() {
        return revisionDate;
    }
    public void setRevisionDate(long revisionDate) {
        this.revisionDate = revisionDate;
    }
}
