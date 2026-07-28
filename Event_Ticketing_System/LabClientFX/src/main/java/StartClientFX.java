import gui.LoginController;
import gui.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jsonprotocol.ServicesJsonProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.lab03.services.ILabServices;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class StartClientFX extends Application {
    private Stage primaryStage;

    private static int defaultChatPort = 55556;
    private static String defaultServer = "localhost";

    private static Logger logger = LogManager.getLogger(StartClientFX.class);

    public void start(Stage primaryStage) throws Exception {
        logger.debug("In start");

        Properties clientProps = new Properties();
        try {
            clientProps.load(StartClientFX.class.getResourceAsStream("/client.properties"));
            logger.info("Client properties set {} ", clientProps);
            clientProps.list(System.out);
        } catch (IOException e) {
            logger.error("Cannot find client.properties " + e);
            return;
        }

        String serverIP = clientProps.getProperty("server.host", defaultServer);

        int serverPort = defaultChatPort;
        try {
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            logger.error("Wrong port number " + ex.getMessage());
            logger.debug("Using default port: " + defaultChatPort);
        }

        logger.info("Using server IP " + serverIP);
        logger.info("Using server port " + serverPort);

        ILabServices server = new ServicesJsonProxy(serverIP, serverPort);

        // LOGIN
        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("LoginW.fxml"));
        Parent root = loader.load();

        LoginController ctrl = loader.getController();
        ctrl.setServer(server);

        // MAIN
        FXMLLoader cloader = new FXMLLoader(
                getClass().getClassLoader().getResource("MainW.fxml"));
        Parent croot = cloader.load();

        MainController mainCtrl = cloader.getController();
        mainCtrl.setServer(server);

        // legaturi
        ctrl.setMainController(mainCtrl);
        ctrl.setMainParent(croot);

        primaryStage.setTitle("MPP chat");
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.show();
    }

}
