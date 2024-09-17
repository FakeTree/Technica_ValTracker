package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class APIRequestService {

    private static final String API_URL = "https://ddragon.leagueoflegends.com/cdn/13.3.1/data/en_US/champion.json"; // Replace with actual API URL

    public APIRequestService() {
        // No-argument constructor
    }

    public String fetchChampionData() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }

    public void fetchAndStoreChampionData() {
        try {
            String data = fetchChampionData();
            // Process and store the data as needed
            System.out.println("Fetched champion data: " + data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
