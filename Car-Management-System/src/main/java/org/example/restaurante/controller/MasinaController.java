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
import org.example.restaurante.domain.Masina;
import org.example.restaurante.domain.Status;
import org.example.restaurante.domain.User;
import org.example.restaurante.service.Service;
import org.example.restaurante.utils.observer.Observer;
import org.example.restaurante.utils.events.EntityChangeEvent;

import java.io.IOException;

public class MasinaController implements Observer<EntityChangeEvent> {

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
    private Label userLabel;

    @FXML
    private TextField searchField;

    private Service service;
    private User currentUser;
    private ObservableList<Masina> model = FXCollections.observableArrayList();

    /**
     * Setează serviciul și user-ul curent
     */
    public void setService(Service service, User currentUser) {
        this.service = service;
        this.currentUser = currentUser;
        service.addObserver(this);
        initializeData();
        }

    /**
     * Inițializare - se apelează automat după încărcarea FXML
     */
    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        denumireColumn.setCellValueFactory(new PropertyValueFactory<>("denumire_masina"));
        descriereColumn.setCellValueFactory(new PropertyValueFactory<>("descriere_masina"));
        pretColumn.setCellValueFactory(new PropertyValueFactory<>("pret_de_baza"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("statur_curent"));

        masiniTable.setItems(model);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterMasini(newValue);
        });
    }

    /**
     * Inițializează datele după setarea serviciului
     */
    private void initializeData() {
        if (currentUser != null) {
            userLabel.setText("Utilizator: " + currentUser.getUsername());
        }
        loadMasini();
    }

    /**
     * Încarcă toate mașinile în tabel
     */
    private void loadMasini() {

        model.setAll(service.getMasini());
    }

    /**
     * Filtrare mașini după text
     */
    private void filterMasini(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            loadMasini();
            return;
        }

        String lower = searchText.toLowerCase();
        ObservableList<Masina> filtered = FXCollections.observableArrayList();

        service.getMasini().forEach(masina -> {
            if (masina.getDenumire_masina().toLowerCase().contains(lower) ||
                    masina.getDescriere_masina().toLowerCase().contains(lower) ||
                    masina.getStatur_curent().toString().toLowerCase().contains(lower)) {
                filtered.add(masina);
            }
        });

        model.setAll(filtered);
    }

    /**
     * Observer pattern - actualizare automată
     */
    @Override
    public void update(EntityChangeEvent event) {
        loadMasini();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Eroare");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Atenție");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleDetalii(){
        Masina selected = masiniTable.selectionModelProperty().get().getSelectedItem();
        if (selected == null) {
            showError("Selecteaza o masina!");
            return;
        }

        Stage stage2 = new Stage();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("detaliu.fxml"));
        Scene scene1 = null;
        try{
            scene1 = new Scene(loader.load(), 320, 240);
        }catch (IOException e) {
            e.printStackTrace();
            showError("Nu pot încărca detaliu.fxml: " + e.getMessage());
            return;
        }
        DetaliuController detaliuController = loader.getController();
        detaliuController.setService(service, selected);
        stage2.setTitle("Masina: " +  selected.getDenumire_masina());
        stage2.setScene(scene1);
        stage2.setWidth(800);
        stage2.setHeight(400);
        stage2.show();
    }
}