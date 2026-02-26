package org.example.ati;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.ati.controller.PatController;
import org.example.ati.domain.validator.PatValidator;
import org.example.ati.domain.validator.PacientValidator;
import org.example.ati.repository.PatRepository;
import org.example.ati.repository.PacientRepository;
import org.example.ati.service.Service;

import java.io.IOException;

public class  HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String url = "jdbc:postgresql://localhost:5432/ATI";
        String user = "postgres";
        String password = "root";

        PacientRepository pacietnRepository = new PacientRepository(url, user, password, new PacientValidator());
        PatRepository patRepository = new PatRepository(url, user, password, new PatValidator());

        Service service = new Service(pacietnRepository, patRepository);


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        PatController controller = fxmlLoader.getController();
        controller.setService(service);

        stage.setTitle("Paturi!");
        stage.setScene(scene);
        stage.show();
    }

}
