package org.example.ati.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.ati.domain.Pat;
import org.example.ati.domain.Pacient;
import org.example.ati.domain.Status;
import org.example.ati.service.Service;
import org.example.ati.utils.events.EntityChangeEvent;
import org.example.ati.utils.observer.Observer;

public class    PacientController implements Observer<EntityChangeEvent> {

    /// PENTRU TABEL
    @FXML
    private TableView<Pacient>  pacientiTable;
    @FXML
    private TableColumn<Pacient, Integer> idColumn;
    @FXML
    private TableColumn<Pacient, Integer> cnpColumn;
    @FXML
    private TableColumn<Pacient, Integer> varstaColumn;
    @FXML
    private TableColumn<Pacient, Status> prematurColumn;
    @FXML
    private TableColumn<Pacient, String> diagnostic_principalColumn;
    @FXML
    private TableColumn<Pacient, Integer> gravitateColumn;

    @FXML
    private Service service;

    private ObservableList<Pacient> model = FXCollections.observableArrayList();

    /**
     * Inițializare - se apelează automat după încărcarea FXML
     */
    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        cnpColumn.setCellValueFactory(new PropertyValueFactory<>("cnp"));
        varstaColumn.setCellValueFactory(new PropertyValueFactory<>("varsta"));
        prematurColumn.setCellValueFactory(new PropertyValueFactory<>("prematur"));
        diagnostic_principalColumn.setCellValueFactory(new PropertyValueFactory<>("diagnostic_principal"));
        gravitateColumn.setCellValueFactory(new PropertyValueFactory<>("gravitate"));

        pacientiTable.setItems(model);
    }

    private void loadPacientiInAsteptare() {

        model.setAll(service.getPacientiInAsteptare());
    }

    /**
     * Setează serviciul și user-ul curent
     */
    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        loadPacientiInAsteptare();
    }

    @Override
    public void update(EntityChangeEvent event) {
        loadPacientiInAsteptare();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Eroare");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleAdaugaTIC()
    {
        Pacient selected = pacientiTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selecteaza un pacient!");
            return;
        }

        long disponibile = service.getNrPaturiDisponibileTIC();
        if (disponibile <= 0 ) {
            showError("Nu mai sunt locuri disponibile!");
            return;
        }

        String tip = "TIC";
        Status ventilatie =Status.valueOf("NU");
        Integer id_pacietn = service.findPacient(selected.getCnp()).getId();
        Integer nr_pat_disponibile = 8;

        Pat pat = new Pat(tip,ventilatie,id_pacietn,nr_pat_disponibile);

        service.savePat(pat);
    }

    public void handleAdaugaTIM()
    {
        Pacient selected = pacientiTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selecteaza un pacient!");
            return;
        }

        long disponibile = service.getNrPaturiDisponibileTIM();
        if (disponibile <= 0 ) {
            showError("Nu mai sunt locuri disponibile!");
            return;
        }

        String tip = "TIM";
        Status ventilatie =Status.valueOf("NU");
        Integer id_pacietn = service.findPacient(selected.getCnp()).getId();
        Integer nr_pat_disponibile = 8;

        Pat pat = new Pat(tip,ventilatie,id_pacietn,nr_pat_disponibile);

        service.savePat(pat);
    }

    public void handleAdaugaTIIP() {
        Pacient selected = pacientiTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selecteaza un pacient!");
            return;
        }

        long disponibile = service.getNrPaturiDisponibileTIIP();
        if (disponibile <= 0) {
            showError("Nu mai sunt locuri disponibile!");
            return;
        }

        String tip = "TIIP";
        Status ventilatie = Status.valueOf("NU");
        Integer id_pacietn = service.findPacient(selected.getCnp()).getId();
        Integer nr_pat_disponibile = 8;

        Pat pat = new Pat(tip, ventilatie, id_pacietn, nr_pat_disponibile);

        service.savePat(pat);
    }
}
