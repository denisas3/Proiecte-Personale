package org.example.restaurante.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.restaurante.domain.Bilet;
import org.example.restaurante.domain.Zbor;
import org.example.restaurante.domain.Client;
import org.example.restaurante.service.Service;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observer;
import org.example.restaurante.utils.paging.Page;
import org.example.restaurante.utils.paging.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;

    public class ZborController implements Observer<EntityChangeEvent> {

    /// PENTRU TABEL
    @FXML
    private TableView<Zbor>  zboruriTable;
    @FXML
    private TableColumn<Zbor, Integer> idColumn;
    @FXML
    private TableColumn<Zbor, String> de_unde;
    @FXML
    private TableColumn<Zbor, String> pana_unde;
    @FXML
    private TableColumn<Zbor, LocalDateTime> decolare;
    @FXML
    private TableColumn<Zbor, LocalDateTime> aterizare;
    @FXML
    private TableColumn<Zbor, Integer> loc;
    @FXML
    private TableColumn<Zbor, Integer> locDisponibileColumn;


    /// PENTRU COMBO BOX
    @FXML
    private ComboBox<String> fromCombo;
    @FXML
    private ComboBox<String> toCombo;


    /// PENTRU DATE PICKER
    @FXML
    private DatePicker datePicker;


    /// PENTRU PAGINARE
    @FXML private Label pageLabel;
    private final int pageSize = 5;
    private int currentPage = 0;
    private int totalPages = 1;


    @FXML
    private Label userLabel;
    private Client currentUser;
    private Service service;

    private ObservableList<Zbor> model = FXCollections.observableArrayList();

    public void initModel() {
        model.setAll(service.getZbores());
    }

    /**
     * Inițializare - se apelează automat după încărcarea FXML
     */
    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        de_unde.setCellValueFactory(new PropertyValueFactory<>("de_unde"));
        pana_unde.setCellValueFactory(new PropertyValueFactory<>("pana_unde"));
        decolare.setCellValueFactory(new PropertyValueFactory<>("decolare"));
        aterizare.setCellValueFactory(new PropertyValueFactory<>("aterizare"));
        loc.setCellValueFactory(new PropertyValueFactory<>("loc"));
        locDisponibileColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(
                        service.getLocuriDisponibile(cellData.getValue())
                )
        );

        zboruriTable.setItems(model);

    }

    /**
     * Setează serviciul și user-ul curent
     */
    public void setService(Service service, Client client) {
        this.service = service;
        this.currentUser = client;
        service.addObserver(this);

        userLabel.setText(client.getUsername());

        initFilters();
        update_zbor();
        loadPage();
    }

    @Override
    public void update(EntityChangeEvent event) {
        update_zbor();
        zboruriTable.refresh();
    }

    public void update_zbor() {
        if (currentUser != null) {
            currentUser = service.findClientByUsername(currentUser.getUsername());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Eroare");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /// PENTRU COMBO BOX
    private void initFilters() {
        var fromList = service.fillComboBoxFrom();
        var toList = service.fillComboBoxTo();

        fromCombo.setItems(FXCollections.observableArrayList(fromList));
        toCombo.setItems(FXCollections.observableArrayList(toList));
    }

    /// PENTRU BUTONUL DE FILTRARE
    @FXML
    private void handleFilterZboruri() {
        String from = fromCombo.getValue();
        String to = toCombo.getValue();
        LocalDate date = datePicker.getValue();

        model.setAll(service.filterZboruri(from, to, date));
    }

    @FXML
    private void handleCumpara() {
        Zbor selected = zboruriTable.getSelectionModel().getSelectedItem();
        if (selected == null) { showError("Selecteaza un zbor!"); return; }

        int disponibile = service.getLocuriDisponibile(selected);
        if (disponibile <= 0) { showError("Nu mai sunt locuri disponibile!"); return; }

        String username = userLabel.getText();
        Integer id_zbor =  zboruriTable.getSelectionModel().getSelectedItem().getId();
        LocalDateTime data_cumpararii = zboruriTable.getSelectionModel().getSelectedItem().getDecolare();

        Bilet bilet = new Bilet(username, id_zbor, data_cumpararii);
        service.saveBilet(bilet);
    }


    /// PENTRU PAGINARE
    private void loadPage() {
        String from = fromCombo.getValue();
        String to = toCombo.getValue();
        LocalDate date = datePicker.getValue();

        Pageable pageable = new Pageable(pageSize, currentPage);
        Page<Zbor> page = service.getZboruriPage(from, to, date, pageable);

        java.util.List<Zbor> list = (java.util.List<Zbor>) page.getElementsOnPage();
        model.setAll(list);

        int total = page.getTotalNumberOfElements();
        totalPages = (int) Math.ceil((double) total / pageSize);
        if (totalPages == 0) totalPages = 1;

        pageLabel.setText((currentPage + 1) + " / " + totalPages);
    }

    @FXML
    private void handleFilter() {
        currentPage = 0;
        loadPage();
    }

    @FXML
    private void handleNext() {
        if (currentPage < totalPages - 1) {
            currentPage++;
            loadPage();
        }
    }

    @FXML
    private void handlePrevious() {
        if (currentPage > 0) {
            currentPage--;
            loadPage();
        }
    }


}
