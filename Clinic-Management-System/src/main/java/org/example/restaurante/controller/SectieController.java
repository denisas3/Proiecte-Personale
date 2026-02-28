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
import org.example.restaurante.domain.Medic;
import org.example.restaurante.domain.Sectie;
import org.example.restaurante.service.Service;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observer;

import java.io.IOException;

public class SectieController implements Observer<EntityChangeEvent> {
   @FXML
   private TableView<Sectie> sectieTable;
   @FXML
   private TableColumn<Sectie, Integer> idColumn;
   @FXML
   private TableColumn<Sectie, String> numeColumn;
   @FXML
   private TableColumn<Sectie, Integer> pretColumn;
   @FXML
   private TableColumn<Sectie, Integer> durataColumn;

    @FXML
    private Label errorLabel;

    private Service service;

    public ObservableList<Sectie> model= FXCollections.observableArrayList();

    public void initialize()
    {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        pretColumn.setCellValueFactory(new PropertyValueFactory<>("pret"));
        durataColumn.setCellValueFactory(new PropertyValueFactory<>("durata"));

        sectieTable.setItems(model);
    }

    public void initModel()
    {
        model.setAll(service.getSectii());
    }

    /**
     * Setează serviciul pentru acest controller
     */
    public void setService(Service service) {

        this.service = service;
        service.addObserver(this);
        initModel();

        service.getMedici().forEach(
                this::openTableWindows
        );
        /// ECHIVALENT CU:
        //service.getMedici().forEach(
//                medic -> {
//                    openTableWindows(medic);
//                }
//        );
    }

    @Override
    public void update(EntityChangeEvent event){
        initModel();
    }
    
    /**
     * Deschide ferestrele pentru mese
     */
    private void openTableWindows(Medic medic) {
        try {
            Stage tableStage = new Stage();
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("table.fxml")
            );

            Scene scene = new Scene(loader.load(), 800, 400);

            MedicController medicController = loader.getController();
            medicController.setService(service,medic);

            tableStage.setTitle("Medic: " + medic.getNume());
            tableStage.setScene(scene);
            tableStage.show();

        } catch (IOException e) {
            showError("Eroare la deschiderea ferestrelor medicilor: " + e.getMessage());
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


    public void handleProgramare(){
        Sectie selected =  (Sectie) sectieTable.getSelectionModel().getSelectedItem();
        if (selected==null){
            showError("Selecteaza o sectie!");
            return;
        }

        openProgramareWindows(selected);
    }

    public void openProgramareWindows(Sectie sectie){
        try {
            Stage tableStage = new Stage();
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("programare.fxml")
            );

            Scene scene = new Scene(loader.load(), 800, 400);

            ConsultatieController consultatieController = loader.getController();
            consultatieController.setService(service, sectie);

            tableStage.setTitle("programare!");
            tableStage.setScene(scene);
            tableStage.show();

        } catch (IOException e) {
            showError("Eroare la deschiderea ferestrelor de programare: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
