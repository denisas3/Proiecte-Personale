package curs3.io.controller;

import curs3.io.domain.Message;
import curs3.io.domain.Utilizator;
import curs3.io.service.ServiceMessage;
import curs3.io.service.ServiceUser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;

import java.time.LocalDate;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ChatController implements Observer {

    @FXML private Label chatHeader;
    @FXML private ListView<Pane> messageList;
    @FXML private TextField messageBox;
    @FXML private VBox replyBox;
    @FXML private Label replyLabel;

    private Message replyTo = null;
    private ServiceMessage msgService;
    private ServiceUser userService;

    private Utilizator currentUser;
    private Utilizator targetUser;

    private ObservableList<Pane> messages = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        messageList.setOnMouseClicked(e -> {
            Pane selected = messageList.getSelectionModel().getSelectedItem();
            if (selected == null) return;

            Message msg = (Message) selected.getUserData();
            if (msg == null) return;

            replyTo = msg;
            replyLabel.setText("Reply to: " + msg.getMessage());
            replyBox.setVisible(true);
            replyBox.setManaged(true);
        });
    }


    public void setServices(ServiceMessage m, ServiceUser u,
                            Utilizator current, Utilizator target) {

        msgService = m;
        userService = u;
        currentUser = current;
        targetUser = target;

        chatHeader.setText("Chat with " + targetUser.getUsername());

        msgService.addObserver(this);

        loadMessages();
    }

    private Pane createBubble(Message msg) {
        Label label = new Label(msg.getMessage());
        label.setWrapText(true);
        label.setMaxWidth(280);

        Label time = new Label(msg.getData().toLocalTime().toString().substring(0,5));
        time.setStyle("-fx-text-fill: gray; -fx-font-size: 10px;");

        VBox content = new VBox();
        content.setSpacing(3);

        if (msg.getReply() != null) {
            Label replied = new Label(  msg.getReply().getMessage());
            replied.setWrapText(true);
            replied.setMaxWidth(260);
            replied.setStyle(
                    "-fx-font-size: 11px;" +
                            "-fx-text-fill: gray;" +
                            "-fx-background-color: #eeeeee;" +
                            "-fx-padding: 6;" +
                            "-fx-background-radius: 8;"
            );
            content.getChildren().add(replied);
        }

        content.getChildren().addAll(label, time);

        HBox bubble = new HBox(content);
        bubble.setMaxWidth(Double.MAX_VALUE);
        bubble.setPadding(new Insets(5));

        boolean sentByMe = msg.getFrom().getId().equals(currentUser.getId());

        if (sentByMe) {
            bubble.setAlignment(Pos.CENTER_RIGHT);
            label.setStyle(
                    "-fx-background-color: #ffb3c6;" +
                            "-fx-background-radius: 14;" +
                            "-fx-padding: 10;"
            );
        } else {
            bubble.setAlignment(Pos.CENTER_LEFT);
            label.setStyle(
                    "-fx-background-color: #ffffff;" +
                            "-fx-background-radius: 14;" +
                            "-fx-padding: 10;" +
                            "-fx-border-color: #d9d9d9;" +
                            "-fx-border-radius: 14;"
            );
        }

        bubble.setUserData(msg);

        return bubble;
    }

    private void loadMessages() {
        messages.clear();

        List<Message> chat = msgService.getChat(currentUser, targetUser);

        LocalDate lastDate = null;

        for (Message msg : chat) {

            LocalDate msgDate = msg.getData().toLocalDate();

            if (lastDate == null || !msgDate.equals(lastDate)) {
                messages.add(createDateSeparator(msgDate));
                lastDate = msgDate;
            }

            messages.add(createBubble(msg));
        }

        messageList.setItems(messages);
        messageList.scrollTo(messages.size() - 1);
    }

    private Pane createDateSeparator(LocalDate date) {
        Label label = new Label(date.toString());
        label.setStyle(
                "-fx-text-fill: gray;" +
                        "-fx-font-size: 12px;" +
                        "-fx-padding: 4;"
        );

        HBox box = new HBox(label);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(5, 0, 5, 0));

        return box;
    }

    @FXML
    private void handleSend() {
        String text = messageBox.getText();
        if (text.isEmpty()) return;

        msgService.sendMessage(currentUser, targetUser, text, replyTo);

        replyTo = null;
        replyBox.setVisible(false);
        replyBox.setManaged(false);

        messageBox.clear();
    }


    @Override
    public void update(Observable o, Object arg) {
        Message msg = (Message) arg;

        boolean relevant =
                 (msg.getFrom().getId().equals(currentUser.getId()) &&
                        msg.getTo().getId().equals(targetUser.getId()))
                        ||
                        (msg.getFrom().getId().equals(targetUser.getId()) &&
                                msg.getTo().getId().equals(currentUser.getId()));

        if (relevant) {
            messages.add(createBubble(msg));
            messageList.scrollTo(messages.size() - 1);
        }
    }
}
