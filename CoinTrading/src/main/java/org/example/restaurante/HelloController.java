package org.example.restaurante;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label orderText;

    @FXML
    protected void onOrderButtonClick() {
        orderText.setText("Your order has been placed!");
    }
}
