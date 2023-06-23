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
    private ArrayList<String> likes;
    private ArrayList<Comment> comments;
    private ArrayList<User> retweet;
    private TweetController controller;

    public Tweet(String text, String username) {
        this.text = text;
        this.username = username;
        dt = LocalDateTime.now();
        likes = new ArrayList<String>();
        comments = new ArrayList<Comment>();
        retweet = new ArrayList<User>();
    }

    public Pane tweetToPane() throws IOException {
        Client.out.writeObject(new Request(RM.GET_NAME, username));
        String name = (String) Client.getObject();
        Client.out.writeObject(new Request(RM.GET_AVATAR, username));
        Avatar avatar = (Avatar) Client.getObject();
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

    @Override
    public String toString() {
        return username + ": " + text;
    }
}