package com.example.technica_valtracker.controller;

import atlantafx.base.theme.Styles;
import com.example.technica_valtracker.Constants;
import com.example.technica_valtracker.HelloApplication;
import com.example.technica_valtracker.UserManager;
import com.example.technica_valtracker.api.ResponseBody;
import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.db.model.Champion;
import com.example.technica_valtracker.db.model.League;
import com.example.technica_valtracker.db.model.Summoner;
import com.example.technica_valtracker.db.model.User;
import com.example.technica_valtracker.utils.URLBuilder;
import static com.example.technica_valtracker.api.Query.getQuery;
import static com.example.technica_valtracker.utils.Deserialiser.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;

import org.jetbrains.annotations.NotNull;

public class Manage_FriendsController {

    @FXML
    private ListView<String> friendsListView; // A list of friends
    @FXML
    private TextField emailTextField; // A text field for entering a friend's email
    @FXML
    private VBox friendContainer; // Container to hold friend details and actions
    @FXML
    private Button onAddConfirm; // Button to add a new friend
    @FXML
    private Button onDelete; // Button to delete a friend


    /**
     * Handles the action when the "Match History" menu item is clicked.
     *
     * This method switches the current scene to the match history screen.
     * It loads the corresponding FXML file for the match history view.
     *
     * @param 'event the event triggered when the menu item is clicked
     * @throws IOException if there is an issue loading the FXML file
     */
    @FXML
    private void OnMatchHistoryMenu(ActionEvent actionEvent) throws IOException {

        //switch to the match history scene
        Stage stage = (Stage) friendsListView.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("match_history-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);
    }

    /**
     * Handles the action when the "Manage Friends" menu item is clicked.
     *
     * This method switches the current scene to the manage friends screen.
     * It loads the corresponding FXML file for the manage friends view.
     *
     * @param 'event the event triggered when the menu item is clicked
     * @throws IOException if there is an issue loading the FXML file
     */
    @FXML
    private void OnManageFriendsMenuClick(ActionEvent actionEvent) throws IOException {
        //switch to the Friends scene
        Stage stage = (Stage) friendsListView.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Manage-Friends.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);

    }

    /**
     * Handles the action when the "Friends" menu item is clicked.
     *
     * This method switches the current scene to the friends screen.
     * It loads the corresponding FXML file for the friends view.
     *
     * @param 'event the event triggered when the menu item is clicked
     * @throws IOException if there is an issue loading the FXML file
     */
    @FXML
    private void OnFriendsMenuClick(ActionEvent actionEvent) throws IOException {
        //switch to the Friends scene
        Stage stage = (Stage) friendsListView.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Friends-View.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);
    }

    /**
     * Handles the action when the "Add" button is pressed.
     *
     * This method checks if the email field is not empty, verifies if the user exists in the system,
     * and adds the email to the friends list if valid. If the email is invalid or does not exist, it shows an error message.
     * If a user is successfully added, it displays a success message.
     */
    @FXML
    private void onAddConfirm() {
        String email = emailTextField.getText();

        if (!email.isEmpty()) {
            // Check if the email exists in the database
            User user = UserManager.getUserByEmail(email);

            if (user != null) {
                // If user exists, add the email to the friendsListView
                friendsListView.getItems().add(email);
                emailTextField.clear(); // Clear the text field after adding

                // Show a success alert indicating that the user was added as a friend
                showAlert("Friend Added", "You have successfully added user " + email + " as a friend.");
            } else {
                // If user does not exist, show an alert or an error message
                showAlert("User not found", "This player does not currently have an account with the Tracker!");
            }
        } else {
            showAlert("Input Error", "Please enter a valid email address.");
        }
    }

    /**
     * Handles the action when the "Delete" button is pressed.
     *
     * This method shows a confirmation dialog asking if the user is sure about deleting the selected friend.
     * If confirmed, the friend is removed from the friends list and an alert is shown.
     * If no friend is selected, no action is taken.
     */
    @FXML
    private void onDelete() {
        // Action when the 'Delete' button is pressed
        String selectedFriend = friendsListView.getSelectionModel().getSelectedItem();

        if (selectedFriend != null) {
            // Show confirmation dialog
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to delete this friend?");

            // Add Yes and No buttons
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // If user clicked OK, proceed with deletion
                friendsListView.getItems().remove(selectedFriend);
                showAlert("Friend Deleted", "Your friend has been deleted.");
            }
            // If user clicked cancel or closed the dialog, no action is taken
        } else {
            // Optionally, you could also show an alert if no friend is selected
            showAlert("No Selection", "Please select a friend to delete.");
        }
    }

    /**
     * Handles the action when the "Log Out" menu item is clicked.
     *
     * This method switches the current scene to the login/signup screen.
     *
     * @param actionEvent the event triggered when the menu item is clicked
     * @throws IOException if loading the new scene fails
     */
    @FXML
    public void onLogOutMenuClick(ActionEvent actionEvent) throws IOException {
        //switch to the login/signup screen
        Stage stage = (Stage) friendsListView.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 450);
        stage.setScene(scene);
    }

    /**
     * Helper method to display an alert.
     *
     * @param title the title of the alert dialog
     * @param message the content message to be displayed in the alert dialog
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
