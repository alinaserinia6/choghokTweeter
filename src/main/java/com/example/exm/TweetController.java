package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private ImageView likepic;

    @FXML
    void like(MouseEvent e) {
        
    }


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

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
