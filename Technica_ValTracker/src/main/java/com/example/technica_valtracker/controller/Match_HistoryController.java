package com.example.technica_valtracker.controller;

import com.example.technica_valtracker.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class Match_HistoryController {

    //FXML ELEMENTS

    //Buttons
    @FXML private Button soloModeButton;
    @FXML private Button flexModeButton;

    //Static Labels
    @FXML private Label HistoryPageHeaderLabel;

    //Changing Labels

    @FXML private Label HistoryMatch1_Win;
    @FXML private Label HistoryMatch1_Mode;
    @FXML private Label HistoryMatch1_KDA;
    @FXML private Label HistoryMatch1_KD;
    @FXML private Label HistoryMatch1_CSpermin;
    @FXML private Label HistoryMatch1_Date;
    @FXML private Label HistoryMatch1_Goldpermin;
    @FXML private Label HistoryMatch1_BuildingDamage;
    @FXML private Label HistoryMatch1_VisionScore;
    @FXML private Label HistoryMatch2_Win;
    @FXML private Label HistoryMatch2_Mode;
    @FXML private Label HistoryMatch2_KDA;
    @FXML private Label HistoryMatch2_KD;
    @FXML private Label HistoryMatch2_CSpermin;
    @FXML private Label HistoryMatch2_Date;
    @FXML private Label HistoryMatch2_Goldpermin;
    @FXML private Label HistoryMatch2_BuildingDamage;
    @FXML private Label HistoryMatch2_VisionScore;
    @FXML private Label HistoryMatch3_Win;
    @FXML private Label HistoryMatch3_Mode;
    @FXML private Label HistoryMatch3_KDA;
    @FXML private Label HistoryMatch3_KD;
    @FXML private Label HistoryMatch3_CSpermin;
    @FXML private Label HistoryMatch3_Date;
    @FXML private Label HistoryMatch3_Goldpermin;
    @FXML private Label HistoryMatch3_BuildingDamage;
    @FXML private Label HistoryMatch3_VisionScore;
    @FXML private Label HistoryMatch4_Win;
    @FXML private Label HistoryMatch4_Mode;
    @FXML private Label HistoryMatch4_KDA;
    @FXML private Label HistoryMatch4_KD;
    @FXML private Label HistoryMatch4_CSpermin;
    @FXML private Label HistoryMatch4_Date;
    @FXML private Label HistoryMatch4_Goldpermin;
    @FXML private Label HistoryMatch4_BuildingDamage;
    @FXML private Label HistoryMatch4_VisionScore;
    @FXML private Label HistoryMatch5_Win;
    @FXML private Label HistoryMatch5_Mode;
    @FXML private Label HistoryMatch5_KDA;
    @FXML private Label HistoryMatch5_KD;
    @FXML private Label HistoryMatch5_CSpermin;
    @FXML private Label HistoryMatch5_Date;
    @FXML private Label HistoryMatch5_Goldpermin;
    @FXML private Label HistoryMatch5_BuildingDamage;
    @FXML private Label matchOneCSMinLabel114;
    @FXML private Label HistoryMatch5_VisionScore;

    //ImageViews
    @FXML private ImageView HistoryMatch1_ChampImage;
    @FXML private ImageView HistoryMatch2_ChampImage;
    @FXML private ImageView HistoryMatch3_ChampImage;
    @FXML private ImageView HistoryMatch4_ChampImage;
    @FXML private ImageView HistoryMatch5_ChampImage;







//METHODS




//MenuBar Button Methods
    @FXML
    private void OnMatchHistoryMenu(ActionEvent actionEvent) throws IOException {

        //switch to the match history scene
        Stage stage = (Stage) HistoryPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("match_history-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);
    }
    @FXML
    public void OnHomeMenuClick(ActionEvent actionEvent) throws IOException {


        //switch to the dashboard scene
        Stage stage = (Stage) HistoryPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);

    }
    @FXML
    private void OnFriendsMenuClick(ActionEvent actionEvent) throws IOException {
        //switch to the Friends scene
        Stage stage = (Stage) HistoryPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Friends-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);

    }
    @FXML
    private void OnAccountMenuClick(ActionEvent actionEvent) throws IOException {

        //switch to the Account scene
        Stage stage = (Stage) HistoryPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("account-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);

    }
}
