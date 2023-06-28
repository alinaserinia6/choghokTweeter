package com.example.exm;

import com.jfoenix.controls.JFXTextArea;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Tweet implements Serializable {
    private int id;
    private Image avatar;
    private String name;
    private String username;
    private String text;
    private LocalDateTime dt;
    private ArrayList<String> likes;
    public ArrayList<Comment> comments;
    private ArrayList<String> retweet;
    private transient TweetController controller;
    private double oldHeight = 0;


    public Tweet() {
        id = -1;
    }

    public Tweet(String text, String username) {
        this.text = text;
        this.username = username;
        dt = LocalDateTime.now();
        likes = new ArrayList<>();
        comments = new ArrayList<>();
        retweet = new ArrayList<>();
    }

    public void update(Image avatar, String name) { // TODO UPDATE WHEN EDIT PROFILE
        this.avatar = avatar;
        this.name = name;
        // username is constant
    }

    public GridPane tweetToPane() throws IOException {
        if (controller == null) {
            TweetController t = Client.getTweetController.get(id);
            if (t == null) {
                controller = new TweetController();
                Client.getTweetController.put(id, controller);
            } else {
                controller = t;
            }
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showTweet.fxml"));
        fxmlLoader.setController(controller);
        GridPane p = fxmlLoader.load();
        controller.build(name, text, likes.size(), comments.size(), retweet.size(), avatar, id, username);
        controller.run();
        return p;
    }
    public GridPane toFocus() throws IOException {
        if (controller == null) {
            TweetController t = Client.getTweetController.get(id);
            if (t == null) {
                controller = new TweetController();
                Client.getTweetController.put(id, controller);
            } else {
                controller = t;
            }
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("focusTweet.fxml"));
        fxmlLoader.setController(controller);
        GridPane p = fxmlLoader.load();
        final JFXTextArea txt = new JFXTextArea();
        txt.setMaxWidth(400);
        txt.setWrapText(true);
        Text t = new Text();
        t.setWrappingWidth(txt.getWidth() - 10);
        t.setText(text);
        double height = t.getLayoutBounds().getHeight();
        txt.setPrefHeight(height + 20);
//        t.textProperty().bind(txt.textProperty());
//        t.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
//            @Override
//            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
//                if (oldHeight != newValue.getHeight()) {
//                    System.out.println("newValue = " + newValue.getHeight());
//                    oldHeight = newValue.getHeight();
//                    txt.setMinHeight(t.getLayoutBounds().getHeight() + 20); // +20 is for paddings
//                }
//            }
//        });
        txt.setText(text);
        p.add(txt, 0, 1); // TODO QUOTE
        controller.focusBuild(name, username, avatar, dt, retweet.size(),retweet.size(), likes.size(), id);
        controller.run();
        return p;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDt() {
        return dt;
    }

    public void setDt(LocalDateTime dt) {
        this.dt = dt;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<String> getRetweet() {
        return retweet;
    }

    public void setRetweet(ArrayList<String> retweet) {
        this.retweet = retweet;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public TweetController getController() {
        return controller;
    }

    public void setController(TweetController controller) {
        this.controller = controller;
    }

    @Override
    public String toString() {
        return username + ": " + text + " " + likes.size();
    }
}