package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class TweetController {
    @FXML
    private TextField name;
    @FXML
    private JFXTextArea text;
    @FXML
    private Label like;
    @FXML
    private Label comment;
    @FXML
    private Label retweet;
    @FXML
    private Avatar avatar;
    @FXML
    private ImageView likePicture;

    private Tweet tweet;
    private boolean isLiked = false;

    @FXML
    void like(MouseEvent e) throws IOException {
        if (isLiked) {
            Client.out.writeObject(new Request(RM.DISLIKE_TWEET, tweet.getUsername(), tweet.getId()));

        } else {
            Client.out.writeObject(new Request(RM.LIKE_TWEET, tweet.getUsername(), tweet.getId()));

        }
        isLiked = !isLiked;
    }

    public void build(String name, String text, int like, int comment, int retweet, Avatar avatar, Tweet tweet) {
        this.name.setText(name);
        this.text.setText(text);
        this.like.setText(String.valueOf(like));
        this.comment.setText(String.valueOf(comment));
        this.retweet.setText(String.valueOf(retweet));
        this.avatar = avatar;
        this.tweet = tweet;
    }
}
