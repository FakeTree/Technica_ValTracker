package com.example.technica_valtracker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import java.io.IOException;

// Note that this Controller currently handles three windows
// 1. Hello Window
// 2. Login window
// 3. Register Window
// This may want to be separated later for clarity

public class HelloController {
    // Reference to the previous scene
    private Scene previousScene;



    // Method to set the previous scene
    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }

    /// Text fields
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField riotIDTextField;
    @FXML
    private TextField nicknameTextField;

    // Text
    @FXML
    private Label welcomeText;

    // Buttons
    @FXML
    private void onBackButtonClick(ActionEvent event) {
        if (previousScene != null) {
            // Get the current stage using the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the previous scene
            stage.setScene(previousScene);
            stage.show();
        }
    }
    @FXML
    private void LoginButtonClick(ActionEvent event) {
        // Get the current text from both text fields
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        // Print the values or use them as needed
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

    }
    @FXML
    private void RegButtonClick(ActionEvent event) {
        // Get the current text from all fields
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        String riotID = riotIDTextField.getText();
        String nickname = nicknameTextField.getText();

        // Print the values or use them as needed
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println("Riot ID: " + riotID);
        System.out.println("Nickname: " + nickname);

    }
    @FXML
    private void onLoginButtonClick(ActionEvent event) {
        try {
            // Load the new scene from the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = loader.load();

            // Get the current stage using the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Get current scene dimensions
            double width = stage.getScene().getWidth();
            double height = stage.getScene().getHeight();

            // Create a new scene with the same dimensions
            Scene newScene = new Scene(root, width, height);

            // Store the current scene as previous before switching
            HelloController controller = loader.getController();
            controller.setPreviousScene(stage.getScene());

            // Set the new scene
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onRegButtonClick(ActionEvent event) {
        try {
            // Load the new scene from the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register-view.fxml"));
            Parent root = loader.load();

            // Get the current stage using the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Get current scene dimensions
            double width = stage.getScene().getWidth();
            double height = stage.getScene().getHeight();

            // Create a new scene with the same dimensions
            Scene newScene = new Scene(root, width, height);

            // Store the current scene as previous before switching
            HelloController controller = loader.getController();
            controller.setPreviousScene(stage.getScene());

            // Set the new scene
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // Closes the current window
    private void closeCurrentWindow() {
        // Get the current window
        Stage stage = (Stage) welcomeText.getScene().getWindow();
        if (stage != null) {
            stage.close(); // Close the current window
        }
    }
}