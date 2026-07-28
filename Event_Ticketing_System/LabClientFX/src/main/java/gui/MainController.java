//package gui;
//
//import javafx.application.Platform;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleLongProperty;
//import javafx.beans.property.SimpleObjectProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.example.lab03.domain.Employee;
//import org.example.lab03.domain.Show;
//import org.example.lab03.domain.Ticket;
//import org.example.lab03.services.ILabObserver;
//import org.example.lab03.services.ILabServices;
//import org.example.lab03.services.LabException;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.List;
//
//public class MainController implements ILabObserver {
//
//    private ILabServices server;
//    private Employee currentEmployee;
//
//    private static final Logger logger = LogManager.getLogger(MainController.class);
//
//    @FXML
//    private Label welcomeLabel;
//
//    @FXML
//    private TableView<Show> allShowsTable;
//    @FXML
//    private TableColumn<Show, String> artistColumn;
//    @FXML
//    private TableColumn<Show, LocalDateTime> dateColumn;
//    @FXML
//    private TableColumn<Show, String> locationColumn;
//    @FXML
//    private TableColumn<Show, Integer> availableSeatsColumn;
//    @FXML
//    private TableColumn<Show, Integer> soldSeatsColumn;
//
//    @FXML
//    private DatePicker searchDatePicker;
//
//    @FXML
//    private TableView<Show> searchResultsTable;
//    @FXML
//    private TableColumn<Show, String> resultArtistColumn;
//    @FXML
//    private TableColumn<Show, String> resultLocationColumn;
//    @FXML
//    private TableColumn<Show, String> resultHourColumn;
//    @FXML
//    private TableColumn<Show, Integer> resultAvailableSeatsColumn;
//
//    @FXML
//    private TextField buyerNameField;
//    @FXML
//    private TextField seatCountField;
//
//    @FXML
//    private ComboBox<Show> showComboBox;
//
//    @FXML
//    private TableView<Ticket> ticketsTable;
//    @FXML
//    private TableColumn<Ticket, Long> ticketIdColumn;
//    @FXML
//    private TableColumn<Ticket, String> ticketBuyerColumn;
//    @FXML
//    private TableColumn<Ticket, Integer> ticketSeatCountColumn;
//
//    @FXML
//    private TextField newSeatCountField;
//
//    public void setServer(ILabServices server) {
//        this.server = server;
//    }
//
//    public void setEmployee(Employee employee) {
//        this.currentEmployee = employee;
//        welcomeLabel.setText("Welcome, " + employee.getUsername() + "!");
//    }
//
//    public void initData() {
//        initTables();
//        loadAllShows();
//        loadShowsInCombo();
//    }
//
//    private void initTables() {
//        artistColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getArtistName()));
//        dateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDate()));
//        locationColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocation()));
//        availableSeatsColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAvailableSeats()).asObject());
//        soldSeatsColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getSoldSeats()).asObject());
//
//        resultArtistColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getArtistName()));
//        resultLocationColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocation()));
//        resultHourColumn.setCellValueFactory(data ->
//                new SimpleStringProperty(data.getValue().getDate().toLocalTime().toString()));
//        resultAvailableSeatsColumn.setCellValueFactory(data ->
//                new SimpleIntegerProperty(data.getValue().getAvailableSeats()).asObject());
//
//        ticketIdColumn.setCellValueFactory(data -> new SimpleLongProperty(data.getValue().getId()).asObject());
//        ticketBuyerColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBuyerName()));
//        ticketSeatCountColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getSeatCount()).asObject());
//
//        showComboBox.setCellFactory(cb -> new ListCell<>() {
//            @Override
//            protected void updateItem(Show item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty || item == null) {
//                    setText(null);
//                } else {
//                    setText(item.getArtistName() + " - " + item.getLocation() + " - " + item.getDate());
//                }
//            }
//        });
//
//        showComboBox.setButtonCell(new ListCell<>() {
//            @Override
//            protected void updateItem(Show item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty || item == null) {
//                    setText(null);
//                } else {
//                    setText(item.getArtistName() + " - " + item.getLocation() + " - " + item.getDate());
//                }
//            }
//        });
//    }
//
//    private ObservableList<Show> convertShows(Iterable<Show> shows) {
//        ObservableList<Show> list = FXCollections.observableArrayList();
//        for (Show show : shows) {
//            list.add(show);
//        }
//        return list;
//    }
//
//    private void loadAllShows() {
//        try {
//            allShowsTable.setItems(convertShows(server.getAllShows()));
//        } catch (LabException e) {
//            showError(e.getMessage());
//        }
//    }
//
//    private void loadShowsInCombo() {
//        try {
//            showComboBox.setItems(convertShows(server.getAllShows()));
//        } catch (LabException e) {
//            showError(e.getMessage());
//        }
//    }
//
//    @FXML
//    public void handleSearchShows() {
//        LocalDate date = searchDatePicker.getValue();
//        if (date == null) {
//            showError("Selecteaza o data.");
//            return;
//        }
//
//        try {
//            LocalDateTime dt = LocalDateTime.of(date, LocalTime.MIN);
//            List<Show> results = server.findShowsByDate(dt);
//            searchResultsTable.setItems(FXCollections.observableArrayList(results));
//        } catch (LabException e) {
//            showError(e.getMessage());
//        }
//    }
//
//    @FXML
//    public void handleBuyTicket() {
//        Show selectedShow = searchResultsTable.getSelectionModel().getSelectedItem();
//        if (selectedShow == null) {
//            selectedShow = allShowsTable.getSelectionModel().getSelectedItem();
//        }
//
//        if (selectedShow == null) {
//            showError("Selecteaza un spectacol.");
//            return;
//        }
//
//        try {
//            String buyer = buyerNameField.getText();
//            int seats = Integer.parseInt(seatCountField.getText());
//
//            Ticket ticket = new Ticket(buyer, seats, LocalDateTime.now(), selectedShow);
//            server.buyTicket(ticket);
//
//            buyerNameField.clear();
//            seatCountField.clear();
//            refreshShows();
//        } catch (NumberFormatException e) {
//            showError("Numarul de locuri trebuie sa fie numeric.");
//        } catch (LabException e) {
//            showError(e.getMessage());
//        }
//    }
//
//    @FXML
//    public void handleLoadTickets() {
//        Show show = showComboBox.getValue();
//        if (show == null) {
//            showError("Selecteaza un spectacol.");
//            return;
//        }
//
//        try {
//            List<Ticket> tickets = server.findTicketsByShow(show);
//            ticketsTable.setItems(FXCollections.observableArrayList(tickets));
//        } catch (LabException e) {
//            showError(e.getMessage());
//        }
//    }
//
//    @FXML
//    public void handleUpdateTicket() {
//        Ticket selectedTicket = ticketsTable.getSelectionModel().getSelectedItem();
//        if (selectedTicket == null) {
//            showError("Selecteaza un bilet.");
//            return;
//        }
//
//        try {
//            int newSeats = Integer.parseInt(newSeatCountField.getText());
//            selectedTicket.setSeatCount(newSeats);
//            server.updateTicket(selectedTicket);
//
//            newSeatCountField.clear();
//            handleLoadTickets();
//            refreshShows();
//        } catch (NumberFormatException e) {
//            showError("Noul numar de locuri trebuie sa fie numeric.");
//        } catch (LabException e) {
//            showError(e.getMessage());
//        }
//    }
//
//    @FXML
//    public void handleLogout() {
//        logout();
//        Platform.exit();
//    }
//
//    public void logout() {
//        try {
//            server.logout(currentEmployee, this);
//        } catch (LabException e) {
//            logger.error("Logout error", e);
//        }
//    }
//
//    private void refreshShows() {
//        loadAllShows();
//        loadShowsInCombo();
//    }
//
//    private void showError(String msg) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Eroare");
//        alert.setHeaderText(null);
//        alert.setContentText(msg);
//        alert.showAndWait();
//    }
//
//    @Override
//    public void ticketsSold(Ticket ticket) throws LabException {
//        Platform.runLater(this::refreshShows);
//    }
//
//    @Override
//    public void ticketsUpdated(Ticket ticket) throws LabException {
//        Platform.runLater(this::refreshShows);
//    }
//}

