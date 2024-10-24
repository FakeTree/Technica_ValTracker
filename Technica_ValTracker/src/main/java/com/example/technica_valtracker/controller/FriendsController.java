package com.example.technica_valtracker.controller;

import com.example.technica_valtracker.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;


public class FriendsController {

    //FXML ELEMENTS

    //MENUITEMS
    @FXML private MenuItem Home_Menu;
    @FXML private MenuItem Match_History_Menu;
    @FXML private MenuItem Friends_Menu;
    @FXML private MenuItem Account_Menu;
    //TEXTFIELD
    @FXML private TextField compareFriendSearchBox;
    //BUTTONS
    @FXML private Button compareFriendSearchButton;
    @FXML private Button soloModeButton;
    @FXML private Button flexModeButton;
    //LABELS
    @FXML private Label comparePlayerWinrate;
    @FXML private Label comparePlayerKDA;
    @FXML private Label comparePlayerCS;
    @FXML private Label comparePlayerLP;
    @FXML private Label compareFriendHeadingLabel;
    @FXML private Label compareFriendWinrate;
    @FXML private Label compareFriendKDA;
    @FXML private Label CompareFriendCS;
    @FXML private Label compareFriendLP;
    @FXML private Label friendPageHeaderLabel;




    @FXML
    private void OnMatchHistoryMenu(ActionEvent actionEvent) throws IOException {

        //switch to the match history scene
        Stage stage = (Stage) friendPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("match_history-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);
    }
    @FXML
    public void OnHomeMenuClick(ActionEvent actionEvent) throws IOException {


        //switch to the dashboard scene
        Stage stage = (Stage) friendPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Dashboard-view.fxml"));
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
        Stage stage = (Stage) friendPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Manage-Friends.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);

    }
    @FXML
    private void OnFriendsMenuClick(ActionEvent actionEvent) throws IOException {
        //switch to the Friends scene
        Stage stage = (Stage) friendPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Friends-View.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);

    }
    @FXML
    private void OnAccountMenuClick(ActionEvent actionEvent) throws IOException {

        //switch to the Account scene
        Stage stage = (Stage) friendPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("account-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);

    }

    @FXML
    public void onLogOutMenuClick(ActionEvent actionEvent) throws IOException {

        //switch to the login/signup screen
        Stage stage = (Stage) friendPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 450);
        stage.setScene(scene);
    }

    //replace values with those from solo mode
    @FXML
    public void onSoloModeClick(ActionEvent actionEvent) {

    }


    //replace values with those from flex mode
    @FXML
    public void onFlexModeClick(ActionEvent actionEvent) {
    }

    //Method to search for a friends stats and add to the friend menu
    @FXML
    public void onFriendSearchClick(ActionEvent actionEvent) {


    }

}








