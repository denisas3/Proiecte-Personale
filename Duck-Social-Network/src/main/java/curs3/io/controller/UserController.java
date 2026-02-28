package curs3.io.controller;

import curs3.io.domain.*;
import curs3.io.service.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UserController {

    private ServiceDuck serviceDuck;
    private ServicePersoana servicePersoana;
    private ServiceUser userService;

    @FXML private TableView<Utilizator> userTable;
    @FXML private ComboBox<String> typeFilter;

    @FXML private TextField duckUsername, duckEmail, duckPassword, duckSpeed, duckResistance, duckType;

    @FXML private TextField persUsername, persEmail, persPassword, persFirstName, persLastName, persJob, persEmp;
    @FXML private DatePicker persBirth;

    @FXML private Label statusLabel;

    private ObservableList<Utilizator> data = FXCollections.observableArrayList();

    public void setServices(ServiceDuck d, ServicePersoana p, ServiceUser u) {
        serviceDuck = d;
        servicePersoana = p;
        userService = u;

        typeFilter.setItems(FXCollections.observableArrayList("USERS", "PERSOANE", "DUCKS"));
        typeFilter.getSelectionModel().select("USERS");
        typeFilter.valueProperty().addListener((obs, old, v) -> loadUsers());

        loadUsers();
    }

    private void loadUsers() {
        userTable.getColumns().clear();
        data.clear();

        String filter = typeFilter.getValue();

        switch (filter) {
            case "USERS" -> loadUsersBasic();
            case "PERSOANE" -> loadPersons();
            case "DUCKS" -> loadDucks();
        }
    }

    private void addCol(String name, java.util.function.Function<Utilizator,String> map) {
        TableColumn<Utilizator,String> c = new TableColumn<>(name);
        c.setCellValueFactory(v -> new SimpleStringProperty(map.apply(v.getValue())));
        c.setPrefWidth(120);
        userTable.getColumns().add(c);
    }

    private void loadUsersBasic() {
        addCol("ID", u -> u.getId().toString());
        addCol("Username", Utilizator::getUsername);
        addCol("Email", Utilizator::getEmail);

        for (Utilizator u : userService.getAll()) {
            if (!(u instanceof Duck) && !(u instanceof Persoana))
                data.add(u);
        }
        userTable.setItems(data);
    }

    private void loadPersons() {
        addCol("ID", u -> u.getId().toString());
        addCol("Username", Utilizator::getUsername);
        addCol("Email", Utilizator::getEmail);
        addCol("First Name", u -> ((Persoana)u).getFirstName());
        addCol("Last Name", u -> ((Persoana)u).getLastName());
        addCol("Job", u -> ((Persoana)u).getJobTitle());
        addCol("Birth", u -> ((Persoana)u).getBirthDate().toString());
        addCol("Empathy", u -> "" + ((Persoana)u).getEmpathyLevel());

        for (Persoana p : servicePersoana.findAll())
            data.add(p);

        userTable.setItems(data);
    }

    private void loadDucks() {
        addCol("ID", u -> u.getId().toString());
        addCol("Username", Utilizator::getUsername);
        addCol("Email", Utilizator::getEmail);
        addCol("Speed", u -> "" + ((Duck)u).getSpeed());
        addCol("Resistance", u -> "" + ((Duck)u).getResistance());
        addCol("Type", u -> ((Duck)u).getType().toString());

        for (Duck d : serviceDuck.getAllDucks())
            data.add(d);

        userTable.setItems(data);
    }

    @FXML
    private void handleAddDuck() {
        try {
            DuckType type = DuckType.valueOf(duckType.getText().trim());

            Duck d = new FlyingDuck(
                    duckUsername.getText(),
                    duckEmail.getText(),
                    duckPassword.getText(),
                    Double.parseDouble(duckSpeed.getText()),
                    Double.parseDouble(duckResistance.getText()),
                    type
            );
            serviceDuck.addDuck(d);
            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Duck added successfully!");

            clearDuckFields();
            loadUsers();

        } catch (Exception e) {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("ERROR: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddPersoana() {
        try {
            Persoana p = new Persoana(
                    persUsername.getText(),
                    persEmail.getText(),
                    persPassword.getText(),
                    persLastName.getText(),
                    persFirstName.getText(),
                    persJob.getText(),
                    persBirth.getValue(),
                    Integer.parseInt(persEmp.getText())
            );

            servicePersoana.addPersoana(p);

            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Persoana added successfully!");

            clearPersFields();
            loadUsers();

        } catch (Exception e) {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("ERROR: " + e.getMessage());
        }
    }


    @FXML
    private void handleRemoveUser() {
        try {
            Utilizator u = userTable.getSelectionModel().getSelectedItem();
            if (u == null) {
                statusLabel.setText("Select a user.");
                return;
            }

            userService.deleteUser(u.getId());
            statusLabel.setText("User deleted.");
            loadUsers();

        } catch (Exception e) {
            statusLabel.setText("ERROR: "+e.getMessage());
        }
    }

    private void clearDuckFields() {
        duckUsername.clear();
        duckEmail.clear();
        duckPassword.clear();
        duckSpeed.clear();
        duckResistance.clear();
        duckType.clear();
    }

    private void clearPersFields() {
        persUsername.clear();
        persEmail.clear();
        persPassword.clear();
        persFirstName.clear();
        persLastName.clear();
        persJob.clear();
        persEmp.clear();
        persBirth.setValue(null);
    }


}
