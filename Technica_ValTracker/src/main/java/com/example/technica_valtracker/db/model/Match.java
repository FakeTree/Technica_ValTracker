package com.example.technica_valtracker.db.model;

import com.example.technica_valtracker.api.ResponseBody;
import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.api.error.ErrorResponseInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.*;

import static com.example.technica_valtracker.utils.Deserialiser.getErrorMessageFromJson;

public class Match {
    private String matchId;
    private List<String> matchIds = new ArrayList<String>();
    private String url;
    private ErrorMessage errorMessage;

    private int queueId;
    private long gameDuration;
    private long date;
    private String gameMode;

    private int championId;
    private int championName;
    private String position;

    private int kills;
    private int deaths;
    private int assists;

    private float creepScore;
    private boolean win;

    // --------------------------------------------------------------------------------

    public void setMatchListByPUUID(ResponseBody res) throws IOException {
        emptyMatchId();
        System.out.println(res.getJson().toString());
        Matcher m = Pattern.compile("[A-Z0-9_]+").matcher(res.getJson().toString());
        while(m.find()){
            addMatchIds(m.group());
        }
    }

    // --------------------------------------------------------------------------------

    public List<String> getMatchIds(){ return matchIds; }
    public void setMatchIds (List<String> param){ this.matchIds = param; }
    private void addMatchIds (String param){ this.matchIds.add(param); }
    private void emptyMatchId(){ this.matchIds = new ArrayList<String>(); }

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

    public ErrorMessage getMatchErrorMessage(){ return errorMessage; }
    public void setErrorMessage(ErrorMessage error) { errorMessage = error; }
}
