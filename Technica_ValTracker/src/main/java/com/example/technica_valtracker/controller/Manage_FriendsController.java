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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Manage_FriendsController {

    @FXML
    private TextField emailTextField;
    @FXML
    private Button addFriendButton;
    @FXML
    private ListView<String> friendsListView;
    @FXML
    private Button deleteFriendButton;




}
