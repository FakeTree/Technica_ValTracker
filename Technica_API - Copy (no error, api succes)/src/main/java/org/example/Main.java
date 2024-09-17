package org.example;

public class Main {
    public static void main(String[] args) {
        // Initialize the database
        DatabaseInitializer.initializeChampionDB();

        // Create an instance of APIRequestService
        APIRequestService apiRequestService = new APIRequestService();

        // Fetch and store champion data
        apiRequestService.fetchAndStoreChampionData();
    }
}
