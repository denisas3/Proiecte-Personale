package curs3.io.controller;

import curs3.io.domain.Utilizator;
import curs3.io.service.ServiceMessage;
import curs3.io.service.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class SendFriendRequestController{

    @FXML
    private TableView<Utilizator> userTable;

    private ServiceUser userService;
    private ServiceMessage msgService;
    private Utilizator loggedUser;

    private ObservableList<Utilizator> users = FXCollections.observableArrayList();

    public void setServices(ServiceUser us, ServiceMessage ms, Utilizator current) {
        this.userService = us;
        this.msgService = ms;
        this.loggedUser = current;
        loadUsers();
    }

    private void loadUsers() {
        userTable.getColumns().clear();
        users.clear();

        TableColumn<Utilizator, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getUsername()));

        userTable.getColumns().add(usernameCol);

        for (Utilizator u : userService.getAll()) {
            if (!u.getId().equals(loggedUser.getId()))
                users.add(u);
        }

        userTable.setItems(users);
    }

    @FXML
    private void handleOpenFriendRequest() throws Exception {
        Utilizator selected = userTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        openFriendRequestWindow(loggedUser, selected);
        openFriendRequestWindow(selected, loggedUser);

        ((Stage) userTable.getScene().getWindow()).close();
    }

    private void openFriendRequestWindow(Utilizator from, Utilizator to) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chat-window.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));

        ChatController ctrl = loader.getController();
        ctrl.setServices(msgService, userService, from, to);

        stage.setTitle(from.getUsername() + " chatting with " + to.getUsername());
        stage.show();
    }


}
