package com.example.exm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddTweetController {
    @FXML
    private TextField text;

    @FXML
    void post(ActionEvent e) throws IOException {
        Tweet tweet = new Tweet(text.getText(), Client.user);


        HelloApplication.ChangePage(e, "a5");
    }

    @FXML
    void back(ActionEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a5");
    }
}
