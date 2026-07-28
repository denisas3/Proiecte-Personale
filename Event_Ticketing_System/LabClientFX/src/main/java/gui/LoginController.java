//package gui;
//
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//import javafx.stage.WindowEvent;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.example.lab03.domain.Employee;
//import org.example.lab03.services.ILabServices;
//import org.example.lab03.services.LabException;
//
//public class LoginController {
//
//    private ILabServices server;
//    private gui.MainController mainCtrl;
//    private Employee currentEmployee;
//    private Parent mainParent;
//
//    private static final Logger logger = LogManager.getLogger(LoginController.class);
//
//    @FXML
//    private TextField usernameField;
//
//    @FXML
//    private PasswordField passwordField;
//
//    @FXML
//    private Button loginButton;
//
//    public void setServer(ILabServices server) {
//        this.server = server;
//    }
//
//    public void setMainParent(Parent mainParent) {
//        this.mainParent = mainParent;
//    }
//
//    public void setMainController(gui.MainController mainCtrl) {
//        this.mainCtrl = mainCtrl;
//    }
//
//    @FXML
//    public void handleLogin(ActionEvent actionEvent) {
//        String username = usernameField.getText();
//        String password = passwordField.getText();
//
//        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
//            MessageAlert.showErrorMessage(null, "Completeaza username si parola.");
//            return;
//        }
//
//        loginButton.setDisable(true);
//
//        Thread thread = new Thread(() -> {
//            try {
//                Employee employee = new Employee(username, password, "");
//
//                service.login(employee, mainController);
//
//                Platform.runLater(() -> {
//                    try {
//                        currentEmployee = employee;
//                        mainController.setService(service, currentEmployee);
//                        mainStage.setScene(mainScene);
//                    } finally {
//                        loginButton.setDisable(false);
//                    }
//                });
//
//            } catch (Exception e) {
//                Platform.runLater(() -> {
//                    loginButton.setDisable(false);
//                    MessageAlert.showErrorMessage(null, e.getMessage());
//                });
//            }
//        });
//
//        thread.setDaemon(true);
//        thread.start();
//    }
//
////    @FXML
////    public void pressLogin(ActionEvent actionEvent) {
////        String username = usernameField.getText();
////        String password = passwordField.getText();
////
////        currentEmployee = new Employee(username, password, "");
////
////        try {
////            server.login(currentEmployee, mainCtrl);
////
////            Stage stage = new Stage();
////            stage.setTitle("Aplicatie bilete - " + username);
////            stage.setScene(new Scene(mainParent));
////
////            stage.setOnCloseRequest((WindowEvent event) -> {
////                mainCtrl.logout();
////                logger.debug("Closing application");
////                System.exit(0);
////            });
////
////            mainCtrl.setServer(server);
////            mainCtrl.setEmployee(currentEmployee);
////            mainCtrl.initData();
////
////            stage.show();
////            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
////
////        } catch (LabException e) {
////            Alert alert = new Alert(Alert.AlertType.ERROR);
////            alert.setTitle("Autentificare");
////            alert.setHeaderText("Autentificare esuata");
////            alert.setContentText("Username sau parola gresita.");
////            alert.showAndWait();
////        }
////    }
//
//    @FXML
//    public void pressCancel(ActionEvent actionEvent) {
//        System.exit(0);
//    }
//}

package gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.lab03.domain.Employee;
import org.example.lab03.services.ILabServices;

public class LoginController {

    private ILabServices server;
    private MainController mainCtrl;
    private Employee currentEmployee;
    private Parent mainParent;

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    public void setServer(ILabServices server) {
        this.server = server;
    }

    public void setMainParent(Parent mainParent) {
        this.mainParent = mainParent;
    }

    public void setMainController(MainController mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @FXML
    public void handleLogin(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare");
            alert.setHeaderText(null);
            alert.setContentText("Completeaza Username si parola");
            alert.showAndWait();
            return;
        }

        loginButton.setDisable(true);

        Thread thread = new Thread(() -> {
            try {
                currentEmployee = new Employee(username, password, "");

                logger.info("Trying login for user {}", username);
                server.login(currentEmployee, mainCtrl);

                Platform.runLater(() -> {
                    try {
                        Stage stage = new Stage();
                        stage.setTitle("Aplicatie bilete - " + username);
                        stage.setScene(new Scene(mainParent));

                        stage.setOnCloseRequest((WindowEvent event) -> {
                            mainCtrl.logout();
                            logger.debug("Closing application");
                            System.exit(0);
                        });

                        mainCtrl.setServer(server);
                        mainCtrl.setEmployee(currentEmployee);
                        mainCtrl.initData();

                        stage.show();

                        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                    } catch (Exception e) {
                        logger.error("Error opening main window", e);
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Eroare");
                        alert.setHeaderText(null);
                        alert.setContentText("Eroare la deschiderea ferestrei principale");
                        alert.showAndWait();
                    } finally {
                        loginButton.setDisable(false);
                    }
                });

            } catch (Exception e) {
                logger.error("Login failed", e);
                Platform.runLater(() -> {
                    loginButton.setDisable(false);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setHeaderText(null);
                    alert.setContentText("Username sau parola gfretita");
                    alert.showAndWait();
                });
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    public void pressCancel(ActionEvent actionEvent) {
        System.exit(0);
    }
}