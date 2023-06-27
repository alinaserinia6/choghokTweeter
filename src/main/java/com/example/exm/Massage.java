package com.example.exm;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Massage implements Serializable {
    private String text;
    private LocalDateTime time;
    private String username;
//    private ShowMassageController controller;


    public Massage() {
        time = LocalDateTime.MIN;
    }

    public Massage(String username, String text, LocalDateTime time) {
        this.username = username;
        this.text = text;
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getUsername() {
        return username;
    }

    public Pane toPane() throws IOException {
        ShowMassageController controller = new ShowMassageController();
//        this.controller = controller;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showMassage.fxml"));
        fxmlLoader.setController(controller);
        Pane p = fxmlLoader.load();
        controller.build(this);
        return p;
    }

    @Override
    public String toString() {
        return username + ": " + text;
    }
}
