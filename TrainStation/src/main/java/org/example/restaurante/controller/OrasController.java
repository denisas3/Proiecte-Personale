package org.example.restaurante.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.restaurante.HelloApplication;
import org.example.restaurante.service.Service;

import java.io.IOException;

public class OrasController {

    @FXML
    private Label errorLabel;

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

        try {
            hideError();
            openTableWindows();
        } catch (Exception e) {
            showError("Eroare la deschiderea ferestrei: " + e.getMessage());
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

            StatieController statieController = loader.getController();
            statieController.setService(service);

            tableStage.setTitle("Rute");
            tableStage.setScene(scene);
            tableStage.show();

        } catch (IOException e) {
            showError("Eroare la deschiderea echipelor: " + e.getMessage());
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

}
