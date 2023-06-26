package com.example.exm;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class singinController {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label error;

    @FXML
    public void singInbuttonAction(ActionEvent e) throws IOException {
        if (username.getText().isEmpty()) {
            error.setText("Username must not be empty");
            error.setVisible(true);
            return;
        }
        if (password.getText().isEmpty()) {
            error.setText("The password must not be empty");
            error.setVisible(true);
            return;
        }
        Request r = new Request(RM.CHECK_PASS ,username.getText(), password.getText());
        Client.out.writeObject(r);
        boolean b = (boolean) Client.getObject();
        if (!b) {
            error.setText("The username or password is incorrect");
            error.setVisible(true);
            return;
        }
        System.out.println("send GET_USER");
        Client.out.writeObject(new Request(RM.GET_USER, username.getText()));
        Client.user = (User) Client.getObject();
        System.out.println("send DISCOVER_USERS");
        Client.out.writeObject(new Request(RM.DISCOVER_USERS, Client.LAST_USER_SEEN));
        System.out.println("GET LAST_USER_SEEN");
        Client.LAST_USER_SEEN = (LocalDateTime) Client.getObject();
        System.out.println("get UserList");
        ArrayList<User> userList = (ArrayList<User>) Client.getObject();
        for (User u : userList) {
            System.out.println(u.getUsername());
            Client.contacts.getChildren().add(u.usertoPane(Client.user.following.get(u.getUsername())));
        }
        // HelloApplication.ChangePage(e, "a5"); TODO see different
        Platform.runLater(() -> {
            try {
                HelloApplication.ChangePage(e, "a5");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    public void backk(ActionEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a2");
    }
}
