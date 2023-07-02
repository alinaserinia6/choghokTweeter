package com.example.exm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class UserController {
    @FXML
    private Label name;
    @FXML
    private Label username;
    @FXML
    private JFXTextArea bio;
    @FXML
    private JFXButton follow;
    @FXML
    private ImageView avatar;
    @FXML
    private ImageView header;
    @FXML
    private Label joinDate;
    @FXML
    private Label follower;
    @FXML
    private Label following;
    @FXML
    private MFXScrollPane sp;
    private Following f;
    private ShowUserController sc;

    private User user;

    public void focusBuild(User user) {
        this.user = user;
        name.setText(user.getFirstName() + " " + user.getLastName());
        username.setText("@"+ user.getUsername());
        bio.setText(user.getBio());
        follower.setText(String.valueOf(user.followers.size()));
        int followingNum = 0;
        for (Following i : user.following.values()) if(i.isFollowing()) followingNum++;
        following.setText(String.valueOf(followingNum));
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy MMM");
        joinDate.setText(user.getJoinDate().format(format));
        avatar.setImage(user.getAvatar());
        makeFollowing();
        if (f.isFollowing()) {
            follow.setText("Following");
        } else {
            follow.setText("Follow");
        }
    }

    public void makeFollowing() {
        sc = Client.showUserControllers.get(user.getUsername());
        if (sc == null) {
            sc = new ShowUserController();
            Client.showUserControllers.put(user.getUsername(), sc);
        }
        f = Client.user.following.get(user.getUsername());
        if (f == null) {
            f = new Following(user.getUsername());
            f.setUser(user);
            Client.user.following.put(user.getUsername(), f);
        }
    }

    public void update(User user) {
        this.user = user;
        name.setText(user.getName());
        bio.setText(user.getBio());
        follower.setText(String.valueOf(user.followers.size()));
        following.setText(String.valueOf(user.following.size()));
        avatar.setImage(user.getAvatar());
        header.setImage(user.getHeader());
    }

    @FXML
    void backToTimeline(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a5");
    }

    @FXML
    void searchButton(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a8");
    }

    @FXML
    void directMassage(MouseEvent e) throws IOException {
        Stage stage = HelloApplication.getStage();
        Direct direct = Client.directs.get(user.getUsername());
        if (direct == null) {
            direct = new Direct(user);
            Client.directs.put(user.getUsername(), direct);
        }
        WriteMassageController controller = direct.getController();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("aWriteMassage.fxml"));
        fxmlLoader.setController(controller);
        controller.setDirect(direct);
        Parent root = fxmlLoader.load();
        root.setId("WriteMassage");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        Application a = new Application() {@Override public void start(Stage stage) throws Exception {}};
        scene.getStylesheets().addAll(a.getClass().getResource("anchor.css").toExternalForm());
        stage.show();
    }

    @FXML
    void followButton(ActionEvent e) throws IOException {
        Client.out.writeObject(new Request(RM.FOLLOW_REQUEST, user.getUsername()));
        sc.followChange();
        Following f = Client.user.following.get(user.getUsername());
        if (f.isFollowing()) {
            follow.setText("Follow");
            user.followers.remove(Client.user.getUsername());
            followChange(-1);
        } else {
            follow.setText("Following");
            user.followers.add(Client.user.getUsername());
            followChange(1);
        }
        f.follow();
    }

    private void followChange(int x) {
        int y = Integer.parseInt(follower.getText());
        y += x;
        follower.setText(String.valueOf(y));
    }

    @FXML
    void seeFollowing(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "aFollowPage", this);
        VBox v = new VBox();
        sp.setContent(v);
        for (Following i : user.following.values()) {
            if (i.isFollowing() && !i.getUsername().equals(Client.user.getUsername())) {
                Platform.runLater(() -> {
                    try {
                        v.getChildren().add(i.getUser().toShow());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }
        }
    }

    @FXML
    void seeFollowers(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "aFollowPage", this);
        VBox v = new VBox();
        sp.setContent(v);
        for (String i : user.followers) {
            Client.out.writeObject(new Request(RM.SEE_USER, i));
            User u = (User) Client.getObject();
            Platform.runLater(() -> {
                try {
                    v.getChildren().add(u.toShow());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

    @FXML
    void seeProfile(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "aUserFocus", this);
        focusBuild(user);
    }



}
