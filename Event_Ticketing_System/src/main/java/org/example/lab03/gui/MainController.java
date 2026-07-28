package org.example.lab03.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.lab03.Employee;
import org.example.lab03.Show;
import org.example.lab03.Ticket;
import org.example.lab03.services.ShowService;
import org.example.lab03.services.TicketService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MainController {
    private ShowService showService;
    private TicketService ticketService;
    private Employee loggedEmployee;

    private final ObservableList<Show> allShowsModel = FXCollections.observableArrayList();
    private final ObservableList<Show> filteredShowsModel = FXCollections.observableArrayList();
    private final ObservableList<Ticket> ticketsModel = FXCollections.observableArrayList();

    @FXML
    private Label welcomeLabel;

    @FXML
    private TableView<Show> allShowsTable;
    @FXML
    private TableColumn<Show, String> artistColumn;
    @FXML
    private TableColumn<Show, String> dateColumn;
    @FXML
    private TableColumn<Show, String> locationColumn;
    @FXML
    private TableColumn<Show, Integer> availableSeatsColumn;
    @FXML
    private TableColumn<Show, Integer> soldSeatsColumn;

    @FXML
    private TableView<Show> filteredShowsTable;
    @FXML
    private TableColumn<Show, String> filteredArtistColumn;
    @FXML
    private TableColumn<Show, String> filteredLocationColumn;
    @FXML
    private TableColumn<Show, String> filteredHourColumn;
    @FXML
    private TableColumn<Show, Integer> filteredAvailableSeatsColumn;

    @FXML
    private DatePicker searchDatePicker;

    @FXML
    private TextField buyerNameField;
    @FXML
    private TextField seatCountField;

    @FXML
    private TextField buyerNameUpdateField;

    @FXML
    private TextField newSeatCountField;

    @FXML
    private ComboBox<Show> showComboBox;

    @FXML
    private TableView<Ticket> ticketsTable;
    @FXML
    private TableColumn<Ticket, Long> ticketIdColumn;
    @FXML
    private TableColumn<Ticket, String> ticketBuyerColumn;
    @FXML
    private TableColumn<Ticket, Integer> ticketSeatsColumn;


    public void setServices(ShowService showService, TicketService ticketService, Employee loggedEmployee) {
        this.showService = showService;
        this.ticketService = ticketService;
        this.loggedEmployee = loggedEmployee;

        welcomeLabel.setText("Welcome, "+loggedEmployee.getUsername() + "!");
        loadAllShows();
    }

    @FXML
    private void initialize() {
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artistName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        soldSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("soldSeats"));

        filteredArtistColumn.setCellValueFactory(new PropertyValueFactory<>("artistName"));
        filteredLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        filteredAvailableSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        filteredHourColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getDate().toLocalTime().toString()
                )
        );

        ticketIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ticketBuyerColumn.setCellValueFactory(new PropertyValueFactory<>("buyerName"));
        ticketSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("seatCount"));

        allShowsTable.setItems(allShowsModel);
        filteredShowsTable.setItems(filteredShowsModel);
        ticketsTable.setItems(ticketsModel);
    }

    private void loadAllShows() {
        List<Show> shows = showService.getAllShows();
        allShowsModel.setAll(shows);
        showComboBox.setItems(FXCollections.observableArrayList(shows));
    }

    @FXML
    private void handleSearch() {
        LocalDate date = searchDatePicker.getValue();
        if (date == null) {
            showError("Select a date!");
            return;
        }

        LocalDateTime dateTime = date.atStartOfDay();
        filteredShowsModel.setAll(showService.searchShowsByDate(dateTime));
    }

    @FXML
    private void handleBuyTicket() {
        Show selectedShow = allShowsTable.getSelectionModel().getSelectedItem();
        if (selectedShow == null) {
            showError("Select a show!");
            return;
        }

        try{
            String buyerName = buyerNameField.getText();
            int seatCount = Integer.parseInt(seatCountField.getText());

            ticketService.buyTicket(selectedShow.getId(),buyerName,seatCount);

            loadAllShows();
            if(searchDatePicker.getValue() != null) {
                LocalDate date = searchDatePicker.getValue();
                LocalDateTime dateTime = date.atStartOfDay();
                filteredShowsModel.setAll(showService.searchShowsByDate(dateTime));
            }

            showInfo("Ticket sold!");
        }catch (NumberFormatException e) {
            showError("Numarul de locuri trebuie sa fie un numar intreg.");
        }catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void handleLoadTicketsForShow() {
        Show selectedShow = showComboBox.getValue();
        if (selectedShow == null) {
            showError("Selecteaza un spectacol.");
            return;
        }

        try {
            ticketsModel.setAll(ticketService.findByShow(selectedShow));
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void handleUpdateTicket() {
        Ticket selectedTicket = ticketsTable.getSelectionModel().getSelectedItem();
        if (selectedTicket == null) {
            showError("Selecteaza un bilet din tabel.");
            return;
        }

        try {
            int newSeats = Integer.parseInt(newSeatCountField.getText());

            ticketService.updateTicket(selectedTicket.getId(), newSeats);

            refreshDataAfterChange();
            handleLoadTicketsForShow();

            showInfo("Ticket updated!");
        } catch (NumberFormatException e) {
            showError("Noul numar de locuri trebuie sa fie un numar.");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void refreshDataAfterChange() {
        loadAllShows();

        if (searchDatePicker.getValue() != null) {
            LocalDate date = searchDatePicker.getValue();
            LocalDateTime dateTime = date.atStartOfDay();
            filteredShowsModel.setAll(showService.searchShowsByDate(dateTime));
        }
    }

    @FXML
    public void handleLogout() {
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        stage.close();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Eroare");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

