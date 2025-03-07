package com.example.technica_valtracker.controller;

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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
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
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import static com.example.technica_valtracker.api.Query.getQuery;
import static com.example.technica_valtracker.utils.Deserialiser.*;
import static com.example.technica_valtracker.utils.Deserialiser.getMatchArrayFromJson;

/**
 * Controller for the Friend Comparison view which displays the player's in-game stats.
 */
public class FriendsController {


    //FXML ELEMENTS

    //PANES
    @FXML public Pane UserWinrateBox;
    @FXML public Pane UserKDABox;
    @FXML public Pane UserCSBox;
    @FXML public Pane UserLPBox;
    @FXML public Pane FriendWinrateBox;
    @FXML public Pane FriendKDABox;
    @FXML public Pane FriendCSBox;
    @FXML public Pane FriendLPBox;
    @FXML public ImageView WinrateImageView;


    //TOGGLES
    @FXML
    ToggleGroup modeToggle;

    //BOXES
    @FXML
    private VBox pageBox;

    //MENUITEMS
    @FXML
    private MenuItem Home_Menu;
    @FXML
    private MenuItem Match_History_Menu;
    @FXML
    private MenuItem Friends_Menu;
    @FXML
    private MenuItem Account_Menu;
    //TEXTFIELD
    @FXML
    private ComboBox<String> FriendComboBox;
    //BUTTONS
    @FXML
    private Button compareFriendSearchButton;
    @FXML
    private ToggleButton soloModeButton;
    @FXML
    private ToggleButton flexModeButton;
    //LABELS
    @FXML
    private Label userWinrate;
    @FXML
    private Label userKda;
    @FXML
    private Label userCSpMin;
    @FXML
    private Label userLeaguePoints;
    @FXML
    private Label compareFriendHeadingLabel;
    @FXML
    private Label compareFriendWinrate;
    @FXML
    private Label compareFriendKDA;
    @FXML
    private Label compareFriendCS;
    @FXML
    private Label compareFriendLP;
    @FXML
    private Label friendPageHeaderLabel;
    @FXML private Label loadingLabel;



    private UserManager userManager = UserManager.getInstance();


