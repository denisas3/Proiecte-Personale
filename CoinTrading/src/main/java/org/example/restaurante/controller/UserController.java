package org.example.restaurante.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.restaurante.domain.Coins;
import org.example.restaurante.domain.User;
import org.example.restaurante.service.Service;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observer;

public class UserController implements Observer<EntityChangeEvent> {
    @FXML
    private TableView<Coins>  coinsTable;
    @FXML
    private TableColumn<Coins, Integer> idColumn;
    @FXML
    private TableColumn<Coins, String> numeColumn;
    @FXML
    private TableColumn<Coins, Float> pretColumn;


    /// PENTRU NUMELE UTILIZATORULUI SI BUGET
     @FXML private Label usernameLabel;
     @FXML private Label bugetLabel;
     @FXML private Label lastUpdateLabel;
     private User currentUser;

    private Service service;

    private ObservableList<Coins> model = FXCollections.observableArrayList();

    public void initModel(){

        model.setAll(service.getCoins());
    }

    /**
     * Inițializare - se apelează automat după încărcarea FXML
     */
    @FXML
    private void initialize() {
        // Enter key pe password field să facă login
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        pretColumn.setCellValueFactory(new PropertyValueFactory<>("pret"));

        coinsTable.setItems(model);
    }

    /**
     * Setează serviciul pentru acest controller
     */
    public void setService(Service service, User user) {
        this.service = service;
        this.currentUser = user;
        service.addObserver(this);

        usernameLabel.setText(user.getUsername());
        bugetLabel.setText(String.valueOf(user.getBuget()));

        initModel();
        coinsTable.refresh();
        updateTimestamp();
    }

    @Override
    public void update(EntityChangeEvent event) {

        initModel();

        if (currentUser != null) {
            bugetLabel.setText(String.valueOf(currentUser.getBuget())); 
        }

        updateTimestamp();
    }

    private void updateTimestamp() {
        if (lastUpdateLabel != null) {
            lastUpdateLabel.setText(java.time.LocalTime.now().withNano(0).toString());
        }
    }

    @FXML
    private void handleBuy() {
        Coins selected = coinsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Selectează o monedă!").showAndWait();
            return;
        }

        try {
            service.buyCoin(currentUser, selected);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void handleSell() {
        Coins selected = coinsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Selectează o monedă!").showAndWait();
            return;
        }

        service.sellCoin(currentUser, selected);
    }

}
