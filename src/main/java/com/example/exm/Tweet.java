package com.example.exm;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    public Tweet(String text, User user) {
        this.text = text;
        this.user = user;
        dt = LocalDateTime.now();
        likes = new ArrayList<User>();
        comments = new ArrayList<Comment>();
    }

    public Pane tweetToPane() throws IOException {
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("showTweet.fxml"));
        Pane p = new Pane(root);

        return p;
    }
}
