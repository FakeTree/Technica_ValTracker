package com.example.technica_valtracker.db.model;
import com.example.technica_valtracker.Constants;
import com.example.technica_valtracker.api.ResponseBody;
import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.api.error.ErrorResponseInterceptor;
import com.fasterxml.jackson.annotation.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static com.example.technica_valtracker.utils.Deserialiser.getErrorMessageFromJson;

public class Summoner {
    private String puuid;
    private String accountId;
    @JsonAlias("id")
    private String summonerId;

    private String region;
    private String gameName;
    private String tagLine;

    private int summonerLevel;
    private int profileIconId;
    @JsonIgnore
    private String profileImageLink;
    private long revisionDate;

    public Summoner() {}

    /**
     * Retrieves a player's Summoner data from the API and stores it in a
     * ResponseBody object that contains the stringified JSON and error check.
     * @param puuid The puuid of the player.
     * @param region The server region of the player.
     * @return ResponseBody object with stringified JSON response and boolean indicating if an error occurred.
     * @throws IOException
     */
    public ResponseBody getSummonerByPuuid(String puuid, String region) throws IOException {
        String json;
        String requestUrl = "https://" + region + ".api.riotgames.com/lol/summoner/v4/summoners/by-puuid/" + puuid;

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
            if (!response.isSuccessful()) {
                ErrorMessage error = getErrorMessageFromJson(response.body().string());
                return new ResponseBody(error);
            }
            // Parse successful response as string
            json = response.body().string();
        }

        return new ResponseBody(json, false);
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

    public String getProfileImageLink() { return profileImageLink; }
    public void setProfileImageLink(String profileImageLink) { this.profileImageLink = profileImageLink; }

    public long getRevisionDate() {
        return revisionDate;
    }
    public void setRevisionDate(long revisionDate) {
        this.revisionDate = revisionDate;
    }
}
