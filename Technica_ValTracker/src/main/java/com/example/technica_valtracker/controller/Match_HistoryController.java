package com.example.technica_valtracker.controller;

/**
 * Controller class responsible for handling the match history view.
 * Manages UI elements and asynchronous tasks for loading and displaying match history.
 */
import atlantafx.base.theme.Styles;
import com.example.technica_valtracker.Constants;
import com.example.technica_valtracker.HelloApplication;
import com.example.technica_valtracker.UserManager;
import com.example.technica_valtracker.api.ResponseBody;
import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.db.model.*;
import com.example.technica_valtracker.utils.URLBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static com.example.technica_valtracker.api.Query.getQuery;
import static com.example.technica_valtracker.utils.Deserialiser.*;

/**
 * Controller for the Match History scene.
 * Handles the initialization, user interface updates, and task execution for loading match history data.
 */
public class Match_HistoryController extends HelloApplication {

    // FXML ELEMENTS

    // Buttons
    @FXML private Button soloModeButton;
    @FXML private Button flexModeButton;

    // Static Labels
    @FXML private Label HistoryPageHeaderLabel;

    // Changing Labels
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

    // Boxes
    @FXML private VBox pageBox;
    @FXML private VBox statVBox;

    // ImageViews
    @FXML private ImageView HistoryMatch1_ChampImage;
    @FXML private ImageView HistoryMatch2_ChampImage;
    @FXML private ImageView HistoryMatch3_ChampImage;
    @FXML private ImageView HistoryMatch4_ChampImage;
    @FXML private ImageView HistoryMatch5_ChampImage;

    // Internal controller properties
    ThreadFactory threadFactory = Executors.defaultThreadFactory();
    ExecutorService singleThreadPool = Executors.newSingleThreadExecutor(threadFactory);
    private UserManager userManager = UserManager.getInstance();
    MatchBucket matchBucket = new MatchBucket();
    User currentUser = userManager.getCurrentUser();

