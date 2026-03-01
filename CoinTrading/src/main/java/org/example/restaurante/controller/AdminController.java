package org.example.restaurante.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.restaurante.domain.Coins;
import org.example.restaurante.domain.Transaction;
import org.example.restaurante.domain.User;
import org.example.restaurante.service.Service;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observer;

public class AdminController implements Observer<EntityChangeEvent> {

    @FXML private TableView<Transaction> transactionsTable;
    @FXML private TableColumn<Transaction, String> traderColumn;
    @FXML private TableColumn<Transaction, String> typeColumn;
    @FXML private TableColumn<Transaction, String> coinColumn;
    @FXML private TableColumn<Transaction, Double> priceColumn;
    @FXML private TableColumn<Transaction, String> timeColumn;

    private Service service;
    private final ObservableList<Transaction> model = FXCollections.observableArrayList();

    /**
     * Inițializare - se apelează automat după încărcarea FXML
     */
    @FXML
    private void initialize() {
        traderColumn.setCellValueFactory(cell -> {
            Transaction t = cell.getValue();
            String name = service.findUserById(t.getUserId())
                    .map(User::getUsername)
                    .orElse("userId=" + t.getUserId());
            return new SimpleStringProperty(name);
        });

        coinColumn.setCellValueFactory(cell -> {
            Transaction t = cell.getValue();
            String coinName = service.findCoinById(t.getCoinId())
                    .map(Coins::getNume)
                    .orElse("coinId=" + t.getCoinId());
            return new SimpleStringProperty(coinName);
        });

        typeColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getType().name()));

        priceColumn.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getPrice()));

        timeColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getTimestamp().toString()));

        transactionsTable.setItems(model);
    }

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        initModel();
    }

    private void initModel() {

        model.setAll(service.getTransactions());
    }

    @Override
    public void update(EntityChangeEvent event) {

        initModel();
    }

}
