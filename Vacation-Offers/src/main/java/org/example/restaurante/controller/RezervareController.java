package org.example.restaurante.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.restaurante.domain.Client;
import org.example.restaurante.domain.Rezervare;
import org.example.restaurante.service.Service;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observer;

import java.time.LocalDate;

public class RezervareController implements Observer<EntityChangeEvent>{

    @FXML
    public TableView<Rezervare> rezervariTable;
    @FXML
    public TableColumn<Rezervare, Integer > id_hotelColumn;
    @FXML
    public TableColumn<Rezervare, LocalDate> data_inceputColumn;
    @FXML
    public TableColumn<Rezervare, LocalDate> data_finalColumn;

    Service service;
    Client currentClient;

    ObservableList<Rezervare> model = FXCollections.observableArrayList();

    public void initialize(){
        id_hotelColumn.setCellValueFactory(new PropertyValueFactory<>("id_hotel"));
        data_inceputColumn.setCellValueFactory(new PropertyValueFactory<>("data_inceput"));
        data_finalColumn.setCellValueFactory(new PropertyValueFactory<>("data_final"));

        rezervariTable.setItems(model);
    }

    public void setService(Service service, Client currentClient){
        this.service = service;
        this.currentClient = currentClient;

        service.addObserver(this);

        initModel();
    }

    public void initModel(){
        model.setAll(service.getRezervariAleClientului(currentClient.getId()));
    }

    @Override
    public void update(EntityChangeEvent entityChangeEvent) {
        initModel();
    }

}
