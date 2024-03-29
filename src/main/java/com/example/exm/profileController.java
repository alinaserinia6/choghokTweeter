package com.example.exm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class profileController {
    @FXML
    private Label name;
    @FXML
    private Label username;
    @FXML
    private Label joinDate;
    @FXML
    private Label follower;
    @FXML
    private Label following;
    @FXML
    private TextArea bio;
    @FXML
    private ImageView avatar;
    @FXML
    private ImageView header;

    public void initialize() {
        name.setText(Client.user.getFirstName() + " " + Client.user.getLastName());
        username.setText("@"+ Client.user.getUsername());
        bio.setText(Client.user.getBio());
        follower.setText(String.valueOf(Client.user.followers.size()));
        following.setText(String.valueOf(Client.user.following.size()));
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy MMM");
        joinDate.setText(Client.user.getJoinDate().format(format));
        avatar.setImage(Client.user.getAvatar());
        header.setImage(Client.user.getHeader());
    }

    @FXML
    void backtotimeline(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a5");
    }

    @FXML
    void searchButton(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a8");
    }

    @FXML
    void editProfileButton(ActionEvent e) throws IOException {
        HelloApplication.ChangePage(e, "aEditProfile");
    }

}
