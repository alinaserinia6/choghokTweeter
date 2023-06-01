package com.example.exm;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Tweet {
    private String text;
    private LocalDateTime dt;
    private User user;
    private ArrayList<User> likes;
    private ArrayList<Comment> comments;
    private ArrayList<User> retweet;
    private TweetController controller;

    public Tweet(String text, User user) {
        this.text = text;
        this.user = user;
        dt = LocalDateTime.now();
        likes = new ArrayList<User>();
        comments = new ArrayList<Comment>();
    }

    public Pane tweetToPane() throws IOException {
        TweetController controller = new TweetController();
        this.controller = controller;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showTweet.fxml"));
        Pane p = fxmlLoader.load();
        fxmlLoader.setController(controller);
        controller.setName(user.getFirstName() + " " + user.getLastName() + " " + user.getUsername());
        controller.setText(text);
        controller.setLike(likes.size());
        controller.setComment(comments.size());
        controller.setRetweet(retweet.size());
        controller.setAvatar(user.getAvatar());
        return p;
    }
}