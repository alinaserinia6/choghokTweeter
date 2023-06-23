package com.example.exm;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDateTime;

public class ShowUserController {
    @FXML
    private Label name;
    @FXML
    private Label username;
    @FXML
    private Label bio;
    @FXML
    private JFXButton follow;
    private boolean isFollow;
    private Following following;

    public void build(String name, String username, String bio, Following following) {
        this.name.setText(name);
        this.username.setText(username);
        this.bio.setText(bio);
        this.following = following;
        isFollow = following != null && following.isFollowing();
        if (isFollow) {
            follow.setText("دنبال شده");
        } else {
            follow.setText("دنبال کردن");
        }
    }

    @FXML
    void followButton(ActionEvent e) throws IOException { // TODO send to server
        Client.out.writeObject(new Request(RM.FOLLOW_REQUEST, username.getText()));
        if (following == null) {
            following = new Following(username.getText(), LocalDateTime.MIN);
            Client.user.following.put(username.getText(), following);
        } else {
            following.follow();
        }
        if (isFollow) {
            follow.setText("دنبال کردن");
        } else {
            follow.setText("دنبال شده");
        }
        isFollow = !isFollow;
    }

}