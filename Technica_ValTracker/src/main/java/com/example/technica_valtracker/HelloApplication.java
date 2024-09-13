package com.example.technica_valtracker;


import com.example.technica_valtracker.db.DbConnection;
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