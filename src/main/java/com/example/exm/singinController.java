package com.example.exm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
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
        Client.out.writeObject(new Request(RM.GET_USER, username.getText()));
        ArrayList<ShowUser> userList = (ArrayList<ShowUser>) Client.getObject();
        for (ShowUser u : userList) {
            System.out.println(u.getUsername());
            Client.contacts.getChildren().add(u.usertoPane(Client.user.following.get(u.getUsername())));
        }
        HelloApplication.ChangePage(e, "a5");
    }
    public void backk(ActionEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a2");
    }
}
