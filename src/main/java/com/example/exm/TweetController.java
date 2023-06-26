package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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

    private Image liked = new Image(getClass().getResource("Pliked.png").toString());
    private Image disliked = new Image(getClass().getResource("Plike.png").toString());


    private Tweet tweet;
    private boolean isLiked;

    @FXML
    void like(MouseEvent e) throws IOException {
        Client.out.writeObject(new Request(RM.LIKE_TWEET, tweet.getUsername(), tweet.getId()));
        if (isLiked) { // dislike
            Client.user.likes.remove(tweet.getId());
            likePicture.setImage(disliked);
            likeChange(-1);
        } else { // like
            Client.user.likes.put(tweet.getId(), tweet);
            likePicture.setImage(liked);
            likeChange(1);
        }
        isLiked = !isLiked;
    }

    public void build(String name, String text, int like, int comment, int retweet, Image avatar, Tweet tweet) {
        this.name.setText(name);
        this.text.setText(text);
        this.like.setText(String.valueOf(like));
        this.comment.setText(String.valueOf(comment));
        this.retweet.setText(String.valueOf(retweet));
        this.avatar.setImage(avatar);
        this.tweet = tweet;
        if (Client.user.likes.containsKey(tweet.getId())) {
            likePicture.setImage(liked);
            isLiked = true;
        }
        else {
            likePicture.setImage(disliked);
            isLiked = false;
        }
    }

    void likeChange(int x) {
        int y = Integer.parseInt(like.getText());
        y += x;
        like.setText(String.valueOf(y));
    }
}
