package com.example.technica_valtracker.controller;

import com.example.technica_valtracker.Constants;
import com.example.technica_valtracker.HelloApplication;
import com.example.technica_valtracker.UserManager;
import com.example.technica_valtracker.api.ResponseBody;
import com.example.technica_valtracker.db.model.Champion;
import com.example.technica_valtracker.db.model.League;
import com.example.technica_valtracker.db.model.Summoner;
import com.example.technica_valtracker.db.model.User;
import com.example.technica_valtracker.utils.PasswordUtils;
import com.example.technica_valtracker.utils.URLBuilder;
import com.example.technica_valtracker.utils.Validation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;


import java.io.IOException;

import static com.example.technica_valtracker.api.Query.getQuery;
import static com.example.technica_valtracker.utils.Deserialiser.*;

// Note that this Controller currently handles three windows
// 1. Hello Window
// 2. Login window
// 3. Register Window
// This may want to be separated later for clarity


public class HelloController {
    // Access UserManager
    private UserManager userManager = UserManager.getInstance();

    // Field for remembering the previous scene
    private Scene previousScene;




    // Method to set the previous scene
    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }

    // Method to go to the previous scene
    private void goToPreviousScene(ActionEvent event) {
        if (previousScene != null) {
            // Get the current stage using the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Set the previous scene
            stage.setScene(previousScene);
            stage.show();
        }
    }

    /// Text fields
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField riotIDTextField;
    @FXML
    private ComboBox<String> regionComboBox;

    // Text
    @FXML
    private Label welcomeText;

    // Buttons
    @FXML
    private Button signUpSubmitButton;
    @FXML
    private Button LoginsubmitButton;

    // Button events
    @FXML
    private void onAPIMagicButtonClick() throws IOException {
        // Only used to test API requests inside JavaFX.
        User testUser = userManager.getUserByRiotID("Lunatown#EUNE");
        String region = testUser.getRegion().toLowerCase();
        Summoner summoner = new Summoner();

        // Summoner test
        String summonerRequestUrl = URLBuilder.buildSummonerRequestUrl(testUser.getUserId(), region);

        ResponseBody summonerQuery = getQuery(summonerRequestUrl, Constants.requestHeaders);

        if (summonerQuery.isError()) {
            System.out.println(summonerQuery.getMessage().getDetail());
        }
        else {
           getSummonerFromJson(summonerQuery.getJson(), summoner);
        }

        // League test
        String leagueRequestUrl = URLBuilder.buildLeagueRequestUrl(summoner.getSummonerId(), region);

        ResponseBody leagueQuery = getQuery(leagueRequestUrl, Constants.requestHeaders);

        if (leagueQuery.isError()) {
            System.out.println(leagueQuery.getMessage().getDetail());
        }
        else {
            League[] leagues = getLeagueArrayFromJson(leagueQuery.getJson());
        }

        // Champion test
        String championRequestUrl = URLBuilder.buildChampionRequestUrl(testUser.getUserId(), region);

        ResponseBody championQuery = getQuery(championRequestUrl, Constants.requestHeaders);

        if (championQuery.isError()) {
            System.out.println(championQuery.getMessage().getDetail());
        }
        else {
            Champion[] champions = getChampionArrayFromJson(championQuery.getJson());
        }
    }

    @FXML
    private void onBackButtonClick(ActionEvent event) {
        goToPreviousScene(event);
    }


    @FXML
    private void LoginButtonClick(ActionEvent event) throws IOException {
        // Get the current text from both text fields
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        // Hash the password
        String hashedPassword = PasswordUtils.hashPassword(password);

        // API Magic
        if(email.equals("api@magic.com")) {
            onAPIMagicButtonClick();
        }
        // Check if the userManager exists
        if (userManager == null) {
            showAlert(AlertType.ERROR, "Login Error", "UserManager not initialized.");
            return;
        }

        // Find the user with the provided email and password
        // This will return null if details do not match exactly
        User user = userManager.findUserByCredentials(email, hashedPassword);

        // Check if user is found
        if (user != null) {
            // Login successful
            // Store the user for its reference point
            userManager.setCurrentUser(user);
            // Alert the user of Login success
            showAlert(AlertType.INFORMATION, "Login Success", "Login successful.");
            // Move to main page here
            Stage stage = (Stage) LoginsubmitButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
            stage.setScene(scene);
        } else {
            // Login failed
            // Note: Deliberately ambiguous
            showAlert(AlertType.ERROR, "Login Failed", "Invalid email or password.");
        }
    }

    @FXML
    private void RegButtonClick(ActionEvent event) throws IOException {
        // Get the current text from all fields
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        String riotID = riotIDTextField.getText();
        String selectedRegion = regionComboBox.getValue();

        // Validate the input fields
        // Note: this is supposed to validate that an option has been used in the combobox
        // but I couldnt get it to work, so at the moment users dont have to send a region
        if (email.isEmpty() || password.isEmpty() || riotID.isEmpty() || selectedRegion == null) {
            showAlert(AlertType.ERROR, "Registration Error", "Please fill in all fields and select a region.");
            return;
        }
        System.out.println(selectedRegion);

        // Validate the email address
        if (!Validation.isEmailValid(email)) {
            showAlert(AlertType.ERROR, "Invalid Email", "Invalid email format.");
            return;
        }

        // Validate the password
        if (!Validation.isPasswordValid(password)) {
            showAlert(AlertType.ERROR, "Invalid Password", "Invalid password format. Must be 8 chars long, 1 Capital and 1 Numeral.");
            return;
        }

        // Validate the Riot ID
        if (!Validation.isRiotIDValid(riotID)) {
            showAlert(AlertType.ERROR, "Invalid Riot ID", "Invalid Riot ID format. Must be in the format: username#tagline");
            return;
        }

        // Hash the password
        String hashedPassword = PasswordUtils.hashPassword(password);

        // Check if the Riot ID corresponds to an existing PUUID
        String puuid = Validation.puuidGet(riotID);
        if (puuid == null) {
            showAlert(AlertType.ERROR, "Invalid Riot ID", "Riot ID does not exist.");
            return;
        }

        // Create a new User object
        User newUser = new User(puuid, email, hashedPassword, riotID, selectedRegion);

        // Check if the UserManager is initialized
        if (userManager == null) {
            System.out.println("UserManager not initialized.");
            showAlert(AlertType.INFORMATION, "Error", "User Manager not initialized. Please restart application.");
            return;
        }

        // Add the user to UserManager
        boolean success = userManager.addUser(newUser);

        // Print the result
        if (success) {
            showAlert(AlertType.INFORMATION, "Registration Success", "User successfully registered.");
            userManager.setCurrentUser(newUser);
            Stage stage = (Stage) signUpSubmitButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
            stage.setScene(scene);
//            goToPreviousScene(event);
        } else {
            // Note: May want a better error system then above. if this error is reached it almost certainly is a riotID conflict
            showAlert(AlertType.ERROR, "Registration Failed", "Registration failed. RiotID may already be taken.");
        }
    }

    @FXML
    private void onLoginButtonClick(ActionEvent event) {
        try {

            // Load the new scene from the FXML file
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
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
            // TODO better error handling
            e.printStackTrace();
        }
    }
    @FXML
    private void onRegButtonClick(ActionEvent event) {
        try {
            // Load the new scene from the FXML file
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("register-view.fxml"));
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
            // TODO better error handling
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


    // Helper method to show alerts
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}