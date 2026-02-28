package org.example.restaurante.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.restaurante.domain.Order;
import org.example.restaurante.domain.PlacedOrderRow;
import org.example.restaurante.service.Service;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observer;

public class StaffController implements Observer<EntityChangeEvent> {

    @FXML
    private TableView<PlacedOrderRow> placedOrdersTable;
    @FXML private TableColumn<PlacedOrderRow, Integer> tableIdColumn;
    @FXML private TableColumn<PlacedOrderRow, java.time.LocalDateTime> dateColumn;
    @FXML private TableColumn<PlacedOrderRow, String> itemsColumn;

    private final ObservableList<PlacedOrderRow> model = FXCollections.observableArrayList();
    private Service service;

    @FXML
    private void initialize() {
        tableIdColumn.setCellValueFactory(new PropertyValueFactory<>("tableId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        itemsColumn.setCellValueFactory(new PropertyValueFactory<>("items"));
        placedOrdersTable.setItems(model);
    }

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        initModel();
    }


    private void initModel() {
        model.setAll(service.getPlacedOrdersView());
    }

    @Override
    public void update(EntityChangeEvent event) {
        // reacționează doar la comenzi noi
        if (event.getData() instanceof Order) {
            Platform.runLater(this::initModel);
        }
    }
}
