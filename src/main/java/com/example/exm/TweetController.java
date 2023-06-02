package com.example.exm;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



import java.io.IOException;

public class TweetController {
    @FXML
    private TextField name;
    @FXML
    private TextArea text;
    @FXML
    private Label like;
    @FXML
    private Label comment;
    @FXML
    private Label retweet;
    @FXML
    private ImageView avatar;
    @FXML
    private ImageView likepic;


    public void setName(String name) {
        this.name.setText(name);
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setLike(int like) {
        this.like.setText(Integer.toString(like));
    }

    public void setComment(int comment) {
        this.comment.setText(Integer.toString(comment));
    }

    public void setRetweet(int retweet) {
        this.retweet.setText(Integer.toString(retweet));
    }

    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }
}
