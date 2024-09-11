package com.example.technica_valtracker;

import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.api.temp.TempResponseObject;
import com.example.technica_valtracker.db.model.League;
import com.example.technica_valtracker.db.model.Summoner;
import com.example.technica_valtracker.db.model.User;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.technica_valtracker.api.RequestService.getAccountByRiotId;
import static com.example.technica_valtracker.api.RequestService.getSummonerByPuuid;
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
    private void onAPIMagicButtonClick(ActionEvent event) throws IOException {
        // Other instantiations:
        // new summoner
        // new match_history
        // new champion mastery
        League baseLeague = new League();
        League soloLeague = null;
        League flexLeague = null;

        String sumId = "R4vyzEe6PM7NKFtjzwrMQeUkGMQkUEguo2DXW67vJlYjIBA";
        String region = "na1";

        String json = baseLeague.getLeagueData(sumId, region);
        League[] leagues = getLeagueArrayFromJson(json);

        if (leagues.length == 0) {
            ErrorMessage error = new ErrorMessage(404, "Error while fetching data from API");
            System.out.println(error.getDetail());
        }
        else {
            for (League league : leagues) {
                if (Objects.equals(league.getQueueType(), "RANKED_FLEX_SR")) {
                    flexLeague = league;
                }
                if (Objects.equals(league.getQueueType(), "RANKED_SOLO_5x5")) {
                    soloLeague = league;
                }
            }
        }

        soloLeague.setWinrate();
        System.out.println(soloLeague.getWinrate());
    }

    @FXML
    private void onBackButtonClick(ActionEvent event) {
        goToPreviousScene(event);
    }


    @FXML
    private void LoginButtonClick(ActionEvent event) {
        // Get the current text from both text fields
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        // Check if the userManager exists
        if (userManager == null) {
            showAlert(AlertType.ERROR, "Login Error", "UserManager not initialized.");
            return;
        }

        // Find the user with the provided email and password
        // This will return null if details do not match exactly
        User user = userManager.findUserByCredentials(email, password);

        // Check if user is found
        if (user != null) {
            // Login successful
            showAlert(AlertType.INFORMATION, "Login Success", "Login successful.");

            // Something happens
            user.IHaveBeenAccessed();

        } else {
            // Login failed
            // Note: Deliberately ambiguous
            showAlert(AlertType.ERROR, "Login Failed", "Invalid email or password.");
        }
    }

    @FXML
    private void RegButtonClick(ActionEvent event) throws IOException {
        // Get the current text from all fields
        String userId = "ASDASIF@((@#(";
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        String riotID = riotIDTextField.getText();
        String selectedRegion = regionComboBox.getValue();

        // Validate the input fields
        // Note: this is supposed to validate that an option has been used in the combobox
        // but I couldnt get it to work, so at the moment users dont have to send a region
        if (email.isEmpty() || password.isEmpty() || riotID.isEmpty() || "Select region".equals(selectedRegion)) {
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

        // Create a new User object
        User newUser = new User(userId, riotID, password, email);

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
            goToPreviousScene(event);
        } else {
            // Note: May want a better error system then above. if this error is reached it almost certainly is a riotID conflict
            showAlert(AlertType.ERROR, "Registration Failed", "Registration failed. RiotID may already be taken.");
        }
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

    /// Sanity check for class initialization
    public HelloController() {
        System.out.println("HelloController initialized");
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