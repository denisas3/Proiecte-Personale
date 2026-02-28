package curs3.io.controller;

import curs3.io.domain.Utilizator;
import curs3.io.service.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class LoginController {

    private ServiceDuck serviceDuck;
    private ServiceUser serviceUser;
    private ServicePersoana servicePersoana;
    private ServiceFriendship serviceFriendship;
    private ServiceMessage serviceMessage;
    private ServiceFriendRequest serviceFriendRequest;

    public static Utilizator loggedUser;

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    public void setServices(ServiceDuck sd,
                            ServiceUser su,
                            ServiceFriendship sf,
                            ServicePersoana sp,
                            ServiceMessage sm,
                            ServiceFriendRequest sfr) {
        this.serviceDuck = sd;
        this.serviceUser = su;
        this.serviceFriendship = sf;
        this.servicePersoana = sp;
        this.serviceMessage = sm;
        this.serviceFriendRequest = sfr;
    }


    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            Optional<Utilizator> opt = serviceUser.findByUsername(username);
            if (opt.isEmpty()) {
                statusLabel.setText("Incorrect data!");
                return;
            }

            Utilizator u = opt.get();
            String hashedInput = ServiceUser.PasswordUtils.hashPassword(password);

            if (!hashedInput.equals(u.getPassword())) {
                statusLabel.setText("Incorrect data!");
                return;
            }

            loggedUser = u;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-menu.fxml"));
            Scene scene = new Scene(loader.load());

            MainMenuController ctrl = loader.getController();
            ctrl.setServices(
                    serviceDuck,
                    serviceUser,
                    serviceFriendship,
                    servicePersoana,
                    serviceMessage,
                    serviceFriendRequest
            );

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Duck Social Network");
            stage.show();

        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
        }
    }
}
