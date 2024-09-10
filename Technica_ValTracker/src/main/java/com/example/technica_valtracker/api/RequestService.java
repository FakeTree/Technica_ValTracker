package com.example.technica_valtracker.api;

import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.api.error.ErrorResponseInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import com.example.technica_valtracker.Constants;

import static com.example.technica_valtracker.utils.Deserialiser.*;

/**
 * Collection of functions that handle sending requests to the Riot API and parsing the output.
 */
public class RequestService {
    /**
     * Sends a GET request to the Riot API to the provided URL.
     * @param requestLink API request link
     * @return JSON response body as string
     */
    public static String sendGetRequest(String requestLink) throws IOException {
        String json;

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ErrorResponseInterceptor())
                .build();

        // Build GET request
        Request request = new Request.Builder()
                .header("X-Riot-Token", Constants.RIOT_API_KEY)
                .url(requestLink)
                .build();

        // Send request to client, if successful parse as string
        try (Response response = client.newCall(request).execute()) {
            json = response.body().string();

            if (!response.isSuccessful()) {
                ErrorMessage error  = getErrorMessageFromJson(json);
                return error.getDetail();
            }

            if (json.isEmpty()) {
                ErrorMessage error = new ErrorMessage(404, "Error while fetching data from API");
                return error.getDetail();
            }
        }
        return json;
    }

    /**
     * Gets a user's game account information based on their Riot username and tag.
     * @param userName The player's Riot username.
     * @param tagLine The player's Riot tag.
     * @return The response body parsed as a string.
     */
    public static String getAccountByRiotId(String userName, String tagLine) throws IOException {
        String url = Constants.ACCOUNTV1_BASE_LINK + userName + "/" + tagLine;
        return sendGetRequest(url);
    }

    /**
     * Gets a user's Summoner information based on their encrypted PUUID.
     * @param region Server region of the player to send the request to.
     * @param puuid The user's encrypted PUUID.
     * @return The response body parsed as a string.
     */
    public static String getSummonerByPuuid(String region, String puuid) throws IOException {
        String url = "https://" + region + ".api.riotgames.com/lol/summoner/v4/summoners/by-puuid/" + puuid;
        return sendGetRequest(url);
    }

    /**
     * Get a user's League information based on their encrypted summoner ID.
     * @param region Server region of the player to send the request to.
     * @param summonerId The user's summoner ID.
     * @return The response body parsed as a string.
     */
    public static String getLeagueBySummonerId(String region, String summonerId) throws IOException {
        String url = "https://" + region + ".api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerId;
        return sendGetRequest(url);
    }

}
