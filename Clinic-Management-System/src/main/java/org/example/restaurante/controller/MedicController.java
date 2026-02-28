package org.example.restaurante.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.restaurante.domain.Consultatie;
import org.example.restaurante.domain.Medic;
import org.example.restaurante.service.Service;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observer;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class MedicController implements Observer<EntityChangeEvent> {

    @FXML
    private TableView<Consultatie>  consultatieTable;
    @FXML
    private TableColumn<Consultatie, Integer> idColumn;
    @FXML
    private TableColumn<Consultatie, Integer> cnpColumn ;
    @FXML
    private TableColumn<Consultatie, String> numeColumn ;
    @FXML
    private TableColumn<Consultatie, Date> dataColumn ;
    @FXML
    private TableColumn<Consultatie, Timer> oraColumn ;


    @FXML
    private Label medicLabel;
    private Medic currentMedic;
    private Service service;

    private ObservableList<Consultatie> model = FXCollections.observableArrayList();

    /**
     * Inițializare - se apelează automat după încărcarea FXML
     */
    @FXML
    private void initialize() {
        // Configurare coloane
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        cnpColumn.setCellValueFactory(new PropertyValueFactory<>("cnp"));
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        oraColumn.setCellValueFactory(new PropertyValueFactory<>("ora"));

        consultatieTable.setItems(model);
    }

    public void initModel(){
        List<Consultatie> consultatii = service.getConsultatie();
        var rez = consultatii.stream()
                        .filter(consultatie -> consultatie.getId_medic().equals(currentMedic.getId()))
                        .sorted(
                                Comparator
                                .comparing(Consultatie::getData)
                                .thenComparing(Consultatie::getOra)
                        )
                        .toList();
        model.setAll(rez);
        consultatieTable.refresh();
    }

    /**
     * Setează serviciul și user-ul curent
     */
    public void setService(Service service, Medic medic) {
        this.service = service;
        this.currentMedic = medic;
        // Abonare la schimbări
        service.addObserver(this);

        // Inițializare date
        initModel();
        medicLabel.setText("Medic: " + medic.getNume());
    }



    // Metode utilitare pentru mesaje

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
        initModel();
    }

}