package gui;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.lab03.domain.Employee;
import org.example.lab03.domain.Show;
import org.example.lab03.domain.Ticket;
import org.example.lab03.services.ILabObserver;
import org.example.lab03.services.ILabServices;
import org.example.lab03.services.LabException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MainController implements ILabObserver {

    private ILabServices server;
    private Employee currentEmployee;

    private static final Logger logger = LogManager.getLogger(MainController.class);

    private final ObservableList<Show> allShowsModel = FXCollections.observableArrayList();
    private final ObservableList<Show> searchResultsModel = FXCollections.observableArrayList();
    private final ObservableList<Ticket> ticketsModel = FXCollections.observableArrayList();
    private final ObservableList<Show> comboShowsModel = FXCollections.observableArrayList();

    @FXML
    private Label welcomeLabel;

    @FXML
    private TableView<Show> allShowsTable;
    @FXML
    private TableColumn<Show, String> artistColumn;
    @FXML
    private TableColumn<Show, LocalDateTime> dateColumn;
    @FXML
    private TableColumn<Show, String> locationColumn;
    @FXML
    private TableColumn<Show, Integer> availableSeatsColumn;
    @FXML
    private TableColumn<Show, Integer> soldSeatsColumn;

    @FXML
    private DatePicker searchDatePicker;

    @FXML
    private TableView<Show> searchResultsTable;
    @FXML
    private TableColumn<Show, String> resultArtistColumn;
    @FXML
    private TableColumn<Show, String> resultLocationColumn;
    @FXML
    private TableColumn<Show, String> resultHourColumn;
    @FXML
    private TableColumn<Show, Integer> resultAvailableSeatsColumn;

    @FXML
    private TextField buyerNameField;
    @FXML
    private TextField seatCountField;

    @FXML
    private ComboBox<Show> showComboBox;

    @FXML
    private TableView<Ticket> ticketsTable;
    @FXML
    private TableColumn<Ticket, Long> ticketIdColumn;
    @FXML
    private TableColumn<Ticket, String> ticketBuyerColumn;
    @FXML
    private TableColumn<Ticket, Integer> ticketSeatCountColumn;

    @FXML
    private TextField newSeatCountField;

    @FXML
    public void initialize() {
        initTables();
        logger.info("MainController initialized");
    }

    public void setServer(ILabServices server) {
        this.server = server;
    }

    public void setEmployee(Employee employee) {
        this.currentEmployee = employee;
        welcomeLabel.setText("Welcome, " + employee.getUsername() + "!");
    }

    public void initData() {
        refreshAllData();
    }

    private void initTables() {
        artistColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getArtistName()));
        dateColumn.setCellValueFactory(data ->
                new SimpleObjectProperty<>(data.getValue().getDate()));
        locationColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getLocation()));
        availableSeatsColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getAvailableSeats()).asObject());
        soldSeatsColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getSoldSeats()).asObject());

        resultArtistColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getArtistName()));
        resultLocationColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getLocation()));
        resultHourColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDate().toLocalTime().toString()));
        resultAvailableSeatsColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getAvailableSeats()).asObject());

        ticketIdColumn.setCellValueFactory(data ->
                new SimpleLongProperty(data.getValue().getId()).asObject());
        ticketBuyerColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getBuyerName()));
        ticketSeatCountColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getSeatCount()).asObject());

        allShowsTable.setItems(allShowsModel);
        searchResultsTable.setItems(searchResultsModel);
        ticketsTable.setItems(ticketsModel);
        showComboBox.setItems(comboShowsModel);

        showComboBox.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Show item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null :
                        item.getArtistName() + " - " + item.getLocation() + " - " + item.getDate());
            }
        });

        showComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Show item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null :
                        item.getArtistName() + " - " + item.getLocation() + " - " + item.getDate());
            }
        });
    }

    private ObservableList<Show> convertShows(Iterable<Show> shows) {
        ObservableList<Show> list = FXCollections.observableArrayList();
        for (Show show : shows) {
            list.add(show);
        }
        return list;
    }

    private void refreshAllData() {
        LocalDate selectedDate = searchDatePicker != null ? searchDatePicker.getValue() : null;
        Show selectedComboShow = showComboBox != null ? showComboBox.getValue() : null;

        Thread thread = new Thread(() -> {
            try {
                ObservableList<Show> freshShows = convertShows(server.getAllShows());

                ObservableList<Show> freshSearchResults = FXCollections.observableArrayList();
                if (selectedDate != null) {
                    LocalDateTime dt = LocalDateTime.of(selectedDate, LocalTime.MIN);
                    List<Show> results = server.findShowsByDate(dt);
                    freshSearchResults.setAll(results);
                }

                ObservableList<Ticket> freshTickets = FXCollections.observableArrayList();
                if (selectedComboShow != null) {
                    List<Ticket> tickets = server.findTicketsByShow(selectedComboShow);
                    freshTickets.setAll(tickets);
                }

                Platform.runLater(() -> {
                    allShowsModel.setAll(freshShows);
                    comboShowsModel.setAll(freshShows);
                    searchResultsModel.setAll(freshSearchResults);
                    ticketsModel.setAll(freshTickets);

                    allShowsTable.refresh();
                    searchResultsTable.refresh();
                    ticketsTable.refresh();
                    showComboBox.setItems(comboShowsModel);

                    logger.info("UI refreshed. First show seats = {}",
                            freshShows.isEmpty() ? "none" : freshShows.get(0).getAvailableSeats());
                });

            } catch (LabException e) {
                Platform.runLater(() -> showError(e.getMessage()));
            } catch (Exception e) {
                logger.error("Error refreshing all data", e);
                Platform.runLater(() ->
                        showError("Eroare la actualizarea datelor: " + e.getMessage()));
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    public void handleSearchShows() {
        LocalDate date = searchDatePicker.getValue();
        if (date == null) {
            showError("Selecteaza o data.");
            return;
        }

        Thread thread = new Thread(() -> {
            try {
                LocalDateTime dt = LocalDateTime.of(date, LocalTime.MIN);
                List<Show> results = server.findShowsByDate(dt);

                Platform.runLater(() -> {
                    searchResultsModel.setAll(results);
                    searchResultsTable.refresh();
                });
            } catch (LabException e) {
                Platform.runLater(() -> showError(e.getMessage()));
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    public void handleBuyTicket() {
        Show selectedShow = searchResultsTable.getSelectionModel().getSelectedItem();
        if (selectedShow == null) {
            selectedShow = allShowsTable.getSelectionModel().getSelectedItem();
        }

        if (selectedShow == null) {
            showError("Selecteaza un spectacol.");
            return;
        }

        String buyer = buyerNameField.getText();
        if (buyer == null || buyer.isBlank()) {
            showError("Completeaza numele cumparatorului.");
            return;
        }

        final int seats;
        try {
            seats = Integer.parseInt(seatCountField.getText());
            if (seats <= 0) {
                showError("Numarul de locuri trebuie sa fie pozitiv.");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Numarul de locuri trebuie sa fie numeric.");
            return;
        }

        Ticket ticket = new Ticket(buyer, seats, LocalDateTime.now(), selectedShow);

        Thread thread = new Thread(() -> {
            try {
                server.buyTicket(ticket);

                Platform.runLater(() -> {
                    buyerNameField.clear();
                    seatCountField.clear();
                });

                refreshAllData();

            } catch (LabException e) {
                Platform.runLater(() -> showError(e.getMessage()));
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    public void handleLoadTickets() {
        Show show = showComboBox.getValue();
        if (show == null) {
            showError("Selecteaza un spectacol.");
            return;
        }

        Thread thread = new Thread(() -> {
            try {
                List<Ticket> tickets = server.findTicketsByShow(show);

                Platform.runLater(() -> {
                    ticketsModel.setAll(tickets);
                    ticketsTable.refresh();
                });
            } catch (LabException e) {
                Platform.runLater(() -> showError(e.getMessage()));
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    public void handleUpdateTicket() {
        Ticket selectedTicket = ticketsTable.getSelectionModel().getSelectedItem();
        if (selectedTicket == null) {
            showError("Selecteaza un bilet.");
            return;
        }

        final int newSeats;
        try {
            newSeats = Integer.parseInt(newSeatCountField.getText());
            if (newSeats <= 0) {
                showError("Noul numar de locuri trebuie sa fie pozitiv.");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Noul numar de locuri trebuie sa fie numeric.");
            return;
        }

        Thread thread = new Thread(() -> {
            try {
                selectedTicket.setSeatCount(newSeats);
                server.updateTicket(selectedTicket);

                Platform.runLater(() -> newSeatCountField.clear());

                refreshAllData();

            } catch (LabException e) {
                Platform.runLater(() -> showError(e.getMessage()));
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    public void handleLogout() {
        logout();
        Platform.exit();
    }

    public void logout() {
        try {
            server.logout(currentEmployee, this);
        } catch (LabException e) {
            logger.error("Logout error", e);
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Eroare");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @Override
    public void ticketsSold(Ticket ticket) throws LabException {
        logger.info("ticketsSold received for {}", ticket);
        refreshAllData();
    }

    @Override
    public void ticketsUpdated(Ticket ticket) throws LabException {
        logger.info("ticketsUpdated received for {}", ticket);
        refreshAllData();
    }
}