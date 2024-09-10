package org.example;
import com.fasterxml.jackson.annotation.*;

public class Summoner {
    // Credentials
    private String puuid;
    private String accountId;
    @JsonAlias("id")
    private String summonerId;

    // Account information
    private String gameName;
    private String tagLine;
    private long summonerLevel;
    private int profileIconId;
    private long revisionDate;

    public Summoner() {}

    public String getPuuid() { return puuid; }
    public void setPuuid(String puuid) { this.puuid = puuid; }

    public String getSummonerId() { return summonerId; }
    public void setSummonerId(String summonerId) { this.summonerId = summonerId; }

    public String getGameName() { return gameName; }
    public void setGameName(String gameName) { this.gameName = gameName; }

    public String getTagLine() { return tagLine; }
    public void setTagLine(String tagLine) { this.tagLine = tagLine; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public long getSummonerLevel() { return summonerLevel; }
    public void setSummonerLevel(long summonerLevel) { this.summonerLevel = summonerLevel; }

    public int getProfileIconId() { return profileIconId; }
    public void setProfileIconId(int profileIconId) { this.profileIconId = profileIconId; }

    public long getRevisionDate() { return revisionDate; }
    public void setRevisionDate(long revisionDate) { this.revisionDate = revisionDate; }

    @Override
    public String toString() {
        return "\nSummoner class values:" + "\nPUUID: " + puuid + "\nAccount ID: " + accountId + "\nSummoner ID: " + summonerId
                + "\nGame name: " + gameName + "\nTag: " + tagLine + "\nSummoner level: " + summonerLevel;
    }
}

