module org.example.ati {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires java.desktop;
    requires javafx.graphics;
    opens org.example.ati to javafx.fxml , javafx.base;
    opens org.example.ati.controller to javafx.fxml , javafx.base;
    opens org.example.ati.domain to javafx.base;
    exports org.example.ati;
}