package org.example.restaurante;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.restaurante.controller.ClientController;
import org.example.restaurante.domain.validator.BiletValidator;
import org.example.restaurante.domain.validator.ZborValidator;
import org.example.restaurante.domain.validator.ClientValidator;
import org.example.restaurante.repository.BiletRepository;
import org.example.restaurante.repository.ZborRepository;
import org.example.restaurante.repository.ClientRepository;
import org.example.restaurante.service.Service;

import java.io.IOException;

public class  HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String url = "jdbc:postgresql://localhost:5432/Zboruri";
        String user = "postgres";
        String password = "root";

        ClientRepository clientRepository = new ClientRepository(url, user, password, new ClientValidator());
        ZborRepository zborRepository = new ZborRepository(url, user, password, new ZborValidator());
        BiletRepository actiuneRepository = new BiletRepository(url, user, password, new BiletValidator());

        Service service = new Service(clientRepository, zborRepository,  actiuneRepository);


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);

        ClientController controller = fxmlLoader.getController();
        controller.setService(service);

        stage.setTitle("Login!");
        stage.setScene(scene);
        stage.show();
    }

}
