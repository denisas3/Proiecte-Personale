package org.example.restaurante;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.restaurante.controller.UserController;
import org.example.restaurante.domain.validator.DetaliuValidator;
import org.example.restaurante.domain.validator.MasinaValidator;
import org.example.restaurante.domain.validator.UserValidator;
import org.example.restaurante.repository.DetaliuRepository;
import org.example.restaurante.repository.MasinaRepository;
import org.example.restaurante.repository.UserRepository;
import org.example.restaurante.service.Service;

import java.io.IOException;

public class  HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String url = "jdbc:postgresql://localhost:5432/Masini";
        String user = "postgres";
        String password = "root";

        UserRepository userRepository = new UserRepository(url, user, password, new UserValidator());
        MasinaRepository masinaRepository = new MasinaRepository(url, user, password, new MasinaValidator());
        DetaliuRepository detaliuRepository = new DetaliuRepository(url,user,password, new DetaliuValidator());

        Service service = new Service(userRepository, masinaRepository, detaliuRepository);


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);

        UserController controller = fxmlLoader.getController();
        controller.setService(service);

        stage.setTitle("Login!");
        stage.setScene(scene);
        stage.show();
    }
}
