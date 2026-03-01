package org.example.restaurante.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.restaurante.HelloApplication;
import org.example.restaurante.domain.Client;
import org.example.restaurante.service.Service;

import java.io.IOException;

public class ClientController {
    @FXML
    private TextField usernameField;
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

        if (username.isEmpty() ) {
            showError("Nume obligatoriu!");
            return;
        }

        try {
            Client client = service.findClientByUsername(username);

            if (client != null) {
                hideError();
                openTableWindows();
            } else {
                showError("Nume incorect!");
            }

        } catch (Exception e) {
            showError("Eroare la autentificare: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deschide ferestrele pentru mese
     */
    private void openTableWindows() {
        try {
            Stage tableStage = new Stage();
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("table.fxml")
            );

            Scene scene = new Scene(loader.load(), 800, 400);

            String username = usernameField.getText().trim();
            Client client = service.findClientByUsername(username);

            ZborController zborController = loader.getController();
            zborController.setService(service, client);

            tableStage.setTitle("Client: " +  usernameField.getText());
            tableStage.setScene(scene);
            tableStage.show();

        } catch (IOException e) {
            showError("Eroare la deschiderea zborurilor: " + e.getMessage());
            e.printStackTrace();
        }
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
        usernameField.setOnAction(event -> handleLogin());
    }

}
