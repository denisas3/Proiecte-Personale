package org.example.lab03;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.lab03.gui.LoginController;
import org.example.lab03.repository.EmployeeDbRepository;
import org.example.lab03.repository.ShowDbRepository;
import org.example.lab03.repository.TicketDbRepository;
import org.example.lab03.services.EmployeeService;
import org.example.lab03.services.ShowService;
import org.example.lab03.services.TicketService;
import org.example.lab03.utils.JdbcUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Properties props = new Properties();
        try (FileReader reader = new FileReader("bd.config")) {
            props.load(reader);
//            System.out.println("JDBC URL = " + props.getProperty("jdbc.url"));
        }
        JdbcUtils dbUtils = new JdbcUtils(props);
        var employeeRepo = new EmployeeDbRepository(dbUtils);
        var showRepo = new ShowDbRepository(dbUtils);
        var ticketRepo = new TicketDbRepository(dbUtils);

        EmployeeService employeeService = new EmployeeService(employeeRepo);
        ShowService showService = new ShowService(showRepo);
        TicketService ticketService = new TicketService(ticketRepo, showRepo);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lab03/login-view.fxml"));

        Scene scene = new Scene(loader.load(), 900, 600);

        LoginController loginController = loader.getController();
        loginController.setServices(employeeService, showService, ticketService);

        stage.setTitle("Aplicatie bilete");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> dbUtils.closeConnection());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
