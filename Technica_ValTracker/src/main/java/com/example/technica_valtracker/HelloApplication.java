package com.example.technica_valtracker;

import com.example.technica_valtracker.db.DbConnection;
import com.example.technica_valtracker.db.IUserDAO;
import com.example.technica_valtracker.db.dao.UserDAO;
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
        // Create Scene
        Scene scene = new Scene(fxmlLoader.load(), 450, 450);
        stage.setTitle("Technica Tracker");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        IUserDAO userDAO;

        Connection connection = DbConnection.getInstance();
        userDAO = new UserDAO();// Connect to db
        launch();                                           // Launch javaFX app
    }
}