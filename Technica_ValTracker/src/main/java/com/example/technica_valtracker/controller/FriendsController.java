package com.example.technica_valtracker.controller;

import atlantafx.base.theme.Styles;
import com.example.technica_valtracker.Constants;
import com.example.technica_valtracker.HelloApplication;
import com.example.technica_valtracker.UserManager;
import com.example.technica_valtracker.api.ResponseBody;
import com.example.technica_valtracker.api.error.ErrorMessage;
import com.example.technica_valtracker.db.model.League;
import com.example.technica_valtracker.db.model.Summoner;
import com.example.technica_valtracker.db.model.User;

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static com.example.technica_valtracker.api.Query.getQuery;
import static com.example.technica_valtracker.utils.Deserialiser.getLeagueArrayFromJson;
import static com.example.technica_valtracker.utils.Deserialiser.getSummonerFromJson;


public class FriendsController {

    //FXML ELEMENTS


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
    private Label comparePlayerWinrate;
    @FXML
    private Label comparePlayerKDA;
    @FXML
    private Label comparePlayerCS;
    @FXML
    private Label comparePlayerLP;
    @FXML
    private Label compareFriendHeadingLabel;
    @FXML
    private Label compareFriendWinrate;
    @FXML
    private Label compareFriendKDA;
    @FXML
    private Label CompareFriendCS;
    @FXML
    private Label compareFriendLP;
    @FXML
    private Label friendPageHeaderLabel;

    @FXML
    private Label userWinrate;
    @FXML
    private Label userLeaguePoints;

    private UserManager userManager = UserManager.getInstance();


    // Create thread pools for executing API query tasks in background threads
    ThreadFactory threadFactory = Executors.defaultThreadFactory();
    ExecutorService singleThreadPool = Executors.newSingleThreadExecutor(threadFactory);

    League friendsoloLeague;      // Stores the user's Solo mode stats
    League friendflexLeague;      // Stores the user's Flex mode stats

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


        soloModeButton.getStyleClass().add(Styles.LEFT_PILL);
        flexModeButton.getStyleClass().add(Styles.RIGHT_PILL);
        modeToggle = new ToggleGroup();
        modeToggle.getToggles().addAll(soloModeButton, flexModeButton);

        UserManager.UserStats rankedStats = userManager.getUserStatList().getFirst();

        User User = UserManager.getInstance().getCurrentUser();


        userWinrate.setText(String.valueOf(rankedStats.getWinrate()));
        userLeaguePoints.setText(String.valueOf(rankedStats.getLeaguePoints()));




        FriendComboBox.getItems().setAll(User.getFriends());


    }

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

        String email = FriendComboBox.getSelectionModel().getSelectedItem();
        System.out.println(email);//TESTING
        User friend = UserManager.getInstance().getUserByEmail(email);

        String friendRiotID = friend.getRiotID();
        String friendPUUID = friend.getUserId();
        String friendRegion = friend.getRegion().toLowerCase();

        compareFriendHeadingLabel.setText(friendRiotID);

        System.out.println(friendRiotID);//TESTING
        System.out.println(friendRegion);//TESTING
        System.out.println(friendPUUID);//TESTING


        Task<ResponseBody> SummonerTask = getSummonerTask(friendPUUID, friendRegion, friendRiotID);
        singleThreadPool.submit(SummonerTask);


    }

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


    private void showAlert(int status, String detail) {
        var alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText("There was a problem while loading the friends data");
        alert.setContentText(String.format("%d\n%s", status, detail));
        alert.initOwner(pageBox.getScene().getWindow());

        alert.show();
    }



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
}

