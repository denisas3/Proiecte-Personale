package curs3.io.controller;

import curs3.io.domain.FriendRequest;
import curs3.io.domain.FriendRequestEvent;
import curs3.io.domain.Utilizator;
import curs3.io.service.ServiceFriendRequest;
import curs3.io.service.ServiceUser;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class FriendRequestsController implements Observer {

    private ServiceFriendRequest requestService;
    private ServiceUser userService;
    private Utilizator logged;

    @FXML private TableView<FriendRequest> requestTable;
    @FXML private TableColumn<FriendRequest, String> fromColumn;
    @FXML private TableColumn<FriendRequest, String> statusColumn;
    @FXML private TableColumn<FriendRequest, Void> actionColumn;

    @FXML private Label statusLabel;

    public void setServices(ServiceFriendRequest reqSrv, ServiceUser userSrv, Utilizator loggedUser) {
        this.requestService = reqSrv;
        this.userService = userSrv;
        this.logged = loggedUser;

        requestService.addObserver(this);

        fromColumn.setCellValueFactory(cell -> {
            Long fromId = cell.getValue().getFromUserId();
            Optional<Utilizator> u = userService.findById(fromId);
            return new SimpleStringProperty(u.map(Utilizator::getUsername).orElse("ID=" + fromId));
        });

        statusColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getStatus().name()));

        setupActionButtons();
        load();
    }

    private void setupActionButtons() {
        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button acceptBtn = new Button("Accept");
            private final Button deleteBtn = new Button("Delete");
            private final HBox box = new HBox(8, acceptBtn, deleteBtn);

            {
                acceptBtn.setOnAction(e -> {
                    FriendRequest req = getTableView().getItems().get(getIndex());
                    try {
                        requestService.accept(req);
                        statusLabel.setStyle("-fx-text-fill: green;");
                        statusLabel.setText("Accepted!");
                        load();
                    } catch (Exception ex) {
                        statusLabel.setStyle("-fx-text-fill: red;");
                        statusLabel.setText("ERROR: " + ex.getMessage());
                    }
                });

                deleteBtn.setOnAction(e -> {
                    FriendRequest req = getTableView().getItems().get(getIndex());
                    try {
                        requestService.reject(req);
                        statusLabel.setStyle("-fx-text-fill: green;");
                        statusLabel.setText("Deleted!");
                        load();
                    } catch (Exception ex) {
                        statusLabel.setStyle("-fx-text-fill: red;");
                        statusLabel.setText("ERROR: " + ex.getMessage());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {

                    setGraphic(box);
                }
            }
        });
    }

    private void load() {
        List<FriendRequest> pending = requestService.getPendingFor(logged.getId());
        requestTable.setItems(FXCollections.observableArrayList(pending));
    }

    @FXML
    private void handleRefresh() {
        load();
        statusLabel.setStyle("-fx-text-fill: green;");
        statusLabel.setText("Refreshed");
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!(arg instanceof FriendRequestEvent ev)) return;
        if (!logged.getId().equals(ev.getUserIdAffected())) return;

        javafx.application.Platform.runLater(this::load);
    }

}
