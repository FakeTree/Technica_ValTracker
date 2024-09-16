package com.example.technica_valtracker.db.model;
import com.fasterxml.jackson.annotation.*;


public class Summoner {
    private String puuid;
    private String accountId;
    @JsonAlias("id")
    private String summonerId;

    private int summonerLevel;
    private int profileIconId;
    @JsonIgnore
    private String profileImageLink;
    private long revisionDate;

    public Summoner() {}

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

    public String getProfileImageLink() { return profileImageLink; }
    public void setProfileImageLink(String profileImageLink) { this.profileImageLink = profileImageLink; }

    public long getRevisionDate() {
        return revisionDate;
    }
    public void setRevisionDate(long revisionDate) {
        this.revisionDate = revisionDate;
    }
}
