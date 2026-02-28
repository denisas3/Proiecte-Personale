package curs3.io;

import curs3.io.controller.LoginController;
import curs3.io.controller.MainMenuController;
import curs3.io.domain.*;
import curs3.io.domain.validators.*;
import curs3.io.repository.*;
import curs3.io.service.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import static javafx.application.Application.launch;


public class Main extends Application {

    public void start(Stage stage) {
        var url = "jdbc:postgresql://localhost:5432/ducksocialnetwork";
        var username = "postgres";
        var password = "root";

        try {
            var connection = DriverManager.getConnection(url, username, password);

            DuckRepository duckRepo = new DuckRepository(connection);
            RepositoryBD<Long, Persoana> persoanaRepo = new PersoanaRepository(connection);
            RepositoryBD<Long, Utilizator> userRepo = new UserRepository(connection);
            FriendshipRepository friendshipRepo = new FriendshipRepository(connection);
            CardRepository cardRepo = new CardRepository(connection);
            CardDuckRepository cardDuckRepo = new CardDuckRepository(connection);
            EventRepository eventRepo = new EventRepository(connection);
            EventSubscribersRepository eventSubscribersRepo = new EventSubscribersRepository(connection);
            MessageRepository messageRepo = new MessageRepository(connection);
            FriendRequestRepository friendRequestRepository = new FriendRequestRepositoryDB(connection);

            Validator<Utilizator> validator = new UtilizatorValidator();

            Validator<Card<Duck>> validator_card = new CardVaidator();

            Validator<Duck> duckValidator = new DuckValidator();

            Validator<Persoana> persoanaValidator = new PersoanaValidator();

            Validator<Event> validator_event = new EventValidator();

            InMemoryRepository repo = new InMemoryRepository(validator);

            InMemoryRepository repo_card = new InMemoryRepository(validator_card);

            InMemoryRepository repo_event = new InMemoryRepository(validator_event);

            Service service = new Service(repo);

            ServiceCard serviceCard = new ServiceCard(cardRepo, cardDuckRepo);

            ServiceEvent serviceEvent = new ServiceEvent(eventRepo, eventSubscribersRepo);

            ServiceDuck serviceDuck = new ServiceDuck(duckRepo, userRepo, (DuckValidator) duckValidator);

            ServicePersoana servicePersoana = new ServicePersoana(persoanaRepo, userRepo, (PersoanaValidator) persoanaValidator);

            ServiceUser userService = new ServiceUser(userRepo);

            ServiceFriendship serviceFriendship = new ServiceFriendship(friendshipRepo, userRepo);
//
            ServiceMessage serviceMessage = new ServiceMessage(messageRepo);
//            Duck duck = new FlyingDuck("duck23","duck23@gmail.com","duck23",6.7,7.8, DuckType.FLYING);
//
//            serviceDuck.addDuck(duck);

            ServiceFriendRequest serviceFriendRequest = new ServiceFriendRequest(friendRequestRepository, serviceFriendship);

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/login-window.fxml"));
            Scene scene = new Scene(loader.load(), 500, 300);
            LoginController controller = loader.getController();
            controller.setServices(serviceDuck,userService,serviceFriendship,servicePersoana, serviceMessage,serviceFriendRequest);

            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
