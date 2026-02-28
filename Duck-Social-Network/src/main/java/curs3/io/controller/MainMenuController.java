package curs3.io.controller;

import curs3.io.domain.FriendRequestEvent;
import curs3.io.service.ServiceDuck;
import curs3.io.service.ServiceFriendRequest;
import curs3.io.service.ServiceFriendship;
import curs3.io.service.ServiceMessage;
import curs3.io.service.ServicePersoana;
import curs3.io.service.ServiceUser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class MainMenuController implements Observer {

    private ServiceDuck serviceDuck;
    private ServiceUser userService;
    private ServiceFriendship friendshipService;
    private ServicePersoana persoanaService;
    private ServiceMessage messageService;
    private ServiceFriendRequest friendRequestService;
    @FXML
    private Button friendRequestsBtn;


    public void setServices(ServiceDuck d,
                            ServiceUser u,
                            ServiceFriendship f,
                            ServicePersoana p,
                            ServiceMessage m,
                            ServiceFriendRequest fr) {
        this.serviceDuck = d;
        this.userService = u;
        this.friendshipService = f;
        this.persoanaService = p;
        this.messageService = m;
        this.friendRequestService = fr;

        friendRequestService.addObserver(this);
        updateFriendRequestBadge();
    }

    @FXML
    private void openFriendRequestsWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/friend-requests-window.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));

        FriendRequestsController ctrl = loader.getController();
        ctrl.setServices(friendRequestService, userService, LoginController.loggedUser);

        stage.setTitle("Friend Requests");
        stage.show();
    }


    @FXML
    private void openFriendshipWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/friendship-window.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));

        FriendshipController ctrl = loader.getController();
        ctrl.setServices(friendshipService, userService, friendRequestService);

        stage.setTitle("Friendships");
        stage.show();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void openUserWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/user-window.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));

        UserController ctrl = loader.getController();
        ctrl.setServices(
                serviceDuck,
                persoanaService,
                userService
        );

        stage.setTitle("User Management");
        stage.show();
    }

    @FXML
    private void openDuckWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/duck-window.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));

        DuckController ctrl = loader.getController();
        ctrl.setServices(
                serviceDuck,
                friendshipService,
                persoanaService,
                userService,
                friendRequestService
        );

        stage.setTitle("Ducks");
        stage.show();
    }

    @FXML
    private void openChatSelectWindow() {
        try {
            System.out.println("Button CLICKED! Opening chat-select");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/chat-select-window.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            ChatSelectController ctrl = loader.getController();
            ctrl.setServices(
                    userService,
                    messageService,
                    LoginController.loggedUser
            );

            stage.setTitle("Choose a User to Chat With");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateFriendRequestBadge() {
        int count = friendRequestService
                .getPendingFor(LoginController.loggedUser.getId())
                .size();

        if (count > 0) {
            friendRequestsBtn.setText("Friend Requests (" + count + ")");
            friendRequestsBtn.setStyle("-fx-font-weight: bold;");
        } else {
            friendRequestsBtn.setText("Friend Requests");
            friendRequestsBtn.setStyle("");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!(arg instanceof FriendRequestEvent ev)) return;

        // actualizezi badge-ul doar dacă evenimentul e pentru user-ul logat
        if (!ev.getUserIdAffected().equals(LoginController.loggedUser.getId())) return;

        Platform.runLater(this::updateFriendRequestBadge);
    }



}
