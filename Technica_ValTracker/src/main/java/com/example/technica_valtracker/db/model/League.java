package com.example.technica_valtracker.db.model;

import com.example.technica_valtracker.Constants;
import com.example.technica_valtracker.api.error.ErrorResponseInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.annotation.*;
import java.io.IOException;

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

    /**
     * Retrieves league information for a player based on their summoner ID and region.
     * @param summonerId The summoner ID of the player.
     * @param region The server region of the player.
     * @return A JSONArray string with the response body if successful, or an ErrorMessage if not.
     * @throws IOException
     * TODO: Return ResponseBody instead? Have to do some extra parsing coz 200 response returns a JsonArray.
     */
    public String getLeagueData(String summonerId, String region) throws IOException {
        String json;
        String requestUrl = "https://" + region + ".api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerId;

        // Set up HTTP client
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ErrorResponseInterceptor())
                .build();

        // Build GET request
        Request request = new Request.Builder()
                .header("X-Riot-Token", Constants.RIOT_API_KEY)
                .url(requestUrl)
                .build();

        // Send request to client
        try (Response response = client.newCall(request).execute()) {
            // If response returned status code other than 200, return array with error message JSON
            if (!response.isSuccessful()) {
                json = "[" + response.body().string() + "]";
                return json;
            }

            // Parse successful response as string
            json = response.body().string();
        }
        return json;
    }

    // automatically calculate and set this value?
    public int calculateWinrate(int wins, int losses) {
        int totalGames = wins + losses;
        return round(((float) wins / totalGames) * 100);
    }

    // Getters and setters

    public String getLeagueId() { return leagueId; }
    public void setLeagueId(String leagueId) { this.leagueId = leagueId; };

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
}