    /**
     * Called after the FXML file has been loaded.
     * Initializes the controller and sets up any necessary components.
     */
    @FXML
    protected void initialize() {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                try {
                    init();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Initializes the view by fetching match history data from the API.
     * Retrieves the user's match history and updates the UI with the data.
     *
     * @throws IOException If an I/O error occurs while fetching the data.
     */
    public void init() throws IOException {

        System.out.println("INIT!"); // TODO REMOVE TESTING ONLY
        String riotId = currentUser.getRiotID();
        String puuid = currentUser.getUserId();
        String region = currentUser.getRegion().toLowerCase();

        // TODO REMOVE TESTING ONLY
        System.out.println(riotId);
        System.out.println(puuid);
        System.out.println(region);

        Task<ResponseBody> AllMatchIdsTask = getAllMatchIdsTask(puuid, region, riotId);

        singleThreadPool.submit(AllMatchIdsTask);
    }

    /**
     * Creates a task that retrieves all match IDs for the current user.
     *
     * @param puuid The user's PUUID (Player Unique ID).
     * @param region The region of the player.
     * @param riotId The user's Riot ID.
     * @return A {@link Task} that fetches all match IDs.
     */
    private @NotNull Task<ResponseBody> getAllMatchIdsTask(String puuid, String region, String riotId) {

        // Declare a Task which performs the API query and returns the JSON string or error if failed.
        Task<ResponseBody> AllMatchIdsTask = new Task<ResponseBody>() {

            @Override
            protected ResponseBody call() throws Exception {
                String matchReqUrl = URLBuilder.buildMatchIDsRequestUrl(puuid, region);
                return getQuery(matchReqUrl, Constants.requestHeaders);
            }
        };

        /*
         * Event handler that runs when the task succeeds;
         * Deserializes the JSON output and updates the match history UI.
         */
        AllMatchIdsTask.setOnSucceeded(new EventHandler() {

            @Override public void handle(Event event) {
                // Switch from the background thread to the application thread to update UI
                Platform.runLater(new Runnable() {
                    @Override public void run() {

                        ResponseBody allMatchIdsQuery = AllMatchIdsTask.getValue();

                        if (allMatchIdsQuery.isError()) {
                            System.out.println("Response returned error!"); // TODO REMOVE TESTING ONLY
                            ErrorMessage error = allMatchIdsQuery.getMessage();
                            showAlert(error.getStatus(), error.getDetail());
                        }
                        else {
                            System.out.println("Success!"); // TODO REMOVE TESTING ONLY

                            try {
                                String json = AllMatchIdsTask.getValue().getJson();
                                System.out.println(json); // TODO REMOVE TEST ONLY

                                matchBucket.setMatchListByPUUID(json);
                                List<String> matches = matchBucket.getMatchIds();

                                for(String matchID : matches) {
                                    Task<ResponseBody> MatchTask = getMatchTask(matchID, region);
                                    System.out.println("Submitting match task..."); // TODO REMOVE TESTING ONLY
                                    singleThreadPool.submit(MatchTask);
                                }

                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
        });
        return AllMatchIdsTask;
    }

    /**
     * Creates a task that retrieves match data for a given match ID.
     *
     * @param matchID The ID of the match to fetch.
     * @param region The region of the player.
     * @return A {@link Task} that fetches match data.
     * @throws IOException If an I/O error occurs while fetching the match data.
     */
    private @NotNull Task<ResponseBody> getMatchTask(String matchID, String region) throws IOException {
        // Declare a Task which performs the API query and returns the JSON string or error if failed.
        Task<ResponseBody> MatchTask = new Task<ResponseBody>() {
            @Override
            protected ResponseBody call() throws Exception {
                String matchReqUrl = URLBuilder.buildMatchRequestUrl(matchID, region);
                System.out.println(matchReqUrl+"?api_key="+Constants.RIOT_API_KEY);
                return getQuery(matchReqUrl, Constants.requestHeaders);
            }
        };

        /*
         * Event handler that runs when the task succeeds;
         * Deserializes the JSON output and updates the match data in the UI.
         */
        MatchTask.setOnSucceeded(new EventHandler() {

            @Override public void handle(Event event) {
                // Switch from the background thread to the application thread to update UI
                Platform.runLater(new Runnable() {
                    @Override public void run() {

                        ResponseBody matchQuery = MatchTask.getValue();

                        if (matchQuery.isError()) {
                            System.out.println("Response returned error!"); // TODO REMOVE TESTING ONLY
                            ErrorMessage error = matchQuery.getMessage();
                            showAlert(error.getStatus(), error.getDetail());
                        }
                        else {

                            String json = MatchTask.getValue().getJson();
                            Match match = null;
                            try {
                                match = getMatchArrayFromJson(json);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                            matchBucket.addMatch(match.getMetadata().getMatchId(), match);
                            // Get Player Number
                            int usrIdx = Match.getParticipantIndexByPuuid(match.getInfo().getParticipants(), currentUser.getUserId());

                            matchBucket.addKDA(match.getInfo().getParticipants().get(usrIdx).getChallenges().getKda());
                            if (match.getInfo().getParticipants().get(usrIdx).getWin())
                                matchBucket.addWinRate();
                            double GoldpMin = match.getInfo().getParticipants().get(usrIdx).getGoldEarned()/
                                    (match.getInfo().getGameDuration()/60.0f);
                            matchBucket.addGoldPerMin(GoldpMin);
                            double CSpMin = (double) match.getInfo().getParticipants().get(usrIdx).gettotalMinionsKilled() /
                                    (match.getInfo().getGameDuration()/60.0f);
                            matchBucket.addCSpMin(CSpMin);
                            System.out.println("KDA: "+matchBucket.getKDAAcrossAllGames());
                            System.out.println("Win Rate: "+matchBucket.getWinRateAcrossAllGames());
                            System.out.println("CS per Min: "+matchBucket.getCSpMinAcrossAllGames());
                            System.out.println("Gold per min: "+matchBucket.getGoldPerMinAcrossAllGames());

                            singleThreadPool.submit(MatchTask);
                        }
                    }
                });
            }
        });

        return MatchTask;
    }

    /**
     * Handles the event when the "Match History" menu item is clicked.
     * Switches the view to the match history scene.
     *
     * @param actionEvent The event that triggered this action.
     * @throws IOException If an error occurs during scene loading.
     */
    @FXML
    private void OnMatchHistoryMenu(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) HistoryPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("match_history-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);
    }

    /**
     * Handles the event when the "Home" menu item is clicked.
     * Switches the view to the dashboard scene.
     *
     * @param actionEvent The event that triggered this action.
     * @throws IOException If an error occurs during scene loading.
     */
    @FXML
    public void OnHomeMenuClick(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) HistoryPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);
    }

    /**
     * Handles the event when the "Friends" menu item is clicked.
     * Switches the view to the friends scene.
     *
     * @param actionEvent The event that triggered this action.
     * @throws IOException If an error occurs during scene loading.
     */
    @FXML
    private void OnFriendsMenuClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) HistoryPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Friends-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);
    }

    /**
     * Handles the event when the "Account" menu item is clicked.
     * Switches the view to the account scene.
     *
     * @param actionEvent The event that triggered this action.
     * @throws IOException If an error occurs during scene loading.
     */
    @FXML
    private void OnAccountMenuClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) HistoryPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("account-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);
    }

    /**
     * Handles the event when the "Log Out" menu item is clicked.
     * Clears the user session and switches to the login/signup screen.
     *
     * @param actionEvent The event that triggered this action.
     * @throws IOException If an error occurs during scene loading.
     */
    @FXML
    public void onLogOutMenuClick(ActionEvent actionEvent) throws IOException {
        userManager.getUserStatList().clear();

        Stage stage = (Stage) HistoryPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);
    }

    /**
     * Displays an alert with the provided error status and message.
     *
     * @param status The error status code.
     * @param detail The detailed error message.
     */
    private void showAlert(int status, String detail) {
        var alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText("There was a problem while loading the dashboard data");
        alert.setContentText(String.format("%d\n%s", status, detail));
        alert.initOwner(pageBox.getScene().getWindow());

        alert.show();
    }
}
