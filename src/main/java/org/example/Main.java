package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Fetch and store champion data
        APIRequestService.fetchAndStoreChampionData();

        // Example to test the JavaFX display
        ChampionDisplayApp.launch(ChampionDisplayApp.class, args);
    }
}
