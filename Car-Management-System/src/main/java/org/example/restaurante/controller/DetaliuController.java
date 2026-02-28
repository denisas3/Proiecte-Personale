package org.example.restaurante.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.restaurante.domain.Detaliu;
import org.example.restaurante.domain.Masina;
import org.example.restaurante.domain.Status;
import org.example.restaurante.service.Service;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observer;

public class DetaliuController implements Observer<EntityChangeEvent> {
    @FXML
    private TableView<Detaliu> detaliiTable;
    @FXML
    private TableColumn<Detaliu, Integer> idColumn;
    @FXML
    private TableColumn<Detaliu, String> detaliiColumn;
    @FXML
    private TableColumn<Detaliu, String> comentariuColumn;

    @FXML
    public Label masinaLabel;
    @FXML
    private Label errorLabel;

    @FXML
    public TextField comentariuField;

    private Service service;
    public Masina currentMasina;

    private ObservableList<Detaliu> model = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        detaliiColumn.setCellValueFactory(new PropertyValueFactory<>("detaliu"));
        comentariuColumn.setCellValueFactory(new PropertyValueFactory<>("comentariu"));

        detaliiTable.setItems(model);
    }

    public  void init_Model() {
        model.setAll(service.getDetaliu(currentMasina.getId()));
    }

    public void setService(Service service, Masina masina) {
        this.service = service;
        this.currentMasina = masina;
        service.addObserver(this);

        masinaLabel.setText(masina.getDenumire_masina());

        init_Model();
    }

    @Override
    public void update(EntityChangeEvent event) {
        init_Model();
    }

    public void handleComentariu(){
        String comentariu = comentariuField.getText();
        if (comentariu == null || comentariu.trim().isEmpty()) {
            showError("Scrie un comentariu!");
            return;
        }

        var id_masina = currentMasina.getId();
        Detaliu existent = service.getDetaliu(currentMasina.getId());
        if (existent == null) {
            showError("Nu există detaliu pentru mașina asta!");
            return;
        }
        String detaliu = existent.getDetaliu();


        Detaliu detaliu1 = new Detaliu(id_masina,detaliu,comentariu);
        service.update_detaliu(detaliu1);

        currentMasina.setStatur_curent(Status.NEEDS_APPROVAL);
        service.update_masina(currentMasina);
    }

    /**
     * Afișează mesaj de eroare
     */
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}
