package com.example.technica_valtracker.db.model;
import com.fasterxml.jackson.annotation.*;

import static com.example.technica_valtracker.api.constants.Champions.getChampionById;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Champion {
    private long championId;
    private String championName;
    private int championPoints;
    private String championIconLink;

    // retrieved from match-v5 endpoint
    private int winrate;
    private int wins;
    private int losses;

    public Champion() {}

    /**
     * Sets the name and icon link values for a given champion.
     */
    public void setChampionInfo() {
        // Get champion name/s based on ID
        String[] championInfo = getChampionById(championId);

        // Check if an alternate Icon link reference is provided in the returned array
        // url template for displaying the champion's icon
        String imgBaseUrl = "https://ddragon.leagueoflegends.com/cdn/14.18.1/img/champion/";
        if (championInfo.length > 1) {
            championName = championInfo[0];
            championIconLink = imgBaseUrl + championInfo[1] + ".png";
        }
        else {
            championName = championInfo[0];
            championIconLink = imgBaseUrl + championInfo[0] + ".png";
        }
    }

    public long getChampionId() {
        return championId;
    }

    public void setChampionId(long championId) {
        this.championId = championId;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public int getChampionPoints() {
        return championPoints;
    }

    public void setChampionPoints(int championPoints) {
        this.championPoints = championPoints;
    }

    public String getChampionIconLink() {
        return championIconLink;
    }

    public void setChampionIconLink(String championIconLink) {
        this.championIconLink = championIconLink;
    }

    public int getWinrate() {
        return winrate;
    }

    public void setWinrate(int winrate) {
        this.winrate = winrate;
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
}
