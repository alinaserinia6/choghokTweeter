package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Tweet implements Serializable {
    private int id;
    private String text;
    private LocalDateTime dt;
    private String username;
    public ArrayList<String> likes;
    public ArrayList<Comment> comments;
    public ArrayList<User> retweet;
    private Avatar avatar;
    private String name;
    private TweetController controller;

    public Tweet() {
        id = -1;
    }
    public Tweet(String text, String username) {
        this.text = text;
        this.username = username;
        dt = LocalDateTime.now();
        likes = new ArrayList<String>();
        comments = new ArrayList<Comment>();
        retweet = new ArrayList<User>();
    }

    public Pane tweetToPane() throws IOException {
        TweetController controller = new TweetController();
        this.controller = controller;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showTweet.fxml"));
        fxmlLoader.setController(controller);
        Pane p = fxmlLoader.load();
        controller.build(name, text, likes.size(), comments.size(), retweet.size(), avatar, this);
        return p;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getDt() {
        return dt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return username + ": " + text;
    }
}