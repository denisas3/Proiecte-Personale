package org.example.restaurante;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.restaurante.controller.StaffController;
import org.example.restaurante.controller.TableController;
import org.example.restaurante.domain.validator.MenuItemValidator;
import org.example.restaurante.domain.validator.OrderItemValidator;
import org.example.restaurante.domain.validator.OrderValidator;
import org.example.restaurante.domain.validator.TabelValidator;
import org.example.restaurante.repository.MenuItemRepository;
import org.example.restaurante.repository.OrderItemRepository;
import org.example.restaurante.repository.OrderRepository;
import org.example.restaurante.repository.TableRepository;
import org.example.restaurante.service.Service;

import java.io.IOException;

public class  HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String url = "jdbc:postgresql://localhost:5432/Restaurante";
        String user = "postgres";
        String password = "root";

        TableRepository tableRepository = new TableRepository(url, user, password, new TabelValidator());
        MenuItemRepository menuItemRepository = new MenuItemRepository(url, user, password, new MenuItemValidator());
        OrderRepository orderRepository = new OrderRepository(url, user, password, new OrderValidator());
        OrderItemRepository orderItemRepository = new OrderItemRepository(url, user, password, new OrderItemValidator());

        Service service = new Service(tableRepository, menuItemRepository, orderRepository, orderItemRepository);


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 300);

        StaffController controller = fxmlLoader.getController();
        controller.setService(service);

        stage.setTitle("Staff!");
        stage.setScene(scene);
        stage.show();

        service.getTables().forEach(table -> {
            Stage stage2 = new Stage();
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("table.fxml"));
            Scene scene1 = null;
            try{
                scene1 = new Scene(loader.load(), 800, 700);
            }catch(IOException e){
                throw new RuntimeException(e);
            }
            TableController tableController = loader.getController();
            tableController.setService(service, table);
            stage2.setTitle("Table" + table.getId());
            stage2.setScene(scene1);
            stage2.setWidth(800);
            stage2.setHeight(700);
            stage2.show();
        });
    }

}
