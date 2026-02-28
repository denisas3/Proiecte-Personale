package curs3.io.controller;
import javafx.stage.Stage;
import curs3.io.domain.Duck;
import curs3.io.domain.DuckType;
import curs3.io.repository.PageObservable;
import curs3.io.service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class DuckController {
    private ServiceDuck serviceDuck;
    private ServiceFriendship serviceFriendship;
    private ServicePersoana servicePersoana;
    private ServiceUser userService;
    private ServiceFriendRequest serviceFriendRequest;


    private ServiceCard serviceCard;
    private ServiceEvent serviceEvent;
    private int currentPage = 0;
    private final int pageSize = 10;
    private PageObservable pageObservable = new PageObservable();
    private String currentFilter = "ALL DUCKS";


    @FXML
    private Label welcomeLabel;
    @FXML
    private TableView<Duck> duckTableView;
    @FXML
    private TableColumn<Duck, Long> idColumn;
    @FXML
    private TableColumn<Duck, String> typeColumn;
    @FXML
    private TableColumn<Duck, Double> staminaColumn;
    @FXML
    private TableColumn<Duck, Double> speedColumn;
    @FXML
    private ObservableList<Duck> ducks = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> typeComboBox;

    public void setServices(ServiceDuck serviceDuck,
                            ServiceFriendship serviceFriendship,
                            ServicePersoana servicePersoana,
                            ServiceUser userService,
                            ServiceFriendRequest serviceFriendRequest) {

        this.serviceDuck = serviceDuck;
        this.serviceFriendship = serviceFriendship;
        this.servicePersoana = servicePersoana;
        this.userService = userService;
        this.serviceFriendRequest = serviceFriendRequest;

        welcomeLabel.setText("DUCK SOCIAL NETWORK");
        welcomeLabel.setStyle("-fx-font-size: 40px; -fx-font-family:'Verdana'; -fx-font-weight: bold;");
        duckTableView.setStyle("-fx-font-size: 16px;-fx-font-family:'Verdana'");
        typeComboBox.setStyle("-fx-font-size: 16px;-fx-font-family:'Verdana'");


        duckTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        staminaColumn.setCellValueFactory(new PropertyValueFactory<>("resistance"));
        speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));

        duckTableView.setItems(ducks);

        pageObservable.addObserver((page, totalPages) -> {
            pageLabel.setText("Page " + page + " / " + totalPages);
        });

        applyFilterAndLoadPage();

        ObservableList<String> typeOptions = FXCollections.observableArrayList();

        typeOptions.add("ALL DUCKS");
        for(DuckType t : DuckType.values())
            typeOptions.add(t.name());
        typeComboBox.setItems(typeOptions);

        typeComboBox.getSelectionModel().select("ALL DUCKS");

        typeComboBox.valueProperty().addListener((obs, old, newValue) -> {
            currentFilter = newValue;
            currentPage = 0;
            applyFilterAndLoadPage();
        });

    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

//    private void loadPage() {
//        ducks.setAll(serviceDuck.getPage(currentPage, pageSize));
//
//        int total = serviceDuck.getTotalDucks();
//        int totalPages = (total - 1) / pageSize + 1;
//
//        pageObservable.notifyObservers(currentPage + 1, totalPages);
//    }

    @FXML
    private void handleNextPage() {
        List<Duck> filtered = getFilteredDucks();
        int total = filtered.size();
        int maxPage = (total - 1) / pageSize;

        if (currentPage < maxPage) {
            currentPage++;
            applyFilterAndLoadPage();
        }
    }

    @FXML
    private void handlePrevPage() {
        if (currentPage > 0) {
            currentPage--;
            applyFilterAndLoadPage();
        }
    }


    @FXML
    private Label pageLabel;

    private List<Duck> getFilteredDucks() {
        List<Duck> all = serviceDuck.getAllDucks();

        return switch (currentFilter) {
            case "SWIMMING" -> all.stream()
                    .filter(d -> d.getType() == DuckType.SWIMMING)
                    .toList();
            case "FLYING" -> all.stream()
                    .filter(d -> d.getType() == DuckType.FLYING)
                    .toList();
            default -> all;
        };
    }

    private void applyFilterAndLoadPage() {

        DuckType type = null;

        if (!currentFilter.equals("ALL DUCKS")) {
            type = DuckType.valueOf(currentFilter);
        }

        List<Duck> page = serviceDuck.getPageFiltered(type, currentPage, pageSize);
        ducks.setAll(page);

        int total = serviceDuck.countFiltered(type);
        int totalPages = (total - 1) / pageSize + 1;

        pageObservable.notifyObservers(currentPage + 1, totalPages);
    }


//    private void applyFilterAndLoadPage() {
//
//        List<Duck> filtered = getFilteredDucks();
//
//        int total = filtered.size();
//        int totalPages = (total - 1) / pageSize + 1;
//
//        int from = currentPage * pageSize;
//        int to = Math.min(from + pageSize, total);
//
//        if (from >= total) {
//            currentPage = 0;
//            from = 0;
//            to = Math.min(pageSize, total);
//        }
//
//        ducks.setAll(filtered.subList(from, to));
//
//        pageObservable.notifyObservers(currentPage + 1, totalPages);
//    }

    @FXML
    private void openUserWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/user-window.fxml"));
        Scene scene = new Scene(loader.load());
        UserController ctrl = loader.getController();
        ctrl.setServices(serviceDuck, servicePersoana, userService);

        Stage stage = new Stage();
        stage.setTitle("User Management");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void openFriendshipWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/friendship-window.fxml"));
        Scene scene = new Scene(loader.load());
        FriendshipController ctrl = loader.getController();
        ctrl.setServices(serviceFriendship, userService, serviceFriendRequest);


        Stage stage = new Stage();
        stage.setTitle("Friendships");
        stage.setScene(scene);
        stage.show();
    }


}
