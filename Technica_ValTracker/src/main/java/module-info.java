module com.example.technica_valtracker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;

    opens com.example.technica_valtracker to javafx.fxml;
    exports com.example.technica_valtracker;
}