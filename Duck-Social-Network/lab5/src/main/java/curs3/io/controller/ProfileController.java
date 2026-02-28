package curs3.io.controller;

import curs3.io.domain.Utilizator;
import curs3.io.service.ServiceFriendRequest;
import curs3.io.service.ServiceFriendship;
import curs3.io.service.ServiceMessage;
import curs3.io.service.ServiceUser;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class ProfileController {

    private ServiceUser userService;
    private ServiceFriendship friendshipService;
    private ServiceFriendRequest requestService;
    private ServiceMessage messageService;

    private Utilizator loggedUser;
    private Utilizator profileUser;

    @FXML private Label usernameLabel;
    @FXML private Label emailLabel;

    @FXML private Label friendsCountLabel;
    @FXML private Label pendingCountLabel;

    @FXML private ListView<String> friendsList;
    @FXML private Button actionBtn;

    @FXML
    private ImageView profileImage;

    public void setServices(ServiceUser userService,
                            ServiceFriendship friendshipService,
                            ServiceFriendRequest requestService,
                            ServiceMessage messageService,
                            Utilizator loggedUser,
                            Utilizator profileUser) {
        this.userService = userService;
        this.friendshipService = friendshipService;
        this.requestService = requestService;
        this.messageService = messageService;
        this.loggedUser = loggedUser;
        this.profileUser = profileUser;

        render();
    }

    private void render() {
        usernameLabel.setText(profileUser.getUsername());
        emailLabel.setText(profileUser.getEmail());

        loadProfileImage();

        List<Utilizator> friends = friendshipService.getFriendsOf(profileUser.getId());

        friendsList.setItems(FXCollections.observableArrayList(
                friends.stream()
                        .map(u -> u.getId() + " | " + u.getUsername())
                        .sorted()
                        .toList()
        ));

        friendsCountLabel.setText(String.valueOf(friends.stream().count()));

        long pending = requestService.getPendingFor(profileUser.getId()).stream().count();
        pendingCountLabel.setText(String.valueOf(pending));

        actionBtn.setDisable(loggedUser.getId().equals(profileUser.getId()));
    }

    @FXML
    private void handleAction() {
        try {
            requestService.sendRequest(loggedUser.getId(), profileUser.getId());
            render();
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
            a.showAndWait();
        }
    }

    private void loadProfileImage() {
        String path = "/images/user_" + profileUser.getId() + ".png";

        Image img;
        try {
            img = new Image(getClass().getResourceAsStream(path));
        } catch (Exception e) {
            img = new Image(getClass().getResourceAsStream("/images/default.png"));
        }

        profileImage.setImage(img);
    }
}
