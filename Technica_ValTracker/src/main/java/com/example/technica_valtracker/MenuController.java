package com.example.technica_valtracker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MenuController {

    @FXML
private Label TTMenu;
    @FXML
private Label HomeMenu;
    @FXML
private Label TrainerMenu;
    @FXML
private Label FriendsMenu;
    @FXML
private Label AboutMenu;
    @FXML
private Label AccountMenu;
    @FXML
private Label TestLabel;






    @FXML
    protected void OnTTClick(){
       TestLabel.setText("TT Clicked");

    }

    @FXML
    protected void OnHomeClick(){
        TestLabel.setText("Home Clicked");

    }

    @FXML
    protected void OnTrainerClick(){
        TestLabel.setText("Trainer Clicked");

    }

    @FXML
    protected void OnFriendsClick(){
        TestLabel.setText("Friends Clicked");

    }

    @FXML
    protected void OnAboutClick(){
        TestLabel.setText("About Clicked");

    }

    @FXML
    protected void OnAccountClick(){
        TestLabel.setText("Account Clicked");

    }

}