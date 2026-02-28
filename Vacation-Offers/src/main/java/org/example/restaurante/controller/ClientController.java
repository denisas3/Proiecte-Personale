package org.example.restaurante.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.restaurante.HelloApplication;
import org.example.restaurante.domain.AfisareOferetRow;
import org.example.restaurante.domain.Client;
import org.example.restaurante.domain.Hotel;
import org.example.restaurante.domain.Rezervare;
import org.example.restaurante.service.Service;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observer;

import java.io.IOException;
import java.time.LocalDate;

public class ClientController implements Observer<EntityChangeEvent> {
    @FXML
    private TableView<AfisareOferetRow> afisareOferetRowTableViewTable;
    @FXML
    private TableColumn<AfisareOferetRow, String> nume_hotelColumn;
    @FXML
    private TableColumn<AfisareOferetRow, String> nume_localitateColumn;
    @FXML
    private TableColumn<AfisareOferetRow, LocalDate> data_inceputColumn;
    @FXML
    private TableColumn<AfisareOferetRow, LocalDate> data_finalColumn;

    @FXML
    private Label updateLabel;

    private Service service;
    private Client currentClient;

    private ObservableList<AfisareOferetRow> model = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        nume_hotelColumn.setCellValueFactory(new PropertyValueFactory<>("nume_hotel"));
        nume_localitateColumn.setCellValueFactory(new PropertyValueFactory<>("nume_locatie"));
        data_inceputColumn.setCellValueFactory(new PropertyValueFactory<>("data_inceput"));
        data_finalColumn.setCellValueFactory(new PropertyValueFactory<>("data_final"));

        afisareOferetRowTableViewTable.setItems(model);
    }

    /**
     * Setează serviciul și user-ul curent
     */
    public void setService(Service service, Client client) {
        this.service = service;
        this.currentClient = client;
        service.addObserver(this);

        intiModel();
    }

    /**
     * Încarcă toate mașinile în tabel
     */
    private void intiModel() {
        model.setAll(service.getAfisareOfereteByClient(currentClient.getId()));
    }

    /**
     * Observer pattern - actualizare automată
     */
    @Override
    public void update(EntityChangeEvent event) {
        intiModel();

        if (!(event.getData() instanceof Rezervare rez)) {
            return;
        }

        if (rez.getId_client().equals(currentClient.getId())) {
            return;
        }

        Client autor = service.getClientById(rez.getId_client());
        if (autor == null) return;

        if (currentClient.getPasiune() != autor.getPasiune()) {
            return;
        }

        Hotel hotel = service.getHotelById(rez.getId_hotel());
        if (hotel == null) return;

        updateLabel.setText("Clientul " + autor.getNume()
                + "cu aceeasi pasiune cu tine a rezervat hotelul " + hotel.getHotel_name() + "!");
    }

    /// PENTRU SAVE LA REZERVARE
    public void handleRezerva(){
        var selected = afisareOferetRowTableViewTable.getSelectionModel().getSelectedItem();
        if(selected == null){
            showError("Selecteaza o oferta");
            return;
        }

        Integer id_client = currentClient.getId();
        Hotel hotel = service.getIdHotelByNume(selected.getNume_hotel());
        if (hotel == null) {
            showError("Nu găsesc hotelul pentru oferta selectată!");
            return;
        }
        Integer id_hotel = hotel.getId();
        LocalDate data_input = selected.getData_inceput();
        LocalDate data_final = selected.getData_final();

        Rezervare rez = new Rezervare(id_client, id_hotel, data_input, data_final);
        try {
            service.save_rezervare(rez);

        } catch (Exception ex) {
            showError("Eroare la salvare rezervare: " + ex.getMessage());
        }
    }


    public void handleAfiseazaRezervari(){
        openTableWindows(currentClient);
    }

    /**
     * Deschide ferestrele pentru mese
     */
    private void openTableWindows(Client client) {
        try {
            Stage tableStage = new Stage();
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("rezervare.fxml")
            );

            Scene scene = new Scene(loader.load(), 800, 400);

            RezervareController rezervareController = loader.getController();
            rezervareController.setService(service, client);

            tableStage.setTitle("Rezervari!");
            tableStage.setScene(scene);
            tableStage.show();

        } catch (IOException e) {
            showError("Eroare la deschiderea rezervarilor: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Eroare");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
