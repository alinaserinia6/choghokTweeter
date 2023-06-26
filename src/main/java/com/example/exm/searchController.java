package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.application.Platform;

public class searchController {
    @FXML
    private MFXTextField searchBox;
    @FXML
    private Avatar avatar;
    @FXML
    private MFXScrollPane sp;

    public void initialize() throws IOException {
        sp.setContent(Client.contacts);
        Client.out.writeObject(new Request(RM.DISCOVER_USERS, Client.LAST_USER_SEEN));
        Client.LAST_USER_SEEN = (LocalDateTime) Client.getObject();
        ArrayList<ShowUser> userList = (ArrayList<ShowUser>) Client.getObject();
        for (ShowUser u : userList) {
            System.out.println(u.getUsername());
            Platform.runLater(() -> {
                try {
                    Client.contacts.getChildren().add(u.usertoPane(Client.user.following.get(u.getUsername())));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }
    @FXML
    void timeLineButton(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a5");
    }

    @FXML
    void setting(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a7");
    }

    @FXML
    void notificationButton(MouseEvent e) throws IOException{
        HelloApplication.ChangePage(e, "aNotification");
    }

}
