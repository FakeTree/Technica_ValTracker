package com.example.technica_valtracker.controller.match_panes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

import static com.example.technica_valtracker.api.constants.Champions.getChampionById;

/**
 * Represents a detailed match pane displaying match history information,
 * including champion information, match result, and various match statistics.
 */
public class LargeMatchPane extends Pane {

    @FXML private Label HistoryMatch_Win;
    @FXML private Label HistoryMatch1_Mode;
    @FXML private ImageView HistoryMatch1_ChampImage;
    @FXML private Label HistoryMatch1_ChampName;
    @FXML private Label HistoryMatch1_KDA;
    @FXML private Label HistoryMatch1_KD;
    @FXML private Label HistoryMatch1_CSpermin;
    @FXML private Label HistoryMatch1_Date;
    @FXML private Label HistoryMatch1_Goldpermin;
    @FXML private Label HistoryMatch1_BuildingDamage;
    @FXML private Label HistoryMatch1_VisionScore;

    /**
     * Initializes a new instance of the LargeMatchPane class.
     * Loads the FXML file and sets up this pane as the root and controller.
     */
    public LargeMatchPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LargeMatchPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the text of the match result label.
     * @return The match result as a string.
     */
    public String getMatchResultText() { return HistoryMatch_Win.textProperty().get(); }

    /**
     * Sets the match result text based on the boolean result.
     * @param matchResult True for "Victory", false for "Defeat".
     */
    public void setMatchResultText(Boolean matchResult) {
        String result = matchResult ? "Victory" : "Defeat";
        HistoryMatch_Win.textProperty().set(result);
    }

    /**
     * Retrieves the match mode text.
     * @return The match mode as a string.
     */
    public String getMatchModeText() { return HistoryMatch1_Mode.textProperty().get(); }

    /**
     * Sets the match mode text.
     * @param matchMode The mode of the match as a string.
     */
    public void setMatchModeText(String matchMode) { HistoryMatch1_Mode.textProperty().set(matchMode); }

    /**
     * Retrieves the champion name text.
     * @return The champion name as a string.
     */
    public String getMatchChampText() { return HistoryMatch1_ChampName.textProperty().get(); }

    /**
     * Sets the champion name text.
     * @param matchChamp The name of the champion as a string.
     */
    public void setMatchChampText(String matchChamp) { HistoryMatch1_ChampName.textProperty().set(matchChamp); }

    /**
     * Retrieves the KDA (kills/deaths/assists) value text.
     * @return The KDA value as a string.
     */
    public String getMatchKdaValue() { return HistoryMatch1_KDA.textProperty().get(); }

    /**
     * Sets the KDA value text.
     * @param matchKda The KDA value as a string.
     */
    public void setMatchKdaValue(String matchKda) { HistoryMatch1_KDA.textProperty().set(matchKda); }

    /**
     * Retrieves the KD (kill/death ratio) value text.
     * @return The KD ratio as a string.
     */
    public String getMatchKdValue() { return HistoryMatch1_KD.textProperty().get(); }

    /**
     * Sets the KD value text.
     * @param matchKd The KD ratio as a string.
     */
    public void setMatchKdValue(String matchKd) { HistoryMatch1_KD.textProperty().set(matchKd); }

    /**
     * Retrieves the CS per minute value text.
     * @return The CS per minute as a string.
     */
    public String getMatchCsMinValue() { return HistoryMatch1_CSpermin.textProperty().get(); }

    /**
     * Sets the CS per minute value text.
     * @param csMin The CS per minute as a string.
     */
    public void setMatchCSMinValue(String csMin) { HistoryMatch1_CSpermin.textProperty().set(csMin); }

    /**
     * Retrieves the match date text.
     * @return The match date as a string.
     */
    public String geHistoryMatch1_Date() { return HistoryMatch1_Date.textProperty().get(); }

    /**
     * Sets the match date text.
     * @param date The date of the match as a string.
     */
    public void setHistoryMatch1_Date(String date) { HistoryMatch1_Date.textProperty().set(date); }

    /**
     * Retrieves the gold per minute value text.
     * @return The gold per minute as a string.
     */
    public String getHistoryMatch1_Goldpermin() { return HistoryMatch1_Goldpermin.textProperty().get(); }

    /**
     * Sets the gold per minute value text.
     * @param goldPerMin The gold per minute as a string.
     */
    public void setHistoryMatch1_Goldpermin(String goldPerMin) { HistoryMatch1_Goldpermin.textProperty().set(goldPerMin); }

    /**
     * Retrieves the building damage value text.
     * @return The building damage as a string.
     */
    public String getHistoryMatch1_BuildingDamage() { return HistoryMatch1_BuildingDamage.textProperty().get(); }

    /**
     * Sets the building damage value text.
     * @param buildingDamage The building damage as a string.
     */
    public void setHistoryMatch1_BuildingDamage(String buildingDamage) { HistoryMatch1_BuildingDamage.textProperty().set(buildingDamage); }

    /**
     * Retrieves the vision score value text.
     * @return The vision score as a string.
     */
    public String getHistoryMatch1_VisionScore() { return HistoryMatch1_VisionScore.textProperty().get(); }

    /**
     * Sets the vision score value text.
     * @param visionScore The vision score as a string.
     */
    public void setHistoryMatch1_VisionScore(String visionScore) { HistoryMatch1_VisionScore.textProperty().set(visionScore); }

    /**
     * Sets the champion image based on the champion ID.
     * Fetches the champion image from the provided champion ID.
     *
     * @param championId The champion's ID.
     */
    public void setMatchChampImg(int championId) {
        String[] championNames = getChampionById(championId);
        String imgBaseUrl = "https://ddragon.leagueoflegends.com/cdn/14.18.1/img/champion/";
        if (championNames.length > 1) {
            HistoryMatch1_ChampImage.setImage(new Image(imgBaseUrl + championNames[1] + ".png"));
        } else {
            HistoryMatch1_ChampImage.setImage(new Image(imgBaseUrl + championNames[0] + ".png"));
        }
    }
}
