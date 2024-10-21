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
import com.example.technica_valtracker.controller.match_panes.LargeMatchPane;
import com.example.technica_valtracker.db.model.*;
import com.example.technica_valtracker.utils.URLBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import static com.example.technica_valtracker.api.Query.getQuery;
import static com.example.technica_valtracker.utils.Deserialiser.*;

/**
 * Controller for the Match History scene.
 * Handles the initialization, user interface updates, and task execution for loading match history data.
 */
public class Match_HistoryController extends HelloApplication {

    // FXML ELEMENTS

    // Static Labels
    @FXML private Label HistoryPageHeaderLabel;

    // Boxes
    @FXML private VBox pageBox;
    @FXML private VBox statVBox;
    @FXML private ScrollPane matchScrollBox;
    @FXML private VBox matchContainer;

    // Internal controller properties
    ThreadFactory threadFactory = Executors.defaultThreadFactory();
    ExecutorService singleThreadPool = Executors.newSingleThreadExecutor(threadFactory);
    ExecutorService matchIdThreadPool = Executors.newSingleThreadExecutor(threadFactory);
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(25, threadFactory);
    private UserManager userManager = UserManager.getInstance();
    SimpleDateFormat formatPattern = new SimpleDateFormat("dd/MM/yyyy");
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
                                    //Task<ResponseBody> MatchTask = getMatchTask(matchID, region);
                                    System.out.println("Submitting match task..."); // TODO REMOVE TESTING ONLY
                                    Task<Boolean> ProcessMatchesTask = getProcessMatchesTask(matchBucket.getMatchIds(), region, puuid);
                                    matchIdThreadPool.submit(ProcessMatchesTask);
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
     * Gets a Task which iterates through a list of match IDs and retrieves individual match information including KDA
     * (average kill ratio) and CS/min (creep score per minute), returning a boolean indicating if the queries were
     * successful. If the Tasks were all completed (return true), calculates the average KDA and cs/min across all matches and
     * injects the values into their corresponding FXML fields.
     * @param matchIds List of match IDs to query.
     * @param region String indicating region server to query.
     * @param puuid String indicating player ID to query.
     * @return Boolean indicating whether the query loop was successful.
     */
    private @NotNull Task<Boolean> getProcessMatchesTask(List<String> matchIds, String region, String puuid) {
        // Create a Task which iterates through a list of matchIDs and retrieves individual match data.
        Task<Boolean> ProcessMatchesTask = new Task<Boolean>() {
            @Override protected Boolean call() throws Exception {
                boolean tasksFinished = false;
                int matchCounter = 0;

                // Get individual match data from matchId list
                for (String matchId : matchIds) {
                    if (matchCounter < 20) {
                        Task<ResponseBody> MatchTask = getMatchTask(matchId, region, puuid, true);
                        fixedThreadPool.submit(MatchTask);
                        matchCounter++;
                        if(matchCounter%10 == 0) {
                            TimeUnit.SECONDS.sleep(1);
                            System.out.println("WAITING... STOP PINGING SO MUCH");
                        }

                    }
                    else {
                        Task<ResponseBody> MatchTask = getMatchTask(matchId, region, puuid, false);
                        fixedThreadPool.submit(MatchTask);
                    }
                }

                // Wait for tasks to finish
                try {
                    fixedThreadPool.shutdown();
                    if (fixedThreadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                        // If all queued tasks have finished, return true
                        tasksFinished = true;
                    }
                } catch (InterruptedException e) {
                    // tasksFinished remains false if error occurred.
                }

                return tasksFinished;
            }
        };

        // Check task completion state; if tasks were completed, get average KDA and CS/min and inject into FXML.
        // If tasks were not completed, show alert popup.
        ProcessMatchesTask.setOnSucceeded(new EventHandler() {
            @Override public void handle(Event event) {
                System.out.println("Process Match Task succeeded."); // TODO REMOVE
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        boolean isTasksDone = ProcessMatchesTask.getValue();

                        if (isTasksDone) {
                        }
                        else {
                            showAlert(501, "Match query failed!");
                        }
                    }
                });
            }
        });
        return ProcessMatchesTask;
    }

    /**
     * Gets a Task which retrieves data for a single match and inserts individual match data values into a Match object
     * and a MatchBucket instance.
     * @param matchId String indicating match ID to query.
     * @param region String indicating region server to query.
     * @param puuid String indicating player ID to query.
     * @return ResponseBody with the JSON string and error status indicator.
     */
    private @NotNull Task<ResponseBody> getMatchTask(String matchId, String region, String puuid, Boolean createPane) throws IOException {
        // Declare a Task which performs the API query and returns the JSON string or error if failed.
        Task<ResponseBody> MatchTask = new Task<ResponseBody>() {
            @Override
            protected ResponseBody call() throws Exception {
                String matchReqUrl = URLBuilder.buildMatchRequestUrl(matchId, region);
                return getQuery(matchReqUrl, Constants.requestHeaders);
            }
        };

        MatchTask.setOnSucceeded(new EventHandler() {
            @Override public void handle(Event event) {
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
                            int usrIdx = Match.getParticipantIndexByPuuid(match.getInfo().getParticipants(), puuid);

                            match.getInfo().getParticipants().get(usrIdx).setMinionKillTotal();

                            matchBucket.addKDA(match.getInfo().getParticipants().get(usrIdx).getChallenges().getKda());
                            if (match.getInfo().getParticipants().get(usrIdx).getWin())
                                matchBucket.addWinRate();
                            double GoldpMin = match.getInfo().getParticipants().get(usrIdx).getGoldEarned()/
                                    (match.getInfo().getGameDuration()/60.0f);
                            matchBucket.addGoldPerMin(GoldpMin);
                            double CSpMin = (double) match.getInfo().getParticipants().get(usrIdx).getMinionKillTotal() /
                                    (match.getInfo().getGameDuration()/60.0f);
                            matchBucket.addCSpMin(CSpMin);

                            // Create a MiniMatchPane and inject FXML values
                            if (createPane) {
                                LargeMatchPane largeMatchPane = new LargeMatchPane();
                                // Match Result (Victory / Defeat)
                                largeMatchPane.setMatchResultText(match.getInfo().getParticipants().get(usrIdx).getWin());

                                // Mode (Ranked Solo / Ranked Flex)
                                match.getInfo().getGameMode();
                                largeMatchPane.setMatchModeText(match.getInfo().getGameMode());

                                // Mode (Ranked Solo / Ranked Flex)
                                Instant instant = Instant.ofEpochMilli( match.getInfo().getGameCreation() );
                                Date date = Date.from( instant );
                                String formatDate = formatPattern.format(date);
                                largeMatchPane.setHistoryMatch1_Date(formatDate);

                                // Champion name & icon
                                largeMatchPane.setMatchChampText(match.getInfo().getParticipants().get(usrIdx)
                                        .getChampionName());
                                largeMatchPane.setMatchChampImg(match.getInfo().getParticipants().get(usrIdx).getChampionId());

                                // K/D/A
                                String KDA = String.format("%d/%d/%d", match.getInfo().getParticipants().get(usrIdx).getKills(),
                                        match.getInfo().getParticipants().get(usrIdx).getDeaths(),
                                        match.getInfo().getParticipants().get(usrIdx).getAssists());
                                largeMatchPane.setMatchKdaValue(KDA);

                                // KDA (ratio)
                                String kdRatio = String.format("%.2f", match.getInfo().getParticipants().get(usrIdx)
                                        .getChallenges().getKda());
                                largeMatchPane.setMatchKdValue(kdRatio);

                                // CS/Min
                                String csPerMin = String.format("%.2f", CSpMin);
                                largeMatchPane.setMatchCSMinValue(csPerMin);

                                // Gold/Min
                                String goldPerMin = String.format("%.2f", GoldpMin);
                                largeMatchPane.setHistoryMatch1_Goldpermin(goldPerMin);

                                // Building Damage
                                int buildingDamage = match.getInfo().getParticipants().get(usrIdx).getdamageDealtToBuildings();
                                String buildingDamageString = String.valueOf(buildingDamage);
                                largeMatchPane.setHistoryMatch1_BuildingDamage(buildingDamageString);

                                // VisionScore
                                int visionScore = match.getInfo().getParticipants().get(usrIdx).getvisionScore();
                                String visionScoreString = String.valueOf(visionScore);
                                largeMatchPane.setHistoryMatch1_VisionScore(visionScoreString);

                                matchContainer.setFillWidth(true); // Makes the VBox fill its width to the parent container
                                matchContainer.getChildren().add(largeMatchPane);
                                matchContainer.prefHeightProperty().bind(
                                        Bindings.createDoubleBinding(() ->
                                                        matchContainer.getChildren().stream().mapToDouble(node -> node.prefHeight(-1)).sum() + matchContainer.getSpacing() * (matchContainer.getChildren().size() - 1),
                                                matchContainer.getChildren()
                                        )
                                );

                            }
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
