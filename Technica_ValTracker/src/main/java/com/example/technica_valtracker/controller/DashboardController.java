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


public class DashboardController {



    //FXML ELEMENTS

    //Menu Bar
    @FXML
    private MenuBar menuBar;
    
    //LABELS
    @FXML
    private Label matchOneResultText;
    @FXML
    private Label matchOneModeText;
    @FXML
    private Label matchOneKDAValue;
    @FXML
    private Label matchOneKDValue;
    @FXML
    private Label matchOneCSMinValue;
    @FXML
    private Label matchTwoResultText;
    @FXML
    private Label matchTwoModeText;
    @FXML
    private Label matchTwoKDAValue;
    @FXML
    private Label matchTwoKDValue;
    @FXML
    private Label matchTwoCSMinValue;
    @FXML
    private Label matchThreeResultText;
    @FXML
    private Label matchThreeModeText;
    @FXML
    private Label matchThreeKDAValue;
    @FXML
    private Label matchThreeKDValue;
    @FXML
    private Label matchThreeCSMinLabel;
    @FXML
    private Label championOneName;
    @FXML
    private Label championOnePointsValue;
    @FXML
    private Label championTwoName;
    @FXML
    private Label championTwoPointsValue;
    @FXML
    private Label championThreePointsValue;
    @FXML
    private Label championThreeName;
    @FXML
    private Label tierText;
    @FXML
    private Label rankText;
    @FXML
    private Label leaguePointsValue;
    @FXML
    private Label winrateValue;
    @FXML
    private Label kdValue;
    @FXML
    private Label csMinValue;
    @FXML
    private Label statPageHeaderLabel;



    //Menu Items
    @FXML
    private MenuItem Home_Menu;
    @FXML
    private MenuItem Match_History_Menu;
    @FXML
    private MenuItem Friends_Menu;
    @FXML
    private MenuItem Account_Menu;

    //ImageViews
    @FXML
    private ImageView matchOneChampionImage;
    @FXML
    private ImageView matchTwoChampionImage;
    @FXML
    private ImageView matchThreeChampionImage;
    @FXML
    private ImageView championOneImage;
    @FXML
    private ImageView championTwoImage;
    @FXML
    private ImageView championThreeImage;
    @FXML
    private ImageView rankBadgeImage;

    //Buttons
    @FXML
    private Button soloModeButton;
    @FXML
    private Button flexModeButton;

    // Boxes
    @FXML
    private VBox statVBox;


    //MenuBar Button Methods
    @FXML
    private void OnMatchHistoryMenu(ActionEvent actionEvent) throws IOException {

        //switch to the match history scene
        Stage stage = (Stage) statPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("match_history-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);
    }
    @FXML
    public void OnHomeMenuClick(ActionEvent actionEvent) throws IOException {


        //switch to the dashboard scene
        Stage stage = (Stage) statPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);

    }
    @FXML
    private void OnFriendsMenuClick(ActionEvent actionEvent) throws IOException {
        //switch to the Friends scene
        Stage stage = (Stage) statPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Friends-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);

    }
    @FXML
    private void OnAccountMenuClick(ActionEvent actionEvent) throws IOException {

        //switch to the Account scene
        Stage stage = (Stage) statPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("account-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);

    }

    // Pre-load api data
    @FXML
    protected void initialize() {
        statVBox.setVisible(false);
    }

    //replace values with those from solo mode
    @FXML
    private void onSoloModeButtonClick(ActionEvent actionEvent) {



    }

    //replace values with those from flex mode
    @FXML
    public void onFlexModeButtonClick(ActionEvent actionEvent) {



    }
}
