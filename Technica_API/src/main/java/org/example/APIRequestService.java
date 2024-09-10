package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class APIRequestService {

    public APIRequestService() {} // Empty constructor for now

    public static String sendGetRequest(String requestLink) throws IOException {
        String json;

        OkHttpClient client = new OkHttpClient();

        // Build GET request
        Request request = new Request.Builder()
                .header("X-Riot-Token", Constants.ANNETTE_RIOT_KEY)
                .url(requestLink)
                .build();

        // Send request to client
        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                json = response.body().string();
            } else {
                json = "Error fetching API data from " + requestLink;
            }
        }

        return json;
    }

    public static void fetchAndStoreChampionData() throws IOException {
        String url = "https://ddragon.leagueoflegends.com/cdn/13.1.1/data/en_US/champion.json";
        String json = sendGetRequest(url);

        // Deserialize JSON into a Map
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> data = objectMapper.readValue(json, Map.class);
        Map<String, Object> champions = (Map<String, Object>) data.get("data");

        // Store champions in DB
        try {
            ChampionDataHandler.storeChampionsInDB(champions);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exception
        }
    }
}
