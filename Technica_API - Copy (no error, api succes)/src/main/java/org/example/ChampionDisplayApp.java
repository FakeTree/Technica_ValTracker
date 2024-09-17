package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text; // Import Text from javafx.scene.text
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ChampionDisplayApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        DatabaseInitializer.initializeChampionDB();
        List<Champion> champions = fetchChampionsFromDB();

        VBox root = new VBox();
        if (champions.isEmpty()) {
            root.getChildren().add(new Text("No champions found."));
        } else {
            for (Champion champion : champions) {
                HBox hbox = new HBox();
                Image image = new Image(champion.getIconUrl());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                Text nameText = new Text(champion.getName());
                Text rankText = new Text(champion.getRank());

                hbox.getChildren().addAll(imageView, nameText, rankText);
                root.getChildren().add(hbox);
            }
        }

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Champion Display");
        primaryStage.show();
    }

    private List<Champion> fetchChampionsFromDB() {
        List<Champion> champions = new ArrayList<>();
        String query = "SELECT * FROM champions";

        try (Connection connection = DatabaseInitializer.getChampionDBConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String title = rs.getString("title");
                String blurb = rs.getString("blurb");
                String iconUrl = rs.getString("icon_url");
                String rank = rs.getString("rank");

                Champion champion = new Champion(id, name, title, blurb, iconUrl, rank);
                champions.add(champion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return champions;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
