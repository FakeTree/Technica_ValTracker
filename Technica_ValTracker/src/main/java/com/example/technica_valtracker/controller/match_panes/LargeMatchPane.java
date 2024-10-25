package com.example.technica_valtracker.controller.match_panes;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import static com.example.technica_valtracker.api.constants.Champions.getChampionById;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import javafx.scene.image.ImageView;

import java.io.IOException;

import static com.example.technica_valtracker.api.constants.Champions.getChampionById;

public class LargeMatchPane extends Pane{
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

    public String getMatchResultText() { return HistoryMatch_Win.textProperty().get(); }
    public void setMatchResultText(Boolean matchResult) {
        String result;
        if (matchResult) { result = "Victory"; } else { result = "Defeat"; };
        HistoryMatch_Win.textProperty().set(result);
    }

    public String getMatchModeText() { return HistoryMatch1_Mode.textProperty().get(); }
    public void setMatchModeText(String matchMode) { HistoryMatch1_Mode.textProperty().set(matchMode); }

    public String getMatchChampText() { return HistoryMatch1_ChampName.textProperty().get(); }
    public void setMatchChampText(String matchChamp) { HistoryMatch1_ChampName.textProperty().set(matchChamp); }

    public String getMatchKdaValue() { return HistoryMatch1_KDA.textProperty().get(); }
    public void setMatchKdaValue(String matchKda) { HistoryMatch1_KDA.textProperty().set(matchKda); }

    public String getMatchKdValue() { return HistoryMatch1_KD.textProperty().get(); }
    public void setMatchKdValue(String matchKd) { HistoryMatch1_KD.textProperty().set(matchKd); }

    public String getMatchCsMinValue() { return HistoryMatch1_CSpermin.textProperty().get(); }
    public void setMatchCSMinValue(String csMin) { HistoryMatch1_CSpermin.textProperty().set(csMin); }

    public String geHistoryMatch1_Date() { return HistoryMatch1_Date.textProperty().get(); }
    public void setHistoryMatch1_Date(String csMin) { HistoryMatch1_Date.textProperty().set(csMin); }

    public String getHistoryMatch1_Goldpermin() { return HistoryMatch1_Goldpermin.textProperty().get(); }
    public void setHistoryMatch1_Goldpermin(String csMin) { HistoryMatch1_Goldpermin.textProperty().set(csMin); }

    public String getHistoryMatch1_BuildingDamage() { return HistoryMatch1_BuildingDamage.textProperty().get(); }
    public void setHistoryMatch1_BuildingDamage(String csMin) { HistoryMatch1_BuildingDamage.textProperty().set(csMin); }

    public String getHistoryMatch1_VisionScore() { return HistoryMatch1_VisionScore.textProperty().get(); }
    public void setHistoryMatch1_VisionScore(String csMin) { HistoryMatch1_VisionScore.textProperty().set(csMin); }

    public void setMatchChampImg(int championId) {
        String[] championNames = getChampionById(championId);
        String imgBaseUrl = "https://ddragon.leagueoflegends.com/cdn/14.18.1/img/champion/";
        if (championNames.length > 1) {
            HistoryMatch1_ChampImage.setImage(new Image(imgBaseUrl + championNames[1] + ".png"));
        }
        else {
            HistoryMatch1_ChampImage.setImage(new Image(imgBaseUrl + championNames[0] + ".png"));
        }
    }
}
