package com.example.technica_valtracker.db.model;
import com.example.technica_valtracker.api.ResponseBody;
import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.api.error.ErrorResponseInterceptor;
import com.fasterxml.jackson.annotation.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

import static com.example.technica_valtracker.assets.Champions.getChampionById;
import static com.example.technica_valtracker.utils.Deserialiser.getErrorMessageFromJson;

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

    // url template for displaying the champion's icon
    private final String imgBaseUrl = "https://ddragon.leagueoflegends.com/cdn/14.18.1/img/champion/";

    public Champion() {}

    /**
     * Get a player's top 3 champions by mastery points from the API.
     * @param puuid The player's puuid.
     * @param region The player's server region.
     * @param url The URL to send a request to.
     * @param headers The request headers to use for the request.
     * @return A ResponseBody with the output as a JSON string if successful, or a ResponseBody with an
     * ErrorMessage if the request failed.
     */
    public ResponseBody getChampionData(String puuid, String region, String url, String[] headers) throws IOException {
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
            if (!response.isSuccessful()) {
                ErrorMessage error = getErrorMessageFromJson(response.body().string());
                return new ResponseBody(error);
            }
            // Parse successful response as string
            json = response.body().string();
        }

        return new ResponseBody(json, false);
    }

    /**
     * Sets the name and icon link values for a given champion.
     */
    public void setChampionInfo() {
        // Get champion name/s based on ID
        String[] championInfo = getChampionById(championId);

        // Check if an alternate Icon link reference is provided in the returned array
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
