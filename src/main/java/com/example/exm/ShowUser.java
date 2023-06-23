package com.example.exm;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.Serializable;

public class ShowUser implements Serializable {
    private String name;
    private String username;
    private String bio;

    public ShowUser(String name, String username, String bio) {
        this.name = name;
        this.username = username;
        this.bio = bio;
    }

    public String getUsername() {
        return username;
    }

    public Pane usertoPane(Following f) throws IOException {
        ShowUserController controller = new ShowUserController();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showUser.fxml"));
        fxmlLoader.setController(controller);
        Pane p = fxmlLoader.load();
        controller.build(name, username, bio, f);
        return p;
    }
}
