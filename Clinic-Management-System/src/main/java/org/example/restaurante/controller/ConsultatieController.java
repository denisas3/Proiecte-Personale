package org.example.restaurante.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.restaurante.domain.Consultatie;
import org.example.restaurante.domain.Sectie;
import org.example.restaurante.service.Service;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observer;
import java.time.LocalDate;
import java.time.LocalTime;

public class ConsultatieController implements Observer<EntityChangeEvent> {

    @FXML
    private Label SectieLabel;

    @FXML
    private ComboBox <String> medicComboBox;
    @FXML
    private DatePicker datePicker;

    /// PENTRU SPINNER
    @FXML
    private Spinner<Integer> oraSpinner;
    @FXML
    private Spinner<Integer> minuteSpinner;

    /// PENTRU CNP SI NUMELE PACIENTULUI
    @FXML
    private TextField numeTextField;
    @FXML
    private TextField cnpTextField;

    @FXML
    private Label errorLabel;

    private Service service;
    private Sectie currentSectie;


    /**
     * Setează serviciul și user-ul curent
     */
    public void setService(Service service, Sectie currentSectie) {
        this.service = service;
        this.currentSectie = currentSectie;

        service.addObserver(this);
        SectieLabel.setText("Sectia: " + currentSectie.getNume());
        initComboBox();

    }

    /// PENTRU SPINNER SA ALEG ORA
    @FXML
    private void initialize() {

        oraSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 8)
        );

        minuteSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0)
        );
    }


    private void initComboBox(){
        var lista = service.getMedici();
        var rez = lista.stream()
                        .filter(m->m.getId_sectie().equals(currentSectie.getId()))
                                .map(m->m.getNume())
                                        .toList();

       medicComboBox.setItems(FXCollections.observableArrayList(rez));

    }

    /**
     * Observer pattern - actualizare automată
     */
    @Override
    public void update(EntityChangeEvent event) {
        initComboBox();
    }


    /// PENTRU A LUAT DATE DINTR UN TEXTFIELD SI COMBO BOX SI DATA PICKER
    public void handleAdaugaProgramare(){
        if (medicComboBox.getValue() == null) {
            showError("Alege un medic.");
            return; }
        if (datePicker.getValue() == null) {
            showError("Alege o data.");
            return; }
        if (cnpTextField.getText().isBlank() || numeTextField.getText().isBlank()) {
            showError("Alege o ora.");
            return; }

        Integer id_medic = service.findMedicIdByNameAndIdSectie(medicComboBox.getValue(),currentSectie.getId());
        Integer cnp = Integer.parseInt(cnpTextField.getText());
        String nume = numeTextField.getText();
        LocalDate date = datePicker.getValue();
        int ora = oraSpinner.getValue();
        int minute = minuteSpinner.getValue();
        LocalTime timp = LocalTime.of(ora, minute);

        Consultatie consultatie = new Consultatie(id_medic, cnp, nume, date, timp);

        service.saveConsultatie(consultatie);
    }

    /**
     * Afișează mesaj de eroare
     */
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

}
