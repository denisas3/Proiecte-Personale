module curs3.io {
    requires java.sql;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.base;
    requires static lombok;
    requires org.postgresql.jdbc;
//    requires curs3.io;

    opens curs3.io to javafx.fxml;
    opens curs3.io.controller to javafx.fxml;

    exports curs3.io;
}
