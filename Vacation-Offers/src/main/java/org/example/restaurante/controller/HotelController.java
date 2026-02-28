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
import org.example.restaurante.domain.Hotel;
import org.example.restaurante.service.Service;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observer;

import java.io.IOException;

public class HotelController implements Observer<EntityChangeEvent> {
    @FXML
    private ComboBox<String> locatieComboBox;

    @FXML
    public TableView<Hotel> hotelTable;
    @FXML
    public TableColumn<Hotel, Integer> idColumn;
    @FXML
    public TableColumn<Hotel, String> hotel_nameColumn;
    @FXML
    public TableColumn<Hotel, Integer> nr_camereColumn;
    @FXML
    public TableColumn<Hotel, Double> priceColumn;
    @FXML
    public TableColumn<Hotel, String> tipColumn;

    @FXML
    private Label errorLabel;

    private Service service;

    private ObservableList<Hotel> model = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        hotel_nameColumn.setCellValueFactory(new PropertyValueFactory<>("hotel_name"));
        nr_camereColumn.setCellValueFactory(new PropertyValueFactory<>("nr_camere"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("pret_per_noapte"));
        tipColumn.setCellValueFactory(new PropertyValueFactory<>("tip"));

        hotelTable.setItems(model);
    }

    public void initModel() {
        model.setAll(service.getHoteluri());
    }

    /**
     * Setează serviciul pentru acest controller
     */
    public void setService(Service service) {
        this.service = service;
        initCombo();
        initModel();
    }

    @Override
    public void update(EntityChangeEvent event) {
        initModel();
    }

    /// PENTRU COMBO BOX
    public void initCombo() {
        var lista = service.getLocatiiDistincte();

        locatieComboBox.setItems(FXCollections.observableArrayList(lista));
    }

    public void handleAfiseaza() {
        String locatie = locatieComboBox.getValue().toString();

        var rez = service.getLocatieDupaNume(locatie);

        model.setAll(service.getHoteluriDupaLocatie(rez.getId()));
    }

    @FXML
    public void handleOferta() {
        Hotel selected = hotelTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selecteaza un zbor!");
            return;
        }
        openTableWindows(selected);
    }

    /**
     * Deschide ferestrele pentru mese
     */
    private void openTableWindows(Hotel hotel) {
        try {
            Stage tableStage = new Stage();
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("table.fxml")
            );

            Scene scene = new Scene(loader.load(), 800, 400);

            OferteSpecialeController ofertaController = loader.getController();
            ofertaController.setService(service, hotel);

            tableStage.setTitle("Oferte Speciale!");
            tableStage.setScene(scene);
            tableStage.show();

        } catch (IOException e) {
            showError("Eroare la deschiderea echipelor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Afișează mesaj de eroare
     */
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    /**
     * Ascunde mesajul de eroare
     */
    private void hideError() {
        errorLabel.setVisible(false);
    }

}



