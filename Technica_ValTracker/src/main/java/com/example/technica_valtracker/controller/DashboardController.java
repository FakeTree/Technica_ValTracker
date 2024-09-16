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
    @FXML
    private VBox pageBox;
    @FXML
    private VBox statVBox;

    // Loading pane elements
    @FXML
    private Pane statLoadPane;
    @FXML
    private ProgressBar statLoadProgressBar;
    @FXML
    private Label statLoadStatusText;

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

    /* Internal controller properties */

    private UserManager userManager = UserManager.getInstance();

    // Create thread pools for executing API query tasks in background threads
    ThreadFactory threadFactory = Executors.defaultThreadFactory();
    ExecutorService singleThreadPool = Executors.newSingleThreadExecutor(threadFactory);

    League soloLeague;      // Stores the user's Solo mode stats
    League flexLeague;      // Stores the user's Flex mode stats


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

        // Get the currently logged in user's details
        User currentUser = userManager.getCurrentUser();

        String riotId = currentUser.getRiotID();
        String puuid = currentUser.getUserId();
        String region = currentUser.getRegion().toLowerCase();

        // Execute the API query Tasks
        Task<ResponseBody> SummonerTask = getSummonerTask(puuid, region, riotId);
        Task<Champion[]> ChampionTask = getChampionTask(puuid, region);

        // Task<> QueryStatusTask = get....
        // task.start() ;

        singleThreadPool.submit(SummonerTask);
        singleThreadPool.submit(ChampionTask);
        // when all tasks are done, make the thingy visible
        // Hide loading pane and display dashboard
        statLoadPane.setVisible(false);
        statVBox.setVisible(true);
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
     * TODO Task failure implementation, better error handling in general
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
                // Retrieve array returned by task and convert to LinkedList to process the elements
                League[] leagues = LeagueTask.getValue();
                List<League> leaguesList = new LinkedList<League>(Arrays.asList(leagues));

                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        statLoadStatusText.setText("Loading league data...");

                        // Remove any league entries that aren't of the Ranked Solo or Ranked Flex mode
                        leaguesList.removeIf(league -> (Objects.equals(league.getQueueType(), "CHERRY")));

                        // Check how many League objects were returned from the response and parse them accordingly
                        if (leaguesList.size() == 1) {
                            League league = leaguesList.getFirst();
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
                    }
                });
            }
        });
        return LeagueTask;
    }

    private void setLeagueValues(League league) {
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
