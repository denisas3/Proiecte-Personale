package curs3.io.controller;

import curs3.io.domain.Utilizator;
import curs3.io.service.ServiceFriendRequest;
import curs3.io.service.ServiceFriendship;
import curs3.io.service.ServiceUser;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class FriendshipController {

    private ServiceFriendship serviceFriendship;
    private ServiceUser userService;
    private ServiceFriendRequest serviceFriendRequest;

    @FXML private TableView<String[]> friendshipTable;
    @FXML private TableColumn<String[], String> fidColumn;
    @FXML private TableColumn<String[], String> u1Column;
    @FXML private TableColumn<String[], String> u2Column;

    @FXML private TextField communityCountField;
    private final ObservableList<String[]> friendshipData = FXCollections.observableArrayList();

    @FXML private TableView<Utilizator> userTable;
    @FXML private TableColumn<Utilizator, Long> idColumn;
    @FXML private TableColumn<Utilizator, String> usernameColumn;

    @FXML private ListView<String> friendsList;

    @FXML private TextField id1Field;
    @FXML private TextField id2Field;

    @FXML private Label statusLabel;

    private final ObservableList<Utilizator> users = FXCollections.observableArrayList();

    public void setServices(ServiceFriendship sFriend,
                            ServiceUser sUser,
                            ServiceFriendRequest sReq) {
        this.serviceFriendship = sFriend;
        this.userService = sUser;
        this.serviceFriendRequest = sReq;

        idColumn.setCellValueFactory(c -> new SimpleLongProperty(c.getValue().getId()).asObject());
        usernameColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getUsername()));

        fidColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue()[0]));
        u1Column.setCellValueFactory(c -> new SimpleStringProperty(c.getValue()[1]));
        u2Column.setCellValueFactory(c -> new SimpleStringProperty(c.getValue()[2]));

        friendshipTable.setItems(friendshipData);
        userTable.setItems(users);

        loadUsers();

        userTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Utilizator sel = userTable.getSelectionModel().getSelectedItem();
                if (sel != null) {
                    openProfile(sel);
                }
            }
        });

        userTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) showFriends(selected);
        });
    }

    private void loadUsers() {
        users.clear();
        userService.getAll().forEach(users::add);
    }

    private void showFriends(Utilizator u) {
        friendsList.getItems().clear();
        List<Utilizator> friends = serviceFriendship.getFriendsOf(u.getId());
        for (Utilizator f : friends) {
            friendsList.getItems().add(f.getId() + " | " + f.getUsername());
        }
    }

    @FXML
    private void handleAddFriend() {
        try {
            long id1 = Long.parseLong(id1Field.getText());
            long id2 = Long.parseLong(id2Field.getText());

            serviceFriendship.addFriend(id1, id2);

            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Friendship added!");

            id1Field.clear();
            id2Field.clear();

            loadUsers();
        } catch (Exception e) {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("ERROR: " + e.getMessage());
        }
    }

    @FXML
    private void handleRemoveFriend() {
        try {
            long id1 = Long.parseLong(id1Field.getText());
            long id2 = Long.parseLong(id2Field.getText());

            serviceFriendship.removeFriend(id1, id2);

            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Friendship removed!");

            id1Field.clear();
            id2Field.clear();

            loadUsers();
        } catch (Exception e) {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("ERROR: " + e.getMessage());
        }
    }

    @FXML
    private void handleSendRequest() {
        try {
            Utilizator selected = userTable.getSelectionModel().getSelectedItem();

            if (selected == null) {
                statusLabel.setStyle("-fx-text-fill: red;");
                statusLabel.setText("Select a user first!");
                return;
            }

            long fromId = LoginController.loggedUser.getId();
            long toId = selected.getId();

            if (fromId == toId) {
                statusLabel.setStyle("-fx-text-fill: red;");
                statusLabel.setText("You cannot send a request to yourself!");
                return;
            }

            serviceFriendRequest.sendRequest(fromId, toId);

            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Friend request sent to " + selected.getUsername() + "!");

        } catch (Exception e) {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("ERROR: " + e.getMessage());
        }
    }

    @FXML
    private void handleShowAllFriendships() {
        friendshipData.clear();
        List<String> all = serviceFriendship.getAllFriendshipsRaw();
        for (String row : all) {
            String[] parts = row.split("\\s*\\|\\s*");
            friendshipData.add(parts);
        }
        statusLabel.setStyle("-fx-text-fill: green;");
        statusLabel.setText("Friendships loaded!");
    }

    @FXML
    private void handleCountCommunities() {
        try {
            List<Utilizator> allUsers = new java.util.ArrayList<>();
            userService.getAll().forEach(allUsers::add);

            int count = serviceFriendship.gasesteComunitati(allUsers).size();

            communityCountField.setText(String.valueOf(count));
            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Community count updated!");
        } catch (Exception e) {
            communityCountField.setText("ERR");
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("ERROR: " + e.getMessage());
        }
    }

    private void openProfile(Utilizator selected) {
        var page = new curs3.io.ui.UserProfilePage(
                userService,
                serviceFriendship,
                serviceFriendRequest,
                null, // messageService dacă vrei și chat button
                LoginController.loggedUser,
                selected
        );

        Stage stage = new Stage();
        stage.setTitle(page.title());
        stage.setScene(new javafx.scene.Scene(page.root()));
        stage.show();
    }

}
