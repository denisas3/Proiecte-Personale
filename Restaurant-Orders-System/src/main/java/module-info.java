//module org.example.restaurante {
//    requires javafx.controls;
//    requires javafx.fxml;
//    requires java.sql;
//    //requires org.example.restaurante;
//    requires javafx.base;
//
//
//    opens org.example.restaurante to javafx.fxml;
//    exports org.example.restaurante;
//}

module org.example.restaurante {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    //requires org.example.restaurante;
    // requires org.postgresql.jdbc;

    opens org.example.restaurante to javafx.fxml;
    opens org.example.restaurante.controller to javafx.fxml;
    opens org.example.restaurante.domain to javafx.base;

    exports org.example.restaurante;
}
