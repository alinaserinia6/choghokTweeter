package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Thread.sleep;

public class TweetController {
    @FXML
    private Label name;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label like;
    @FXML
    private Label comment;
    @FXML
    private Label retweet;
    @FXML
    private Label quote;
    @FXML
    private Avatar avatar;
    @FXML
    private Label date;
    @FXML
    private Label datePassed;
    @FXML
    private ImageView likePicture;
    @FXML
    private ImageView retweetPicture;
    @FXML
    private MFXScrollPane sp;
    private Image liked = new Image(getClass().getResource("Pliked.png").toString());
    private Image disliked = new Image(getClass().getResource("Plike.png").toString());
    private Image notRetweet = new Image(getClass().getResource("Pretweet.png").toString());
    private Image retweeted = new Image(getClass().getResource("Pretweeted.png").toString());
    private int id;
    private String username;
    private Tweet tweet;
    private boolean isLiked;
    private boolean isRetweeted;

    public void run() {
        try {
            Client.out.writeObject(new Request(RM.UPDATE_TWEET, username, id));
        } catch (IOException e) {throw new RuntimeException(e);}
        Thread thread1 = new Thread(() -> {
            while (true) {
                try {
                    Client.out.writeObject(new Request(RM.UPDATE_TWEET, username, id));
                } catch (IOException e) {throw new RuntimeException(e);}
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread1.start();
        Thread thread2 = new Thread(() -> { // TODO FROM TWEET SUBMIT NOT TWEET GET
            for (int i = 1; i < 60; i++) {
                int f = i;
                Platform.runLater(() -> {
                    datePassed.setText(f + "s");
                });
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            for (int i = 1; i < 60; i++) {
                int f = i;
                Platform.runLater(() -> {
                    datePassed.setText(f + "m");
                });
                try {
                    sleep(60000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            for (int i = 1; i < 24; i++) {
                try {
                    sleep(60000 * 60);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int f = i;
                Platform.runLater(() -> {
                    datePassed.setText(f + "h");
                });
            }
            for (int i = 1; i < 30; i++) {
                try {
                    sleep(60000 * 60);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int f = i;
                Platform.runLater(() -> {
                    datePassed.setText(f + "d");
                });
            }
        });
//        thread2.start();
    }

    public void build(Tweet tweet, String name, String text, int like, int comment, int retweet, String avatar, int id, String username) { // TODO CAN YOU DELETE THIS?
        this.tweet = tweet;
        this.name.setText(name);
        this.like.setText(String.valueOf(like));
        this.comment.setText(String.valueOf(comment));
        this.retweet.setText(String.valueOf(retweet));
        Image a = new Image(String.valueOf(getClass().getResource(avatar)));
        this.avatar.setImage(a);
        this.id = id;
        this.username = username;
        if (Client.user.likes.contains(id)) {
            likePicture.setImage(liked);
            isLiked = true;
        }
        else {
            likePicture.setImage(disliked);
            isLiked = false;
        }
    }

    public void focusBuild(Tweet tweet, String name, String username, String avatar, LocalDateTime date, int retweet, int quote, int like, int id) {
        this.tweet = tweet;
        this.name.setText(name);
        this.like.setText(String.valueOf(like));
        this.retweet.setText(String.valueOf(retweet));
        this.quote.setText(String.valueOf(quote));
        Image a = new Image(String.valueOf(getClass().getResource(avatar)));
        this.avatar.setImage(a);
        this.id = id;
        this.usernameLabel.setText(username);
        this.username = username;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy MMM");
        this.date.setText(date.format(format));
    }

    public void buildScroll() {
        VBox v = new VBox();
        sp.setContent(v);
        Platform.runLater(() -> {
            try {
                v.getChildren().add(tweet.toFocus());
                System.out.println(tweet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        for (Comment c : tweet.comments) {
            Platform.runLater(() -> {
                try {
                    v.getChildren().add(c.toShow());
                    System.out.println(c);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public void update(Tweet tweet) { // don't add constant field
        this.tweet = tweet;
        Platform.runLater(() -> {
            this.name.setText(tweet.getName());
            this.like.setText(String.valueOf(tweet.getLikes().size()));
            if (comment != null) this.comment.setText(String.valueOf(tweet.comments.size()));
            this.retweet.setText(String.valueOf(tweet.getRetweet().size()));
            this.avatar.setImage(tweet.getAvatar());
            if (tweet.getLikes().contains(Client.user.getUsername())) {
                likePicture.setImage(liked);
                isLiked = true;
            } else {
                likePicture.setImage(disliked);
                isLiked = false;
            }
            if (tweet.getRetweet().contains(Client.user.getUsername())) { // #00CE10 color of green retweeted
                retweetPicture.setImage(retweeted);
                isRetweeted = true;
            } else {
                retweetPicture.setImage(notRetweet);
                isRetweeted = false;
            }
        });
        System.out.println("tweet update: @" + username + "-" + id);
    }

    void likeChange(int x) {
        int y = Integer.parseInt(like.getText());
        y += x;
        like.setText(String.valueOf(y));
    }

    void retweetChange(int x) {
        int y = Integer.parseInt(retweet.getText());
        y += x;
        retweet.setText(String.valueOf(y));
    }

    @FXML
    void commentButton(MouseEvent e) throws IOException {

    }
    @FXML
    void likeButton(MouseEvent e) throws IOException {
        Client.out.writeObject(new Request(RM.LIKE_TWEET, username, id));
        if (isLiked) { // dislike
            Client.user.likes.remove(id);
            likePicture.setImage(disliked);
            likeChange(-1);
        } else { // like
            Client.user.likes.add(id);
            likePicture.setImage(liked);
            likeChange(1);
        }
        isLiked = !isLiked;
    }
    @FXML
    void retweetButton(MouseEvent e) throws IOException {

    }

    @FXML
    void searchButton(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a8");
    }

    @FXML
    void notificationButton(MouseEvent e) throws IOException{
        HelloApplication.ChangePage(e, "aNotification");
    }

    @FXML
    void directButton(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "aDirect");
    }

    @FXML
    void timelineButton(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a5");
    }

}
