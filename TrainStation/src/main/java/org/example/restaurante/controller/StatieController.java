package org.example.restaurante.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.restaurante.HelloApplication;
import org.example.restaurante.domain.Statie;
import org.example.restaurante.domain.Oras;
import org.example.restaurante.service.Service;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observer;

import java.io.IOException;

public class StatieController implements Observer<EntityChangeEvent> {

    /// PENTRU TABEL
    @FXML
    private TableView<Statie>  statieTable;
    @FXML
    private TableColumn<Statie, Integer> idColumn;
    @FXML
    private TableColumn<Statie, Integer> id_oras_plecare;
    @FXML
    private TableColumn<Statie, Integer> id_oras_sosire;
    @FXML
    private TableColumn<Statie, Integer> id_tren;

    /// PENTRU COMBO BOX
    @FXML
    private ComboBox<String> oras_plecare;
    @FXML
    private ComboBox<String> oras_sosire;

    /// PENTRU CHECK BOX
    @FXML private CheckBox rutaDirecta;


    /// /// PENTRU LIST VIEW BOX
    @FXML private ListView<String> routesList;

    @FXML
    private Label userLabel;
    private Service service;

    private ObservableList<Statie> model = FXCollections.observableArrayList();

    private void initModel() {

        model.setAll(service.getStatie());
    }

    /**
     * Setează serviciul și user-ul curent
     */
    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);

        initModel();
        initFilters();
    }

    /**
     * Inițializare - se apelează automat după încărcarea FXML
     */
    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        id_tren.setCellValueFactory(new PropertyValueFactory<>("id_tren"));
        id_oras_plecare.setCellValueFactory(new PropertyValueFactory<>("id_oras_plecare"));
        id_oras_sosire.setCellValueFactory(new PropertyValueFactory<>("id_oras_sosier"));

        statieTable.setItems(model);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Eroare");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Observer pattern - actualizare automată
     */
    @Override
    public void update(EntityChangeEvent event) {
        model.setAll(service.getStatie());
    }


    /// PENTRU COMBO BOX
    private void initFilters() {
        var orase_plecare = service.fillComboBoxOras();
        var orase_sosire = service.fillComboBoxOras();

        oras_plecare.setItems(FXCollections.observableArrayList(orase_plecare));
        oras_sosire.setItems(FXCollections.observableArrayList(orase_sosire));
    }


    @FXML
    private void handleSearch() {
        String plecare = oras_plecare.getValue();
        String sosire = oras_sosire.getValue();
        if (plecare == null || sosire == null) { showError("Alege oras plecare si sosire!"); return; }

        boolean directOnly = rutaDirecta.isSelected();

        int startId = service.getOrasIdByName(plecare);
        int endId = service.getOrasIdByName(sosire);

        var routes = service.findRoutes(startId, endId, directOnly);

        var display = routes.stream()
                .map(route -> service.formatRoute(route))
                .toList();

        routesList.setItems(FXCollections.observableArrayList(display));
    }
}
