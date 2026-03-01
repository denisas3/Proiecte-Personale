package org.example.restaurante;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.restaurante.controller.AdminController;
import org.example.restaurante.controller.UserController;
import org.example.restaurante.domain.validator.CoinValidator;
import org.example.restaurante.domain.validator.TransactionValidator;
import org.example.restaurante.domain.validator.UserValidator;
import org.example.restaurante.repository.CoinRepository;
import org.example.restaurante.repository.TransactionRepository;
import org.example.restaurante.repository.UserRepository;
import org.example.restaurante.service.Service;

import java.io.IOException;

public class  HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String url = "jdbc:postgresql://localhost:5432/Piata";
        String user = "postgres";
        String password = "root";

        UserRepository userRepository = new UserRepository(url, user, password, new UserValidator());
        CoinRepository coinRepository = new CoinRepository(url, user, password, new CoinValidator());
        TransactionRepository transactionRepository = new TransactionRepository(url, user, password, new TransactionValidator());

        Service service = new Service(userRepository, coinRepository, transactionRepository);


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);

        AdminController adminController = fxmlLoader.getController();
        adminController.setService(service);

        stage.setTitle("Market Admin");
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(400);
        stage.show();

        service.getUsers().forEach( u -> {
            Stage stage1 = new Stage();
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("table.fxml"));
            Scene scene1 = null;
            try{
                scene1 = new Scene(loader.load(), 320, 240);
            }catch (IOException e ) {
                throw new RuntimeException(e);
            }

            UserController user_controller = loader.getController();
            user_controller.setService(service, u);

            stage1.setTitle("Trader")  ;
            stage1.setScene(scene1);
            stage1.setWidth(800);
            stage1.setHeight(400);
            stage1.show();
        });
    }

}
