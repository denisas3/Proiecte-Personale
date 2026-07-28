package org.example.lab03.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.lab03.Employee;
import org.example.lab03.services.EmployeeService;
import org.example.lab03.services.ShowService;
import org.example.lab03.services.TicketService;

import java.io.IOException;

public class LoginController {
   private EmployeeService employeeService;
   private ShowService showService;
   private TicketService ticketService;

   @FXML
   private TextField usernameField;

   @FXML
   private TextField passwordField;

   public void setServices(EmployeeService employeeService, ShowService showService, TicketService ticketService) {
       this.employeeService = employeeService;
       this.showService = showService;
       this.ticketService = ticketService;
   }

   @FXML
   public void handleLogin(){
       String username = usernameField.getText();
       String password = passwordField.getText();

       try{
           Employee employee = employeeService.login(username,password);

           FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lab03/main-view.fxml"));

           Scene scene = usernameField.getScene();
           scene.setRoot(loader.load());

           MainController mainController = loader.getController();
           mainController.setServices(showService,ticketService,employee);
       }catch (IOException e) {
           showError("Nu s-a putut incarca fereastra principala.");
       }catch (Exception e){
           showError(e.getMessage());
       }
   }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Eroare");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
