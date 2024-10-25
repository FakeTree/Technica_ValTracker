package com.example.technica_valtracker.controller.match_panes;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import static com.example.technica_valtracker.api.constants.Champions.getChampionById;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import javafx.scene.image.ImageView;

/**
 * Custom component that provides the layout for a Match History item in the Dashboard view.
 */
public class MiniMatchPane extends Pane {
    @FXML private Label matchResultText;
    @FXML private Label matchModeText;
    @FXML private Label matchChampText;
    @FXML private Label matchKdaValue;
    @FXML private Label matchKDValue;
    @FXML private Label matchCSMinValue;
    @FXML private ImageView matchChampImg;

    public MiniMatchPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MiniMatchPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMatchResultText() { return matchResultText.textProperty().get(); }
    public void setMatchResultText(Boolean matchResult) {
        String result;
        if (matchResult) { result = "Victory"; } else { result = "Defeat"; };
        matchResultText.textProperty().set(result);
    }

    public String getMatchModeText() { return matchModeText.textProperty().get(); }
    public void setMatchModeText(String matchMode) { matchModeText.textProperty().set(matchMode); }

    public String getMatchChampText() { return matchChampText.textProperty().get(); }
    public void setMatchChampText(String matchChamp) { matchChampText.textProperty().set(matchChamp); }

    public String getMatchKdaValue() { return matchKdaValue.textProperty().get(); }
    public void setMatchKdaValue(String matchKda) { matchKdaValue.textProperty().set(matchKda); }

    public String getMatchKdValue() { return matchKDValue.textProperty().get(); }
    public void setMatchKdValue(String matchKd) { matchKDValue.textProperty().set(matchKd); }

    public String getMatchCsMinValue() { return matchCSMinValue.textProperty().get(); }
    public void setMatchCSMinValue(String csMin) { matchCSMinValue.textProperty().set(csMin); }

    public void setMatchChampImg(int championId) {
        String[] championNames = getChampionById(championId);
        String imgBaseUrl = "https://ddragon.leagueoflegends.com/cdn/14.18.1/img/champion/";
        if (championNames.length > 1) {
            matchChampImg.setImage(new Image(imgBaseUrl + championNames[1] + ".png"));
        }
        else {
            matchChampImg.setImage(new Image(imgBaseUrl + championNames[0] + ".png"));
        }
    }
}
