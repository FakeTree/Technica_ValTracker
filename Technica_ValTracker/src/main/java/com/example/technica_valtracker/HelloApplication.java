package com.example.technica_valtracker;

import com.example.technica_valtracker.db.DbConnection;
import com.example.technica_valtracker.db.dao.*;
import com.example.technica_valtracker.db.model.League;
import com.example.technica_valtracker.db.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

public class HelloApplication extends Application {
    Connection connection;
    @Override
    public void start(Stage stage) throws IOException {

        // A singleton UserManager has been used and is therefore self initialized and accessible anywhere.
        // Powerful but might make debugging hard
        UserManager userManager = UserManager.getInstance();
        // Define and add static test users
        // They need all details in order for the DB to function
        userManager.addUser(new User("2o0oH2mB1yACRUlp1IA90ncgreXaMkR_KXzndaBCPYxnNh6YoNPjvd3rrBh5jkogYH5xT9GIsios5i", "user1@example.com", "password1", "Riot#ID1"));
        userManager.addUser(new User("dAwZYLh0XPQr6mw_rYAd9tSU1OBdjlFcmPJDYt8nqxtefC5zH1Z8LZlArxu9lUdMknlx8_kY5y_Lcf", "user2@example.com", "password2", "Riot#ID2"));
        userManager.addUser(new User("IJKT5O60Z3DuUsbZCKKFuKrv4p5QpKTkT4r7vcpNkuYU1lGlyy21tWKmtKXHTyb7PK8hU9eUM6tDRt", "user3@example.com", "password3", "Riot#ID3"));
        userManager.addUser(new User("Yu0Y0EGEUgkB90Nurly8f9dyUQNzOdDxyntp0zF9RBgfUq1DR5qhbqF9R622H9f0zHy4JkEG12XjR7", "user4@example.com", "password4", "Riot#ID4"));
        userManager.addUser(new User("MHBzH1hDGwyJOojAUYMv3lmlnlOLt5OHmzak_R5nBGD1z1L6hiQpUoSOjoInEbzgWttIcO7qrqoBcL", "user5@example.com", "password5", "Riot#ID5"));

        // Load FXML
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        // Create Scene
        Scene scene = new Scene(fxmlLoader.load(), 450, 450);
        stage.setTitle("Technica Tracker");
        stage.setScene(scene);

        stage.show();
    }

    /*
    Executed when program is closed.
     */
    @Override
    public void stop() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("\nConnection closed."); // DEBUGGING REMOVE LATER
            } catch (SQLException e) {
                // TODO: handle error
            }
        }
    }

    public static void main(String[] args) {
        // Probably need to move all the DAO activation stuff to
        // another function at some point
//        IUserDAO userDAO;
//
        Connection connection = DbConnection.getInstance(); // Connect to db
//        userDAO = new UserDAO();
        launch();                                           // Launch javaFX app
    }
}