package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ChampionDisplayApp extends Application {

    @Override
    public void start(Stage stage) {
        DatabaseInitializer.initializeDatabase();

        VBox root = new VBox(10);

        try (Connection conn = DatabaseInitializer.getConnection();
             Statement stmt = conn.createStatement()) {

            // Fetch the first champion as an example
            ResultSet rs = stmt.executeQuery("SELECT name, icon_url FROM champions LIMIT 1");
            if (rs.next()) {
                String championName = rs.getString("name");
                String iconUrl = rs.getString("icon_url");

                // Champion Icon
                Image championImage = new Image(iconUrl);
                ImageView championImageView = new ImageView(championImage);

                // Assuming rank is determined by championName for demo
                String rank = getRankByChampion(championName); // Custom logic to determine rank
                Image rankImage = RankImageLoader.loadRankImage(rank);
                ImageView rankImageView = new ImageView(rankImage);

                // Add both images to the layout
                root.getChildren().addAll(championImageView, rankImageView);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    // Sample function to determine rank based on champion name
    private String getRankByChampion(String championName) {
        switch (championName) {
            case "Champion1":
                return "Bronze";
            case "Champion2":
                return "Gold";
            case "Champion3":
                return "Silver";
            default:
                return "Unranked"; // Use a default if rank isn't determined
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
