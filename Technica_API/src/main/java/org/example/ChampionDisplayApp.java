package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChampionDisplayApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            String championId = "Vi"; // Example champion ID
            String iconUrl = JavaFXHelper.getChampionIconUrlFromDB(championId);

            Image championImage = new Image(iconUrl);
            ImageView imageView = new ImageView(championImage);
            Label nameLabel = new Label("Champion: " + championId);

            VBox vbox = new VBox(nameLabel, imageView);
            Scene scene = new Scene(vbox, 300, 250);

            primaryStage.setTitle("Champion Display");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
