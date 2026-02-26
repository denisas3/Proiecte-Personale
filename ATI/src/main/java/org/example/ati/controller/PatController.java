package org.example.ati.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.ati.HelloApplication;
import org.example.ati.service.Service;
import org.example.ati.utils.events.EntityChangeEvent;
import org.example.ati.utils.observer.Observer;

import java.io.IOException;


public class PatController implements Observer<EntityChangeEvent> {
    /// PENTRU TABEL
    @FXML
    private TableView<PatDisponibilRow> patTable;
    @FXML
    private TableColumn<PatDisponibilRow, String> TipPat;
    @FXML
    private TableColumn<PatDisponibilRow, Integer> NrPatDisponibile;

    /// PENTRU LABEL
    @FXML
    private Label paturi_ocupate;

    @FXML
    private Label errorLabel;

    private Service service;

    private ObservableList<PatDisponibilRow> model= FXCollections.observableArrayList();

    public void initialize() {
        TipPat.setCellValueFactory(new PropertyValueFactory<>("tip"));
        NrPatDisponibile.setCellValueFactory(new PropertyValueFactory<>("disponibile"));

        patTable.setItems(model);
    }

    public void initModel() {
        if (service == null) return;

        this.paturi_ocupate.setText("Paturi ocupate: " + service.getNrPaturiOcupate());

        var map = service.getNrPaturiDisponibile();

        model.setAll(
                new PatDisponibilRow("TIC", map.getOrDefault("TIC", 0)),
                new PatDisponibilRow("TIM", map.getOrDefault("TIM", 0)),
                new PatDisponibilRow("TIIP", map.getOrDefault("TIIP", 0))
        );

        patTable.refresh();
    }

    public void setService(Service service) {
        this.service = service;
        initModel();

        service.addObserver(this);
    }


    @Override
    public void update(EntityChangeEvent event) {

        initModel();
    }

    @FXML
    /**
     * Deschide ferestrele pentru mese
     */
    private void openTableWindows() {
        try {
            Stage tableStage = new Stage();
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("pacient.fxml")
            );

            Scene scene = new Scene(loader.load(), 800, 400);

            PacientController pacientController = loader.getController();
            pacientController.setService(service);

            tableStage.setTitle("Pacienti in asteptare");
            tableStage.setScene(scene);
            tableStage.show();

        } catch (IOException e) {
            showError("Eroare la deschiderea pacientilor: " + e.getMessage());
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

    public void handleAfiseazaPacienti()
    {
        openTableWindows();
    }

}
