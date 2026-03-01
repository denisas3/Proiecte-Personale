package org.example.restaurante;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.restaurante.controller.OrasController;
import org.example.restaurante.domain.validator.StatieValidator;
import org.example.restaurante.domain.validator.OrasValidator;
import org.example.restaurante.domain.validator.TrenValidator;
import org.example.restaurante.repository.TrenRepository;
import org.example.restaurante.repository.StatieRepository;
import org.example.restaurante.repository.OrasRepository;
import org.example.restaurante.service.Service;

import java.io.IOException;

public class  HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String url = "jdbc:postgresql://localhost:5432/Trenuri";
        String user = "postgres";
        String password = "root";

        OrasRepository orasRepository = new OrasRepository(url, user, password, new OrasValidator());
        StatieRepository statieRepository = new StatieRepository(url, user, password, new StatieValidator());
        TrenRepository trenRepository = new TrenRepository(url, user, password, new TrenValidator());

        Service service = new Service(orasRepository, statieRepository,  trenRepository);


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);

        OrasController controller = fxmlLoader.getController();
        controller.setService(service);

        stage.setTitle("Open window!");
        stage.setScene(scene);
        stage.show();
    }

}
