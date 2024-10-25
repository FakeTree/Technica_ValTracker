package com.example.technica_valtracker.controller;

import atlantafx.base.theme.Styles;
import com.example.technica_valtracker.Constants;
import com.example.technica_valtracker.HelloApplication;
import com.example.technica_valtracker.UserManager;
import com.example.technica_valtracker.api.ResponseBody;
import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.controller.match_panes.MiniMatchPane;
import com.example.technica_valtracker.db.model.*;
import com.example.technica_valtracker.utils.URLBuilder;
import static com.example.technica_valtracker.api.Query.getQuery;
import static com.example.technica_valtracker.utils.Deserialiser.*;

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

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;

import org.jetbrains.annotations.NotNull;

/**
 * Controller for the Dashboard view which displays the player's in-game stats.
 */
public class DashboardController {

    /* FXML properties */

    //Menu Bar
    @FXML
    private MenuBar menuBar;
    
    //LABELS
    @FXML
    private Label accountNameText;
    @FXML
    private Label accountLevelValue;
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
    private ImageView accountIconImageView;
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

    // Toggles
    @FXML
    private ToggleButton soloToggleButton;
    @FXML
    private ToggleButton flexToggleButton;
    @FXML
    ToggleGroup modeToggle;

    // Boxes
    @FXML private VBox pageBox;
    @FXML private VBox statVBox;
    @FXML private VBox matchesVBox;

    // Loading pane elements
    @FXML
    private Pane statLoadPane;
    @FXML
    private ProgressBar statLoadProgressBar;
    @FXML
    private Label statLoadStatusText;

