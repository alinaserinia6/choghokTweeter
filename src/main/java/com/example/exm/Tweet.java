package com.example.exm;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Tweet implements Serializable {
    private int id;
    private String avatar;
    private String name;
    private String username;
    private String text;
    protected LocalDateTime dt;
    protected ArrayList<String> likes;
    public ArrayList<Comment> comments;
    protected ArrayList<String> retweet;
    private transient TweetController controller;

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
        avatar = "Plike.png";
    }

    public void fitTextArea(JFXTextArea txt) {
        txt.setEditable(false);
        txt.setMaxWidth(400);
        txt.setWrapText(true);
        Text t = new Text();
        t.setWrappingWidth(txt.getWidth() - 10);
        t.setText(text);
        double height = t.getLayoutBounds().getHeight() * 1.1;
        txt.setPrefHeight(height + 20);
        txt.setText(text);
    }

    public void update(String avatar, String name) { // TODO UPDATE WHEN EDIT PROFILE
        this.avatar = avatar;
        this.name = name;
    }

    public GridPane toShow() throws IOException {
        setController();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showTweet.fxml"));
        fxmlLoader.setController(controller);
        GridPane p = fxmlLoader.load();
        JFXTextArea txt = new JFXTextArea();
        fitTextArea(txt);
        txt.setCursor(Cursor.HAND);
        txt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
//                TimeLineController.stop();
                try {
                    HelloApplication.ChangePage(e, "aFocusTweet", controller);
                    controller.buildScroll();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        p.add(txt, 1, 1);
        controller.build(this, name, text, likes.size(), comments.size(), retweet.size(), avatar, id, username);
        controller.run();
        return p;
    }
    public GridPane toFocus() throws IOException {
        setController();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("focusTweet.fxml"));
        fxmlLoader.setController(controller);
        GridPane p = fxmlLoader.load();
        JFXTextArea txt = new JFXTextArea();
        fitTextArea(txt);
        p.add(txt, 0, 1); // TODO QUOTE
        controller.focusBuild(this, name, username, avatar, dt, retweet.size(),retweet.size(), likes.size(), id);
        controller.run();
        return p;
    }

    public void setController() {
        if (controller == null) {
            TweetController t = Client.getTweetController.get(id);
            if (t == null) {
                controller = new TweetController();
                Client.getTweetController.put(id, controller);
            } else {
                controller = t;
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Image getAvatar() {
        return new Image(String.valueOf(getClass().getResource(avatar)));
    }

    public void setAvatar(String avatar) {
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