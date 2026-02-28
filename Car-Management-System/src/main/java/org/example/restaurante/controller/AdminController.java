package org.example.restaurante.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.restaurante.HelloApplication;
import org.example.restaurante.domain.Masina;
import org.example.restaurante.domain.Status;
import org.example.restaurante.service.Service;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observer;

import java.io.IOException;

public class AdminController implements Observer<EntityChangeEvent> {
    @FXML
    private TableView<Masina> masiniTable;
    @FXML
    private TableColumn<Masina, Integer> idColumn;
    @FXML
    private TableColumn<Masina, String> denumireColumn;
    @FXML
    private TableColumn<Masina, String> descriereColumn;
    @FXML
    private TableColumn<Masina, Double> pretColumn;
    @FXML
    private TableColumn<Masina, Status> statusColumn;

    @FXML
    private Label errorLabel;
    @FXML
    private Label updateLabel;

    private Service service;

    private ObservableList<Masina> model = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        denumireColumn.setCellValueFactory(new PropertyValueFactory<>("denumire_masina"));
        descriereColumn.setCellValueFactory(new PropertyValueFactory<>("descriere_masina"));
        pretColumn.setCellValueFactory(new PropertyValueFactory<>("pret_de_baza"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("statur_curent"));

        masiniTable.setItems(model);
    }

        public  void init_Model() {
        model.setAll(service.getMasiniCuAnumitStatus());
    }

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        hideUpdate();
        init_Model();
    }

    private void hideUpdate() {
        updateLabel.setVisible(false);
    }

    @Override
    public void update(EntityChangeEvent event) {
        init_Model();
        showUpdate("Cerere noua de aprobare!");
    }

    public void handleDetalii(){
        Masina selected = masiniTable.selectionModelProperty().get().getSelectedItem();
        if (selected == null) {
            showError("Selecteaza o masina!");
            return;
        }

        Stage stage2 = new Stage();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("detaliu_admin.fxml"));
        Scene scene1 = null;
        try{
            scene1 = new Scene(loader.load(), 320, 240);
        }catch (IOException e) {
            e.printStackTrace();
            showError("Nu pot încărca detaliu.fxml: " + e.getMessage());
            return;
        }
        Detaliu_AdminController detaliu_adminController = loader.getController();
        detaliu_adminController.setService(service, selected);
        stage2.setTitle("Masina: " +  selected.getDenumire_masina());
        stage2.setScene(scene1);
        stage2.setWidth(800);
        stage2.setHeight(400);
        stage2.show();
    }

    private void showUpdate(String message) {
        updateLabel.setText(message);
        updateLabel.setVisible(true);
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}
