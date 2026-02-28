package curs3.io.ui;

import curs3.io.controller.ProfileController;
import curs3.io.domain.Utilizator;
import curs3.io.service.ServiceFriendRequest;
import curs3.io.service.ServiceFriendship;
import curs3.io.service.ServiceMessage;
import curs3.io.service.ServiceUser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class UserProfilePage implements Page {

    private final Parent view;
    private final Utilizator profileUser;

    public UserProfilePage(ServiceUser userService,
                           ServiceFriendship friendshipService,
                           ServiceFriendRequest requestService,
                           ServiceMessage messageService,
                           Utilizator loggedUser,
                           Utilizator profileUser) {
        try {
            this.profileUser = profileUser;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/profile-page.fxml"));
            this.view = loader.load();

            ProfileController ctrl = loader.getController();
            ctrl.setServices(userService, friendshipService, requestService, messageService, loggedUser, profileUser);

        } catch (Exception e) {
            throw new RuntimeException("Cannot load profile page: " + e.getMessage(), e);
        }
    }

    @Override
    public String title() {
        return "Profile: " + profileUser.getUsername();
    }

    @Override
    public Parent root() {
        return view;
    }
}
