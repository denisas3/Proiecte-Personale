package org.example.restaurante.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.restaurante.domain.Hotel;
import org.example.restaurante.domain.OferteSpeciale;
import org.example.restaurante.service.Service;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observer;

import java.time.LocalDate;

public class OferteSpecialeController implements Observer<EntityChangeEvent> {

    /// PENTRU DATA PICKER
    @FXML
    private DatePicker data_inceputPicker;
    @FXML
    private DatePicker data_finalPicker;

    @FXML
    private TableView<OferteSpeciale>  oferteSpecialeTableView;
    @FXML
    private TableColumn<OferteSpeciale, LocalDate> data_inceputColumn;
    @FXML
    private TableColumn<OferteSpeciale, LocalDate> data_finalColumn;
    @FXML
    private TableColumn<OferteSpeciale, Integer> procentColumn;

    @FXML
    private Label hotelLabel;
    private Hotel currentHotel;
    private Service service;

    private ObservableList<OferteSpeciale> model = FXCollections.observableArrayList();

    /**
     * Inițializare - se apelează automat după încărcarea FXML
     */
    @FXML
    private void initialize() {
        data_inceputColumn.setCellValueFactory(new PropertyValueFactory<>("data_inceput"));
        data_finalColumn.setCellValueFactory(new PropertyValueFactory<>("data_final"));
        procentColumn.setCellValueFactory(new PropertyValueFactory<>("procent"));
        oferteSpecialeTableView.setItems(model);

    }

    /**
     * Setează serviciul și user-ul curent
     */
    public void setService(Service service, Hotel hotel) {
        this.service = service;
        this.currentHotel = hotel;
        service.addObserver(this);
        hotelLabel.setText("Oferte Speciale Pentru Hotelul: " + currentHotel.getHotel_name());
    }


    /// PENTRU DATA PICKER
    public void handleGaseste(){
        var data_i = data_inceputPicker.getValue();
        var data_f = data_finalPicker.getValue();
        var id_hotel = currentHotel.getId();

        if(data_i==null || data_f==null){
            showError("Alege data de inceput si de sfarsit!");
            return;
        }

        if(data_i==data_f){
            showError("Trebuie sa fie cel putin o noapte!");
            return;
        }

        var rezultat = service.findOfertaDupaDate(data_i,data_f,id_hotel);

        if(rezultat==null){
            showError("Nu exista oferte speciale in perioada selectata!");
            return;
        }
        else {
            model.setAll(rezultat);
        }
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
        handleGaseste();
    }

}
