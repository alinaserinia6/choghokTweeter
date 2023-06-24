package com.example.exm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AddTweetController {
    @FXML
    private TextArea text;
    @FXML
    private Button postButton;

    @FXML
    void post(ActionEvent e) throws IOException {
        if (text.getText().isEmpty()) {
            return;
        }
        Tweet tweet = new Tweet(text.getText(), Client.user.getUsername());
        Client.out.writeObject(new Request(RM.ADD_TWEET, tweet));
        System.out.println("request send");
        int id = (Integer) Client.getObject();
        System.out.println("id get");
        tweet.setId(id);
        Client.user.tweets.put(id, tweet);
        System.out.println("id set");
        Client.timeline.getChildren().add(0, tweet.tweetToPane());
        System.out.println("id pane");
        HelloApplication.ChangePage(e, "a5");
    }

    @FXML
    void back(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a5");
    }

    @FXML
    void postButtonAppear() {
        if (text.getText().isEmpty()) {
            postButton.setOpacity(0.5);
        } else {
            postButton.setOpacity(1);
        }
    }
}