    // Create thread pools for executing API query tasks in background threads
    ThreadFactory threadFactory = Executors.defaultThreadFactory();
    ExecutorService singleThreadPool = Executors.newSingleThreadExecutor(threadFactory);
    ExecutorService matchIdThreadPool = Executors.newSingleThreadExecutor(threadFactory);
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20, threadFactory);
    ExecutorService usrMatchIdThreadPool = Executors.newSingleThreadExecutor(threadFactory);
    ExecutorService usrFixedThreadPool = Executors.newFixedThreadPool(20, threadFactory);

    League friendsoloLeague;      // Stores the user's Solo mode stats
    League friendflexLeague;      // Stores the user's Flex mode stats

    MatchBucket matchBucket = new MatchBucket();    // Stores match history data



    /**
     * Declares an initialise method that is called just before the scene UI is fully loaded
     */
    @FXML
    protected void initialize() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    init();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    protected void init() throws IOException {

        // Get current user instance and data
        UserManager.UserStats rankedStats = userManager.getUserStatList().getFirst();
        User User = UserManager.getInstance().getCurrentUser();

        // Configure mode toggle buttons
        soloModeButton.getStyleClass().add(Styles.LEFT_PILL);
        flexModeButton.getStyleClass().add(Styles.RIGHT_PILL);
        modeToggle = new ToggleGroup();
        modeToggle.getToggles().addAll(soloModeButton, flexModeButton);

        // Set current user's stats
        Task<ResponseBody> MatchIdTask = getAllMatchIdsTask(User.getUserId(), User.getRegion().toLowerCase(), true);
        usrMatchIdThreadPool.submit(MatchIdTask);

        userWinrate.setText(String.valueOf(rankedStats.getWinrate()));
        userLeaguePoints.setText(String.valueOf(rankedStats.getLeaguePoints()));

        // TODO REMOVE -- TESTING FRIENDS FEATURE
        User.addFriend("c@c.com");
        FriendComboBox.getItems().setAll(User.getFriends());


    }
    /**
     * Handles the action when the "Match History" menu item is clicked.
     *
     * This method switches the current scene to the manage friends screen.
     * It loads the corresponding FXML file for the manage friends view.
     *
     * @param 'event the event triggered when the menu item is clicked
     * @throws IOException if there is an issue loading the FXML file
     */
    @FXML
    private void OnMatchHistoryMenu(ActionEvent actionEvent) throws IOException {

        //switch to the match history scene
        Stage stage = (Stage) friendPageHeaderLabel.getScene().getWindow();
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


    /**
     * Handles the action when the "Account" menu item is clicked.
     *
     * This method switches the current scene to the Account screen.
     *
     * @param actionEvent the event triggered when the menu item is clicked
     * @throws IOException if loading the new scene fails
     */
    @FXML
    private void OnAccountMenuClick(ActionEvent actionEvent) throws IOException {

        //switch to the Account scene
        Stage stage = (Stage) friendPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("account-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);

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
        // clear cached user stat data
        userManager.getUserStatList().clear();

        //switch to the login/signup screen
        Stage stage = (Stage) friendPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);
    }


    //Method to search for a friends stats and add to the friend menu
    public void FriendSelectButtonClick(ActionEvent actionEvent) {
        loadingLabel.setVisible(true);

        String email = FriendComboBox.getSelectionModel().getSelectedItem();
        System.out.println(email);//TESTING
        User friend = UserManager.getInstance().getUserByEmail(email);

        String friendRiotID = friend.getRiotID();
        String friendPUUID = friend.getUserId();
        String friendRegion = friend.getRegion().toLowerCase();

        compareFriendHeadingLabel.setText(friendRiotID);

        //System.out.println(friendRiotID);//TESTING
        //System.out.println(friendRegion);//TESTING
       //System.out.println(friendPUUID);//TESTING


        Task<ResponseBody> SummonerTask = getSummonerTask(friendPUUID, friendRegion, friendRiotID);
        Task<ResponseBody> MatchIdTask = getAllMatchIdsTask(friendPUUID, friendRegion, false);

        matchIdThreadPool.submit(MatchIdTask);
        singleThreadPool.submit(SummonerTask);

        //System.out.println("USER WINRATE =" + userWinrate.getText());//TESTING

       // if (Integer.parseInt(userWinrate.getText()) < Integer.parseInt(compareFriendWinrate.getText())) {
      //      System.out.println("Player SUcks");
      //      UserWinrateBox.setStyle("-fx-background-color: Green;");
      //  }

        //if (userWinrate.getText() <) {}



        SetCompareGUI();
    }

    /**
     * Creates a task that retrieves all match IDs for the account used for comparison.
     *
     * @param puuid The user's PUUID (Player Unique ID).
     * @param region The region of the player.
     * @return A {@link Task} that fetches all match IDs.
     */
    private @NotNull Task<ResponseBody> getAllMatchIdsTask(String puuid, String region, Boolean isUser) {

        // Declare a Task which performs the API query and returns the JSON string or error if failed.
        Task<ResponseBody> AllMatchIdsTask = new Task<ResponseBody>() {

            @Override
            protected ResponseBody call() throws Exception {
                matchBucket.resetBucket();
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
                            ErrorMessage error = allMatchIdsQuery.getMessage();
                            showAlert(error.getStatus(), error.getDetail());
                        }
                        else {
                            System.out.println("matchId success");

                            // Add match id list to matchBucket
                            try {
                                List<String> matchIds = getMatchIdListFromJson(allMatchIdsQuery.getJson());
                                matchBucket.setMatchIds(matchIds);
                                //System.out.println(matchBucket.getMatchIds()); // TODO REMOVE TESTING ONLY

                                Task<Boolean> ProcessMatchesTask = getProcessMatchesTask(matchBucket.getMatchIds(), region, puuid, isUser);

                                if (isUser) {
                                    usrMatchIdThreadPool.submit(ProcessMatchesTask);
                                }
                                else {
                                    matchIdThreadPool.submit(ProcessMatchesTask);
                                }

                            } catch (JsonProcessingException e) {
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
    private @NotNull Task<Boolean> getProcessMatchesTask(List<String> matchIds, String region, String puuid, Boolean isUser) {
        // Create a Task which iterates through a list of matchIDs and retrieves individual match data.
        Task<Boolean> ProcessMatchesTask = new Task<Boolean>() {
            @Override protected Boolean call() throws Exception {
                boolean tasksFinished = false;
                ExecutorService threadPool;

                if (isUser) {
                    threadPool = usrFixedThreadPool;
                }
                else {
                    threadPool = fixedThreadPool;
                }

                for (String matchId : matchIds ) {
                    Task<ResponseBody> MatchTask = getMatchTask(matchId, region, puuid);
                    threadPool.submit(MatchTask);
                }

                // Wait for tasks to finish
                try {
                    threadPool.shutdown();
                    if (threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
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
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        boolean isTasksDone = ProcessMatchesTask.getValue();

                        if (isTasksDone) {
                            // Format values to 2 decimal places
                            String avgKda = String.format("%.2f", matchBucket.getKDAAcrossAllGames());
                            String avgCsPerMin = String.format("%.2f", matchBucket.getCSpMinAcrossAllGames());

                            // Set FXML values in GUI for average kda and cs/min
                            if (isUser) {
                                userKda.setText(avgKda);
                                userCSpMin.setText(avgCsPerMin);
                            }
                            else {
                                compareFriendKDA.setText(avgKda);
                                compareFriendCS.setText(avgCsPerMin);
                                loadingLabel.setVisible(false);
                            }
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
    private @NotNull Task<ResponseBody> getMatchTask(String matchId, String region, String puuid) throws IOException {
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
                        }
                    }
                });
            }
        });

        return MatchTask;
    }

    /**
     * Gets a Task which queries the API for the user's Friend's Summoner data and returns the ResponseBody.
     *      * If the Task is successful, injects the retrieved data into the corresponding FXML fields and
     *      * submits the retrieved summonerId to a LeagueTask; otherwise displays an error message.
     * @param friendRegion String indicating region server to query.
     * @param friendPUUID String indicating player ID to query.
     * @return ResponseBody with the JSON string and error status indicator.
     */
    private @NotNull Task<ResponseBody> getSummonerTask(String friendPUUID, String friendRegion, String friendRiotID) {
        Summoner summoner = new Summoner();

        // Declare a Task which performs the API query and returns the JSON string or error if failed.
        Task<ResponseBody> SummonerTask = new Task<ResponseBody>() {
            @Override
            protected ResponseBody call() throws Exception {
                String summonerReqUrl = URLBuilder.buildSummonerRequestUrl(friendPUUID, friendRegion);
                System.out.println("Task worked");
                return getQuery(summonerReqUrl, Constants.requestHeaders);
            }
        };

        SummonerTask.setOnSucceeded(new EventHandler(){


            @Override public void handle(Event event) {

                Platform.runLater(new Runnable() {

                    @Override public void run(){

                        ResponseBody summonerQuery = SummonerTask.getValue();

                        if (summonerQuery.isError()) {
                            System.out.println("Response returned error!"); // TODO REMOVE TESTING ONLY
                            ErrorMessage error = summonerQuery.getMessage();
                            showAlert(error.getStatus(), error.getDetail());


                        }
                        else {
                            String json = SummonerTask.getValue().getJson();

                            try {
                                getSummonerFromJson(json, summoner);

                                // Start next API call to get league data using the summonerId retrieved from this task
                                Task<League[]> LeagueTask = getLeagueTask(summoner.getSummonerId(), friendRegion);
                                singleThreadPool.submit(LeagueTask);

                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
        });

        return SummonerTask;
    }

    //shows an alert when the task returns an error
    private void showAlert(int status, String detail) {
        var alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText("There was a problem while loading the friends data");
        alert.setContentText(String.format("%d\n%s", status, detail));
        alert.initOwner(pageBox.getScene().getWindow());

        alert.show();
    }


    /**
     * Gets a Task which queries the API for the user's League data returned as an array of League objects.
     * If the Task succeeds, parse the array and update the dashboard view accordingly.
     *
     * @param summonerId The friend's summonerID.
     * @param region The friend's region
     * @return The LeagueTask and its setonSucceeded handler.
     */
    private @NotNull Task<League[]> getLeagueTask(String summonerId, String region) {
        Task<League[]> LeagueTask = new Task<League[]>() {
            @Override
            protected League[] call() throws Exception {
                String leagueReqUrl = URLBuilder.buildLeagueRequestUrl(summonerId, region);
                ResponseBody leagueQuery = getQuery(leagueReqUrl, Constants.requestHeaders);

                return getLeagueArrayFromJson(leagueQuery.getJson());
            }
        };


        LeagueTask.setOnSucceeded(new EventHandler(){
            @Override public void handle(Event event) {
                // Retrieve array returned by task and convert to LinkedList to process the elements
                League[] leagues = LeagueTask.getValue();
                List<League> leaguesList = new LinkedList<League>(Arrays.asList(leagues));


                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        //statLoadStatusText.setText("Loading league data...");

                        if (leaguesList.isEmpty()) {
                            // If no data was returned from API, go ahead and display the dashboard
                            //statLoadPane.setVisible(false);
                            //statVBox.setVisible(true);
                        }
                        else {
                            // Remove any league entries that aren't of the Ranked Solo or Ranked Flex mode
                            leaguesList.removeIf(league -> (Objects.equals(league.getQueueType(), "CHERRY")));

                            // Check how many League objects were returned from the response and parse them accordingly
                            if (leaguesList.size() == 1) {
                                League league = leaguesList.getFirst();

                                // Calculate winrate
                                league.setWinrate();

                                // Set ranked emblem image reference based on tier
                                league.setRankedEmblem();

                                // Focus the correct toggle button depending on queue type (solo or flexed)
                                if (Objects.equals(league.getQueueType(), "RANKED_SOLO_5x5")) {
                                    soloModeButton.setSelected(true);
                                    flexModeButton.setDisable(true);
                                }
                                else {
                                    flexModeButton.setSelected(true);
                                    soloModeButton.setDisable(true);
                                }
                                // Inject values retrieved from API into application
                                setLeagueValues(league);

                            }
                            else {
                                for (League league : leaguesList) {
                                    league.setWinrate();
                                    league.setRankedEmblem();
                                }

                                League leagueOne = leaguesList.get(0);
                                League leagueTwo = leaguesList.get(1);

                                // Check the queue value of the first League object in the response array
                                if (Objects.equals(leagueOne.getQueueType(), "RANKED_SOLO_5x5")) {
                                    soloModeButton.setSelected(true);
                                    friendsoloLeague = leagueOne;
                                    friendflexLeague = leagueTwo;
                                    setLeagueValues(friendsoloLeague);        // Inject retrieved values into FXML
                                }
                                else {
                                    flexModeButton.setSelected(true);
                                    friendflexLeague = leagueOne;
                                    friendsoloLeague = leagueTwo;
                                    setLeagueValues(friendflexLeague);
                                }
                                // Create Listener to detect when the selected mode changes and update the displayed values
                                modeToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                                    public void changed(ObservableValue<? extends Toggle> ov,
                                                        Toggle oldToggle, Toggle newToggle) {
                                        if (modeToggle.getSelectedToggle() == soloModeButton) {
                                            setLeagueValues(friendsoloLeague);
                                        }
                                        else {
                                            setLeagueValues(friendflexLeague);
                                        }
                                    }
                                });
                            }
                            // Cache winrate and leaguePoints data
                            userManager.setUserStatList(leaguesList);

                            //edit GUI to match stats
                            SetCompareGUI();

                            // Hide loading pane and display dashboard
                            //statLoadPane.setVisible(false);
                           // statVBox.setVisible(true);
                        }


                    }
                });
            }
        });

        return LeagueTask;
    }



    private void setLeagueValues(League league) {
        System.out.println(league);
        compareFriendLP.setText(String.valueOf(league.getLeaguePoints()));
        compareFriendWinrate.setText(String.valueOf(league.getWinrate()));



    }

    private  void SetCompareGUI(){
        //Declaring Variables
        int PlayerWinrate = Integer.parseInt(userWinrate.getText());
        int FriendWinrate = Integer.parseInt(compareFriendWinrate.getText());
        double PlayerKDA = Double.parseDouble(userKda.getText());
        double FriendKDA = Double.parseDouble(compareFriendKDA.getText());
        double PlayerCS = Double.parseDouble(userCSpMin.getText());
        double FriendCS = Double.parseDouble(compareFriendCS.getText());
        int PlayerLP = Integer.parseInt(userLeaguePoints.getText());
        int FriendLP = Integer.parseInt(compareFriendLP.getText());



        //changing the colour of background panes based on who is better
        if(PlayerWinrate < FriendWinrate){

            UserWinrateBox.setStyle("-fx-background-color: LightCoral");
            FriendWinrateBox.setStyle("-fx-background-color: Lightgreen");


        } else if (PlayerWinrate > FriendWinrate){
            UserWinrateBox.setStyle("-fx-background-color: LightGreen");
            FriendWinrateBox.setStyle("-fx-background-color: LightCoral");
        }

        if(PlayerKDA < FriendKDA){

            UserKDABox.setStyle("-fx-background-color: LightCoral");
            FriendKDABox.setStyle("-fx-background-color: Lightgreen");


        } else if (PlayerKDA > FriendKDA){
            UserKDABox.setStyle("-fx-background-color: LightGreen");
            FriendKDABox.setStyle("-fx-background-color: LightCoral");
        }

        if(PlayerCS < FriendCS){

            UserCSBox.setStyle("-fx-background-color: LightCoral");
            FriendCSBox.setStyle("-fx-background-color: Lightgreen");

        } else if (PlayerCS > FriendCS){
            UserCSBox.setStyle("-fx-background-color: LightGreen");
            FriendCSBox.setStyle("-fx-background-color: LightCoral");

        }

        if(PlayerLP < FriendLP){

            UserLPBox.setStyle("-fx-background-color: LightCoral");
            FriendLPBox.setStyle("-fx-background-color: Lightgreen");
        } else if (PlayerLP > FriendLP){
            UserLPBox.setStyle("-fx-background-color: LightGreen");
            FriendLPBox.setStyle("-fx-background-color: LightCoral");
        }
























    }



}

