package com.example.technica_valtracker.db.model;

import com.example.technica_valtracker.api.ResponseBody;
import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.api.error.ErrorResponseInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.concurrent.*;

import java.io.IOException;

import static com.example.technica_valtracker.utils.Deserialiser.getErrorMessageFromJson;

public class Match {
    private String matchId;
    private String[] matchIds;
    private boolean completeQuery;
    private long date;
    private String gameMode;
    private String url;
    private ErrorMessage errorMessage;

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

    // --------------------------------------------------------------------------------

    public ResponseBody getMatchListByPUUID(String puuid, String region, String url, String[] headers) throws IOException {
        String json;

        // Set up HTTP client
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ErrorResponseInterceptor())
                .build();

        // Build GET request
        Request request = new Request.Builder()
                .header(headers[0], headers[1])
                .url(url)
                .build();

        // Send request to client
        try (Response response = client.newCall(request).execute()) {
            // If response returned status code other than 200, return array with error message JSON
            if (!response.isSuccessful()) {
                ErrorMessage error = getErrorMessageFromJson(response.body().string());
                return new ResponseBody(error);
            }
            // Parse successful response as string
            json = response.body().string();
        }
        setMatchIds(json.trim().split(","));
        return new ResponseBody(json, false);
    }

    public ResponseBody getMatchData(String matchID, String region, String url, String[] headers)throws IOException{
        String json;

        // Set up HTTP client
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ErrorResponseInterceptor())
                .build();

        // Build GET request
        Request request = new Request.Builder()
                .header(headers[0], headers[1])
                .url(url)
                .build();

        // Send request to client
        try (Response response = client.newCall(request).execute()) {
            // If response returned status code other than 200, return array with error message JSON
            if (!response.isSuccessful()) {
                ErrorMessage error = getErrorMessageFromJson(response.body().string());
                return new ResponseBody(error);
            }
            // Parse successful response as string
            json = response.body().string();
        }
        return new ResponseBody(json, false);
    }

    // --------------------------------------------------------------------------------

    public String[] getMatchIds(){ return matchIds; }
    private void setMatchIds(String[] matchIds){ this.matchIds = matchIds; }

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
