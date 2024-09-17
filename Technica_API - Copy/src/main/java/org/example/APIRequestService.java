package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class APIRequestService {

    private static final String CHAMPION_DATA_URL = "https://ddragon.leagueoflegends.com/cdn/13.3.1/data/en_US/champion.json";

    public static void fetchAndStoreChampionData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(CHAMPION_DATA_URL).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                ObjectMapper objectMapper = new ObjectMapper();

                // Parse the response to a JsonNode
                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode dataNode = rootNode.get("data"); // "data" contains all the champions

                // Initialize an empty list to store champions
                List<Champion> champions = new ArrayList<>();

                // Iterate through the entries in the "data" node
                Iterator<String> fieldNames = dataNode.fieldNames();
                while (fieldNames.hasNext()) {
                    String key = fieldNames.next();
                    JsonNode championNode = dataNode.get(key);

                    // Extract champion details
                    String id = championNode.get("id").asText();
                    String name = championNode.get("name").asText();
                    String title = championNode.get("title").asText();
                    String blurb = championNode.get("blurb").asText();
                    String iconUrl = "https://ddragon.leagueoflegends.com/cdn/13.3.1/img/champion/" + id + ".png";

                    // Create Champion object and add to list
                    Champion champion = new Champion(id, name, title, blurb, iconUrl);
                    champions.add(champion);
                }

                // Now store the champions in the database
                ChampionDataHandler.storeChampionsInDB(champions);
            } else {
                System.err.println("Failed to fetch champion data");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
