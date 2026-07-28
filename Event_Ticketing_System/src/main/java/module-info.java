module org.example.lab03 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires java.naming;
    requires LabModel;

    opens org.example.lab03.gui to javafx.fxml;
    opens org.example.lab03 to javafx.base;

    exports org.example.lab03;
    exports org.example.lab03.gui;
    exports org.example.lab03;
    exports org.example.lab03;
    exports org.example.lab03.services;
}