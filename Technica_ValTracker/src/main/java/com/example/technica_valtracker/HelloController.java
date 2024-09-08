package com.example.technica_valtracker;

import com.example.technica_valtracker.api.Region;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextField gameNameTextField;

    @FXML
    private TextField tagLineTextField;

    @FXML
    private ComboBox<Region> regionComboBox = new ComboBox<>();

    @FXML
    private Button searchButton;
    @FXML
    private TextArea apiDataView;

    @FXML
    public void initialize() {
        apiDataView.setText(
                """
                Waiting...
                """
        );
    }

    @FXML
    public void onSearchButtonClick() {
        // Retrieve values from TextFields
        String gameName = gameNameTextField.getText();
        String tagLine = tagLineTextField.getText();
        String region = String.valueOf(regionComboBox.getValue()).toLowerCase();
        // Validate input

        // Pass fields into request function

        String combined = String.format("%s#%s\n%s", gameName, tagLine, region);

        apiDataView.setText(combined);
    }


}