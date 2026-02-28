package org.example.restaurante;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.restaurante.controller.SectieController;
import org.example.restaurante.domain.validator.ConsultatieValidator;
import org.example.restaurante.domain.validator.SectieValidator;
import org.example.restaurante.domain.validator.MedicValidator;
import org.example.restaurante.repository.ConsultatieRepository;
import org.example.restaurante.repository.MedicRepository;
import org.example.restaurante.repository.SectieRepository;
import org.example.restaurante.service.Service;

import java.io.IOException;

public class  HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String url = "jdbc:postgresql://localhost:5432/ClinicaPrivata";
        String user = "postgres";
        String password = "root";

        SectieRepository userRepository = new SectieRepository(url, user, password, new SectieValidator());
        MedicRepository echipeRepository = new MedicRepository(url, user, password, new MedicValidator());
        ConsultatieRepository actiuneRepository = new ConsultatieRepository(url, user, password, new ConsultatieValidator());

        Service service = new Service(userRepository, echipeRepository,  actiuneRepository);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        SectieController controller = fxmlLoader.getController();
        controller.setService(service);

        stage.setTitle("Sectii! ");
        stage.setScene(scene);
        stage.show();


    }

}
