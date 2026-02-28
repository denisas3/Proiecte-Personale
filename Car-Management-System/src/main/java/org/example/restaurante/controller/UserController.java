package org.example.restaurante.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.restaurante.HelloApplication;
import org.example.restaurante.domain.User;
import org.example.restaurante.service.Service;

import java.io.IOException;

public class UserController{
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    private Service service;

    /**
     * Setează serviciul pentru acest controller
     */
    public void setService(Service service) {

        this.service = service;
    }

    /**
     * Handler pentru butonul de login
     */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Username și parola sunt obligatorii!");
            return;
        }

        try {
            User user = service.authenticate(username, password);

            if (user != null) {
                hideError();
                openTableWindows(user);
            } else {
                showError("Username sau parolă incorectă!");
            }

        } catch (Exception e) {
            showError("Eroare la autentificare: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deschide ferestrele pentru mese
     */
    private void openTableWindows(User user) {
        if(user.getUsername().equals("dealer")) {
            try {
                Stage tableStage = new Stage();
                FXMLLoader loader = new FXMLLoader(
                        HelloApplication.class.getResource("table.fxml")
                );

                Scene scene = new Scene(loader.load(), 800, 400);

                MasinaController masinaController = loader.getController();
                masinaController.setService(service, user);

                tableStage.setTitle("Masini");
                tableStage.setScene(scene);
                tableStage.show();

            } catch (IOException e) {
                showError("Eroare la deschiderea masinii: " + e.getMessage());
                e.printStackTrace();
            }
        }
        else if(user.getUsername().equals("admin")){
            Stage stage2 = new Stage();
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("admin.fxml"));
            Scene scene1 = null;
            try{
                scene1 = new Scene(loader.load(), 320, 240);
            }catch (IOException e) {
                e.printStackTrace();
                showError("Nu pot încărca detaliu.fxml: " + e.getMessage());
                return;
            }
            AdminController adminController = loader.getController();
            adminController.setService(service);
            stage2.setTitle("Masini:");
            stage2.setScene(scene1);
            stage2.setWidth(800);
            stage2.setHeight(400);
            stage2.show();
        }
    }

    /**
     * Închide fereastra de login
     */
    private void closeLoginWindow() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Afișează mesaj de eroare
     */
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    /**
     * Ascunde mesajul de eroare
     */
    private void hideError() {
        errorLabel.setVisible(false);
    }

    /**
     * Inițializare - se apelează automat după încărcarea FXML
     */
    @FXML
    private void initialize() {
        passwordField.setOnAction(event -> handleLogin());
    }

}
