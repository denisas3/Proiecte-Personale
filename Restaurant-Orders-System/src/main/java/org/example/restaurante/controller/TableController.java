package org.example.restaurante.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.restaurante.domain.MenuItem;
import org.example.restaurante.domain.Tabel;
import org.example.restaurante.service.Service;
import org.example.restaurante.utils.observer.Observer;
import org.example.restaurante.utils.events.EntityChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class TableController implements Observer<EntityChangeEvent> {
    @FXML private TableView<MenuItem> tableView;
    @FXML private TableColumn<MenuItem, Integer> idColumn;
    @FXML private TableColumn<MenuItem, String> itemColumn;
    @FXML private TableColumn<MenuItem, Double> priceColumn;
    @FXML private TableColumn<MenuItem, String> currencyColumn;
    @FXML private TableColumn<MenuItem, Boolean> selectColumn;

    @FXML private TableView<MenuItem> tableView1;
    @FXML private TableColumn<MenuItem, Integer> idColumn1;
    @FXML private TableColumn<MenuItem, String> itemColumn1;
    @FXML private TableColumn<MenuItem, Double> priceColumn1;
    @FXML private TableColumn<MenuItem, String> currencyColumn1;
    @FXML private TableColumn<MenuItem, Boolean> selectColumn1;

    @FXML private TableView<MenuItem> tableView2;
    @FXML private TableColumn<MenuItem, Integer> idColumn2;
    @FXML private TableColumn<MenuItem, String> itemColumn2;
    @FXML private TableColumn<MenuItem, Double> priceColumn2;
    @FXML private TableColumn<MenuItem, String> currencyColumn2;
    @FXML private TableColumn<MenuItem, Boolean> selectColumn2;

    @FXML
    private Label errorLabel;

    @FXML
    public Label categoryLable;
    @FXML
    public Label categoryLable1;
    @FXML
    public Label categoryLable2;

    Service service;
    Tabel currentTable;

    ObservableList<MenuItem> modelMenuItem = FXCollections.observableArrayList();
    ObservableList<MenuItem> modelMenuItem1 = FXCollections.observableArrayList();
    ObservableList<MenuItem> modelMenuItem2 = FXCollections.observableArrayList();

    public void initModel(){

        modelMenuItem.setAll(service.getMenuItemsByCategory("Antreuri"));
        modelMenuItem1.setAll(service.getMenuItemsByCategory("Fel Principal"));
        modelMenuItem2.setAll(service.getMenuItemsByCategory("Desert"));
    }

    @FXML
    private void initialize() {
        tableView.setEditable(true);
        tableView1.setEditable(true);
        tableView2.setEditable(true);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));
        tableView.setItems(modelMenuItem);

        /// PENTRU CHECK BOX IN TABEL

        selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn));
        selectColumn.setEditable(true);

        idColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemColumn1.setCellValueFactory(new PropertyValueFactory<>("item"));
        priceColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));
        currencyColumn1.setCellValueFactory(new PropertyValueFactory<>("currency"));
        tableView1.setItems(modelMenuItem1);

        /// PENTRU CHECK BOX IN TABEL

        selectColumn1.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectColumn1.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn1));
        selectColumn1.setEditable(true);

        idColumn2.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemColumn2.setCellValueFactory(new PropertyValueFactory<>("item"));
        priceColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));
        currencyColumn2.setCellValueFactory(new PropertyValueFactory<>("currency"));
        tableView2.setItems(modelMenuItem2);

        /// PENTRU CHECK BOX IN TABEL

        selectColumn2.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectColumn2.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn2));
        selectColumn2.setEditable(true);
    }

    public void setService(Service service, Tabel currentTable) {
        this.service = service;
        this.currentTable = currentTable;

        categoryLable.setText("Antreuri");
        categoryLable1.setText("Fel Principal");
        categoryLable2.setText("Desert");

        service.addObserver(this);
        initModel();
    }


    @Override
    public void update(EntityChangeEvent event) {
        initModel();
    }


    /// EXTRAG OBIECTELE CARE AU FOST SELECTATE
    public List<MenuItem> getSelected() {
        List<MenuItem> rez = new ArrayList<>();
        for (MenuItem m : modelMenuItem) if (m.isSelected()) rez.add(m);
        for (MenuItem m : modelMenuItem1) if (m.isSelected()) rez.add(m);
        for (MenuItem m : modelMenuItem2) if (m.isSelected()) rez.add(m);
        return rez;
    }


    public void handleOrder() {
        List<MenuItem> menuItems = getSelected();
        if (menuItems.isEmpty()) {
            showError("Please select at least one item");
            return;
        }

        service.placeOrder(currentTable, menuItems);
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}
