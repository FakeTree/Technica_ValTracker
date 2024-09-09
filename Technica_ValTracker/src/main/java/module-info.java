module com.example.technica_valtracker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens com.example.technica_valtracker to javafx.fxml;
    exports com.example.technica_valtracker;

    exports com.example.technica_valtracker.api to javafx.fxml;
    exports com.example.technica_valtracker.db.model;
    opens com.example.technica_valtracker.db.model to javafx.fxml;
}