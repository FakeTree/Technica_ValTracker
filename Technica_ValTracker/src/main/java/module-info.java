module com.example.technica_valtracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires okhttp3;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.json;

    opens com.example.technica_valtracker to javafx.fxml;
    exports com.example.technica_valtracker;

    exports com.example.technica_valtracker.api to javafx.fxml;
    exports com.example.technica_valtracker.db.model;
    exports com.example.technica_valtracker.api.error;

    opens com.example.technica_valtracker.db.model to javafx.fxml;
    exports com.example.technica_valtracker.utils;
    opens com.example.technica_valtracker.utils to javafx.fxml;
}