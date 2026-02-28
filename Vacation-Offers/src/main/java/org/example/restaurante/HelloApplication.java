package org.example.restaurante;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.restaurante.controller.ClientController;
import org.example.restaurante.controller.HotelController;
import org.example.restaurante.domain.validator.*;
import org.example.restaurante.repository.*;
import org.example.restaurante.service.Service;

import java.io.IOException;

public class  HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String url = "jdbc:postgresql://localhost:5432/Oferte_Vacanta";
        String user = "postgres";
        String password = "root";

        LocatieRepository userRepository = new LocatieRepository(url, user, password, new LocatieValidator());
        HotelRepository echipeRepository = new HotelRepository(url, user, password, new HotelValidator());
        OferteSpecialeRepository actiuneRepository = new OferteSpecialeRepository(url, user, password, new OferteSpecialeValidator());
        ClientRepository clientRepository = new ClientRepository(url, user, password, new ClientValidator());
        RezervareRepository rezervareRepository = new RezervareRepository(url,user,password, new RezervareValidator());

        Service service = new Service(userRepository, echipeRepository,  actiuneRepository,clientRepository,rezervareRepository);


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);

        HotelController controller = fxmlLoader.getController();
        controller.setService(service);

        stage.setTitle("Hotel!");
        stage.setScene(scene);
        stage.show();

        service.getClienti().forEach(client -> {
            Stage stage2 = new Stage();
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("actiune.fxml"));
            Scene scene1 = null;
            try{
                scene1 = new Scene(loader.load(), 320, 240);
            }catch(IOException e){
                throw new RuntimeException(e);
            }
            ClientController clientController = loader.getController();
            clientController.setService(service,client);
            stage2.setTitle("Client:" + client.getNume());
            stage2.setScene(scene1);
            stage2.setWidth(800);
            stage2.setHeight(400);
            stage2.show();

        });
    }

}
