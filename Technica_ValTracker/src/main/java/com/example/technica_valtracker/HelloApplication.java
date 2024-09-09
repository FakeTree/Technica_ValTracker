package com.example.technica_valtracker;

import com.example.technica_valtracker.db.DbConnection;
import com.example.technica_valtracker.db.dao.*;
import com.example.technica_valtracker.db.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import java.sql.Connection;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // A singleton UserManager has been used and is therefore self initialized and accessible anywhere.
        // Powerful but might make debugging hard
        UserManager userManager = UserManager.getInstance();
        // Define and add static test users
        userManager.addUser(new User("RiotID1", "password1", "user1@example.com"));
        userManager.addUser(new User("RiotID2", "password2", "user2@example.com"));
        userManager.addUser(new User("RiotID3", "password3", "user3@example.com"));
        userManager.addUser(new User("RiotID4", "password4", "user4@example.com"));
        userManager.addUser(new User("RiotID5", "password5", "user5@example.com"));


        // Load FXML
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1024, 600);
//        stage.setTitle("Technica Tracker");
        // Create Scene
        Scene scene = new Scene(fxmlLoader.load(), 450, 450);
        stage.setTitle("Technica Tracker");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        // Probably need to move all the DAO activation stuff to
        // another function at some point
        IUserDAO userDAO;
        ISummonerDAO summonerDAO;
        IMatchHistoryDAO matchHistoryDAO;

        Connection connection = DbConnection.getInstance(); // Connect to db
        userDAO = new UserDAO();
        summonerDAO = new SummonerDAO();
        matchHistoryDAO = new MatchHistoryDAO();
        launch();                                           // Launch javaFX app
    }
}