    private UserManager userManager = UserManager.getInstance();

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
    private void OnManageFriendsMenuClick(ActionEvent actionEvent) throws IOException {
        //switch to the Friends scene
        Stage stage = (Stage) statPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Manage-Friends.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);

    }
    @FXML
    private void OnFriendsMenuClick(ActionEvent actionEvent) throws IOException {
        //switch to the Friends scene
        Stage stage = (Stage) statPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Friends-View.fxml"));
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

    @FXML
    public void onLogOutMenuClick(ActionEvent actionEvent) throws IOException {
        // clear cached user stat data
        userManager.getUserStatList().clear();

        //switch to the login/signup screen
        Stage stage = (Stage) statPageHeaderLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setScene(scene);
    }

    /* Internal controller properties */

    // Create thread pools for executing API query tasks in background threads
    ThreadFactory threadFactory = Executors.defaultThreadFactory();
    ExecutorService singleThreadPool = Executors.newSingleThreadExecutor(threadFactory);
    ExecutorService matchIdThreadPool = Executors.newSingleThreadExecutor(threadFactory);
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(25, threadFactory);

    League soloLeague;      // Stores the user's Solo mode stats
    League flexLeague;      // Stores the user's Flex mode stats

    MatchBucket matchBucket = new MatchBucket();        // Stores user's match history data

    /**
     * Declares an initialise method that is called just before the scene UI is fully loaded
     */
    @FXML
    protected void initialize() {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                try {
                    init();
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Initialise the dashboard view with data retrieved from the API.
     * Sets up the view's UI elements (loading, header, mode toggle), gets the current User instance,
     * and executes the tasks which perform the API requests and injections into the FXML file.
     */
    protected void init() throws IOException {

        // Show loading screen with progress bar
        statLoadPane.setVisible(true);
        statLoadProgressBar.isIndeterminate();

        // Set page header text
        statPageHeaderLabel.setText("Your Dashboard");

        // Configure mode toggle group
        soloToggleButton.getStyleClass().add(Styles.LEFT_PILL);
        flexToggleButton.getStyleClass().add(Styles.RIGHT_PILL);
        modeToggle = new ToggleGroup();
        modeToggle.getToggles().addAll(soloToggleButton, flexToggleButton);

        // Get the current user's riotID, playerId (userId), and region.
        User currentUser = userManager.getCurrentUser();
        String riotId = currentUser.getRiotID();
        String puuid = currentUser.getUserId();
        String region = currentUser.getRegion().toLowerCase();

        // Execute the API query Tasks
        Task<ResponseBody> SummonerTask = getSummonerTask(puuid, region, riotId);
        Task<Champion[]> ChampionTask = getChampionTask(puuid, region);
        Task<ResponseBody> MatchIdTask = getAllMatchIdsTask(puuid, region);

        matchIdThreadPool.submit(MatchIdTask);
        singleThreadPool.submit(SummonerTask);
        singleThreadPool.submit(ChampionTask);

    }

    /**
     * Creates a task that retrieves all match IDs for the current user.
     *
     * @param puuid The user's PUUID (Player Unique ID).
     * @param region The region of the player.
     * @return A {@link Task} that fetches all match IDs.
     */
    private @NotNull Task<ResponseBody> getAllMatchIdsTask(String puuid, String region) {

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
                System.out.println("Match ID Task succeeded."); // TODO REMOVE
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
                                System.out.println(matchBucket.getMatchIds()); // TODO REMOVE TESTING ONLY

                                Task<Boolean> ProcessMatchesTask = getProcessMatchesTask(matchBucket.getMatchIds(), region, puuid);
                                matchIdThreadPool.submit(ProcessMatchesTask);
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
    private @NotNull Task<Boolean> getProcessMatchesTask(List<String> matchIds, String region, String puuid) {
        // Create a Task which iterates through a list of matchIDs and retrieves individual match data.
        Task<Boolean> ProcessMatchesTask = new Task<Boolean>() {
            @Override protected Boolean call() throws Exception {
                boolean tasksFinished = false;
                int matchCounter = 3;

                // Get individual match data from matchId list
                for (String matchId : matchIds) {
                    if (matchCounter > 0) {
                        Task<ResponseBody> MatchTask = getMatchTask(matchId, region, puuid, true);
                        fixedThreadPool.submit(MatchTask);
                        matchCounter--;
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
                            // Format values to 2 decimal places
                            String avgKda = String.format("%.2f", matchBucket.getKDAAcrossAllGames());
                            String avgCsPerMin = String.format("%.2f", matchBucket.getCSpMinAcrossAllGames());

                            // Set FXML values in dashboard for average kda and cs/min
                            kdValue.setText(avgKda);
                            csMinValue.setText(avgCsPerMin);
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
                                MiniMatchPane miniMatchPane = new MiniMatchPane();
                                // Match Result (Victory / Defeat)
                                miniMatchPane.setMatchResultText(match.getInfo().getParticipants().get(usrIdx).getWin());

                                // Mode (Ranked Solo / Ranked Flex)
                                match.getInfo().setQueueMode();
                                miniMatchPane.setMatchModeText(match.getInfo().getQueueMode());

                                // Champion name & icon
                                miniMatchPane.setMatchChampText(match.getInfo().getParticipants().get(usrIdx)
                                        .getChampionName());
                                miniMatchPane.setMatchChampImg(match.getInfo().getParticipants().get(usrIdx).getChampionId());

                                // K/D/A
                                String KDA = String.format("%d/%d/%d", match.getInfo().getParticipants().get(usrIdx).getKills(),
                                        match.getInfo().getParticipants().get(usrIdx).getDeaths(),
                                        match.getInfo().getParticipants().get(usrIdx).getAssists());
                                miniMatchPane.setMatchKdaValue(KDA);

                                // KDA (ratio)
                                String kdRatio = String.format("%.2f", match.getInfo().getParticipants().get(usrIdx)
                                        .getChallenges().getKda());
                                miniMatchPane.setMatchKdValue(kdRatio);

                                // CS/Min
                                String csPerMin = String.format("%.2f", CSpMin);
                                miniMatchPane.setMatchCSMinValue(csPerMin);

                                matchesVBox.getChildren().add(miniMatchPane);
                            }
                        }
                    }
                });
            }
        });

        return MatchTask;
    }



    /**
     * Gets a Task which queries the API for the user's Summoner data and returns the ResponseBody.
     * If the Task is successful, injects the retrieved data into the corresponding FXML fields and
     * submits the retrieved summonerId to a LeagueTask; otherwise displays an error message.
     * @param puuid The user's puuid (same as their userId).
     * @param region The user's region.
     * @param riotId The user's RiotId, with the username and tagLine formatted together.
     * @return The SummonerTask and its setonSucceeded handler.
     */
    private @NotNull Task<ResponseBody> getSummonerTask(String puuid, String region, String riotId) {
        Summoner summoner = new Summoner();

        // Declare a Task which performs the API query and returns the JSON string or error if failed.
        Task<ResponseBody> SummonerTask = new Task<ResponseBody>() {
            @Override
            protected ResponseBody call() throws Exception {
                String summonerReqUrl = URLBuilder.buildSummonerRequestUrl(puuid, region);
                return getQuery(summonerReqUrl, Constants.requestHeaders);
            }
        };

        /*
         * Event handler that runs when the SummonerTasks succeeds; deserialises the JSON output to a Summoner object
         * and sets the corresponding FXML properties.
         */
        SummonerTask.setOnSucceeded(new EventHandler() {
            @Override public void handle(Event event) {
                System.out.println("Summoner Task succeeded."); // TODO REMOVE
                // Switch from the background thread to the application thread to update UI
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        ResponseBody summonerQuery = SummonerTask.getValue();
                        statLoadStatusText.setText("Loading summoner data...");

                        if (summonerQuery.isError()) {
                            System.out.println("Response returned error!"); // TODO REMOVE TESTING ONLY
                            ErrorMessage error = summonerQuery.getMessage();
                            showAlert(error.getStatus(), error.getDetail());

                            // Hide loading pane and display dashboard
                            statLoadPane.setVisible(false);
                            statVBox.setVisible(true);
                        }
                        else {
                            String json = SummonerTask.getValue().getJson();

                            try {
                                getSummonerFromJson(json, summoner);

                                summoner.setProfileImageLink();
                                accountIconImageView.setImage(new Image(summoner.getProfileImageLink()));
                                accountNameText.setText(riotId);
                                accountLevelValue.setText(String.valueOf(summoner.getSummonerLevel()));

                                // Start next API call to get league data using the summonerId retrieved from this task
                                Task<League[]> LeagueTask = getLeagueTask(summoner.getSummonerId(), region);
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

    /**
     * Gets a Task which queries the API for the user's Champion Mastery data, returned as an array of Champion objects.
     * If the Task is successful, injects the retrieved data into the corresponding FXML fields.
     * @param puuid The user's puuid (same as their userId)
     * @param region The user's region.
     * @return The ChampionTask and its setOnSucceeded handler.
     */
    private @NotNull Task<Champion[]> getChampionTask(String puuid, String region) {
        Task<Champion[]> ChampionTask = new Task<Champion[]>() {
            @Override protected Champion[] call() throws Exception {
                String champReqUrl = URLBuilder.buildChampionRequestUrl(puuid, region);
                ResponseBody champQuery = getQuery(champReqUrl, Constants.requestHeaders);

                return getChampionArrayFromJson(champQuery.getJson());
            }
        };

        ChampionTask.setOnSucceeded(new EventHandler() {
            @Override public void handle(Event event) {
                System.out.println("Champion Task succeeded."); // TODO REMOVE
                Champion[] champions = ChampionTask.getValue();

                // Set each champion's name and image link based on its champion ID
                for (Champion champion : champions) {
                    champion.setChampionInfo();
                }

                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        statLoadStatusText.setText("Loading champion mastery...");
                        // Set icon, name, and points for each champion.
                        championOneImage.setImage(new Image(champions[0].getChampionIconLink()));
                        championOneName.setText(champions[0].getChampionName());
                        championOnePointsValue.setText(String.valueOf(champions[0].getChampionPoints()));

                        championTwoImage.setImage(new Image(champions[1].getChampionIconLink()));
                        championTwoName.setText(champions[1].getChampionName());
                        championTwoPointsValue.setText(String.valueOf(champions[1].getChampionPoints()));

                        championThreeImage.setImage(new Image(champions[2].getChampionIconLink()));
                        championThreeName.setText(champions[2].getChampionName());
                        championThreePointsValue.setText(String.valueOf(champions[2].getChampionPoints()));
                    }
                });
            }
        });
        return ChampionTask;
     }

    /**
     * Gets a Task which queries the API for the user's League data returned as an array of League objects.
     * If the Task succeeds, parse the array and update the dashboard view accordingly.
     * TODO implement runLater into setonSucceed handler method if needed
     * @param summonerId The user's summonerID.
     * @param region The user's region
     * @return The LeagueTask and its setonSucceeded handler.
     */
    private @NotNull Task<League[]> getLeagueTask(String summonerId, String region) {
        Task<League[]> LeagueTask = new Task<League[]>() {
            @Override protected League[] call() throws Exception {
                String leagueReqUrl = URLBuilder.buildLeagueRequestUrl(summonerId, region);
                ResponseBody leagueQuery = getQuery(leagueReqUrl, Constants.requestHeaders);

                return getLeagueArrayFromJson(leagueQuery.getJson());
            }
        };

        LeagueTask.setOnSucceeded(new EventHandler() {
            @Override public void handle(Event event) {
                System.out.println("League Task succeeded."); // TODO REMOVE
                // Retrieve array returned by task and convert to LinkedList to process the elements
                League[] leagues = LeagueTask.getValue();
                List<League> leaguesList = new LinkedList<League>(Arrays.asList(leagues));

                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        statLoadStatusText.setText("Loading league data...");

                        if (leaguesList.isEmpty()) {
                            // If no data was returned from API, go ahead and display the dashboard
                            statLoadPane.setVisible(false);
                            statVBox.setVisible(true);
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
                                    soloToggleButton.setSelected(true);
                                    flexToggleButton.setDisable(true);
                                }
                                else {
                                    flexToggleButton.setSelected(true);
                                    soloToggleButton.setDisable(true);
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
                                    soloToggleButton.setSelected(true);
                                    soloLeague = leagueOne;
                                    flexLeague = leagueTwo;
                                    setLeagueValues(soloLeague);        // Inject retrieved values into FXML
                                }
                                else {
                                    flexToggleButton.setSelected(true);
                                    flexLeague = leagueOne;
                                    soloLeague = leagueTwo;
                                    setLeagueValues(flexLeague);
                                }
                                // Create Listener to detect when the selected mode changes and update the displayed values
                                modeToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                                    public void changed(ObservableValue<? extends Toggle> ov,
                                                        Toggle oldToggle, Toggle newToggle) {
                                        if (modeToggle.getSelectedToggle() == soloToggleButton) {
                                            setLeagueValues(soloLeague);
                                        }
                                        else {
                                            setLeagueValues(flexLeague);
                                        }
                                    }
                                });
                            }
                            // Cache winrate and leaguePoints data
                            userManager.setUserStatList(leaguesList);

                            // Hide loading pane and display dashboard
                            statLoadPane.setVisible(false);
                            statVBox.setVisible(true);
                        }
                    }
                });
            }
        });
        return LeagueTask;
    }

    private void setLeagueValues(League league) {
        InputStream inputStream = this.getClass().getResourceAsStream(league.getRankedEmblem());
        rankBadgeImage.setImage(new Image(inputStream));
        tierText.setText(league.getTier());
        rankText.setText(league.getRank());
        leaguePointsValue.setText(String.valueOf(league.getLeaguePoints()));
        winrateValue.setText(String.valueOf(league.getWinrate()));
    }

    private void showAlert(int status, String detail) {
        var alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText("There was a problem while loading the dashboard data");
        alert.setContentText(String.format("%d\n%s", status, detail));
        alert.initOwner(pageBox.getScene().getWindow());

        alert.show();
    }
}
