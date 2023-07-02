package com.example.exm;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    private ImageView avatar;
    @FXML
    private Label datePassed;
    @FXML
    private ImageView likePicture;
    @FXML
    private ImageView retweetPicture;
    private Image liked = new Image(getClass().getResource("Pliked.png").toString());
    private Image disliked = new Image(getClass().getResource("Plike.png").toString());
    private Image notRetweet = new Image(getClass().getResource("Pretweet.png").toString());
    private Image retweeted = new Image(getClass().getResource("Pretweeted.png").toString());
    private FocusTweetController ft;
    private UserController uc;
    private int id;
    private String username;
    private Tweet tweet;
    private boolean itsMe;

    public void run() {
        Thread thread1 = new Thread(() -> {
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
        if (datePassed == null) return;
        Thread thread2 = new Thread(() -> { // TODO check
            long i = ChronoUnit.SECONDS.between(tweet.getDt(), LocalDateTime.now()), mod = 1, seconds = 1000;
            long [] a = {1, 60, 60, 24, 30, 12};
            String [] symbol = {"s", "m", "h", "d", "M"};
            for (int j = 0; j < 5; j++) {
                final int g = j;
                while (i < a[j + 1]) {
                    final long f = i;
                    Platform.runLater(() -> {
                        datePassed.setText(f + symbol[g]);
                    });
                    i++;
                    try {
                        sleep(mod * seconds);
                        if (mod != a[j]) mod = a[j];
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                seconds *= a[j + 1];
                mod = a[j + 1] - i % a[j + 1];
                i /= a[j + 1];
            }
        });
        thread2.start();
    }

    public void build(Tweet tweet, String name, int like, int comment, int retweet, String avatar, int id, String username) {
        this.tweet = tweet;
        this.name.setText(name);
        this.like.setText(String.valueOf(like));
        this.comment.setText(String.valueOf(comment));
        this.retweet.setText(String.valueOf(retweet));
        Image a = new Image(String.valueOf(getClass().getResource(avatar)));
        this.avatar.setImage(a);
        this.id = id;
        this.username = username;
        itsMe = username.equals(Client.user.getUsername());
        long i = ChronoUnit.SECONDS.between(tweet.getDt(), LocalDateTime.now());
        String symbol = "s";
        if (i > 60) {
            i = ChronoUnit.MINUTES.between(tweet.getDt(), LocalDateTime.now());
            symbol = "m";
        }
        if (i > 60) {
            i = ChronoUnit.HOURS.between(tweet.getDt(), LocalDateTime.now());
            symbol = "h";
        }
        if (i > 24) {
            i = ChronoUnit.DAYS.between(tweet.getDt(), LocalDateTime.now());
            symbol = "d";
        }
        this.datePassed.setText(i + symbol);
        if (Client.user.likes.contains(id)) {
            likePicture.setImage(liked);
        } else {
            likePicture.setImage(disliked);
        }
        if (Client.user.tweets.containsKey(id) && !itsMe) {
            retweetPicture.setImage(retweeted);
        } else {
            retweetPicture.setImage(notRetweet);
        }
        ft = Client.focusTweetControllers.get(id);
    }

    public void update(Tweet tweet) { // don't add constant field
        this.tweet = tweet;
        debugUpdate();
        Platform.runLater(() -> {
            this.name.setText(tweet.getName());
            this.like.setText(String.valueOf(tweet.getLikes().size()));
            this.retweet.setText(String.valueOf(tweet.getRetweet().size()));
            this.comment.setText(String.valueOf(tweet.comments.size()));
            this.avatar.setImage(tweet.getAvatar());
        });
        System.out.println("tweet update: @" + username + "-" + id);
    }

    private void debugUpdate() {
        if (tweet.getLikes().contains(Client.user.getUsername()) && !Client.user.likes.contains(id)) {
            tweet.getLikes().remove(Client.user.getUsername());
            likePicture.setImage(disliked);
        }
        if (!tweet.getLikes().contains(Client.user.getUsername()) && Client.user.likes.contains(id)) {
            tweet.getLikes().add(Client.user.getUsername());
            likePicture.setImage(liked);
        }
        if (!tweet.getLikes().contains(Client.user.getUsername()) && !Client.user.likes.contains(id)) {
            likePicture.setImage(disliked);
        }
        if (tweet.getLikes().contains(Client.user.getUsername()) && Client.user.likes.contains(id)) {
            likePicture.setImage(liked);
        }
        if (tweet.getRetweet().contains(Client.user.getUsername()) && !Client.user.tweets.containsKey(id)) {
            tweet.getRetweet().remove(Client.user.getUsername());
            retweetPicture.setImage(notRetweet);
        }
        if (!tweet.getRetweet().contains(Client.user.getUsername()) && Client.user.tweets.containsKey(id) && !itsMe) {
            tweet.getRetweet().add(Client.user.getUsername());
            retweetPicture.setImage(retweeted);
        }
    }

    public void likeChange(int x) {
        int y = Integer.parseInt(like.getText());
        y += x;
        like.setText(String.valueOf(y));
        if (x == 1) likePicture.setImage(liked);
        else likePicture.setImage(disliked);
    }

    public void retweetChange(int x) {
        int y = Integer.parseInt(retweet.getText());
        y += x;
        retweet.setText(String.valueOf(y));
        if (x == 1) retweetPicture.setImage(retweeted);
        else retweetPicture.setImage(notRetweet);
    }

    @FXML
    void seeProfile(MouseEvent e) throws IOException{
        makeController();
        TimeLineController.stop();
        Client.out.writeObject(new Request(RM.SEE_USER, username));
        User u = (User) Client.getObject();
        HelloApplication.ChangePage(e, "aUserFocus", uc);
        uc.focusBuild(u);
    }

    private void makeController() {
        uc = Client.userControllers.get(username);
        if (uc == null) {
            uc = new UserController();
            Client.userControllers.put(username, uc);
        }
    }

    @FXML
    void commentButton(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "aFocusTweet");
    }
    @FXML
    void likeButton(MouseEvent e) throws IOException {
        Client.out.writeObject(new Request(RM.LIKE_TWEET, username, id));
        if (Client.user.likes.contains(id)) { // dislike
            Client.user.likes.remove(id);
            likeChange(-1);
            if (ft != null) ft.likeChange(-1);
        } else { // like
            Client.user.likes.add(id);
            likeChange(1);
            if (ft != null) ft.likeChange(1);
        }
    }
    @FXML
    void retweetButton(MouseEvent e) throws IOException {
        if (itsMe) return;
        Client.out.writeObject(new Request(RM.RETWEET, username, id));
        if (Client.user.tweets.containsKey(id)) {
            Client.user.tweets.remove(id);
            retweetPicture.setImage(notRetweet);
            retweetChange(-1);
            if (ft != null) ft.retweetChange(-1);
        } else {
            Client.user.tweets.put(id, tweet);
            retweetPicture.setImage(retweeted);
            retweetChange(1);
            if (ft != null) ft.retweetChange(1);
        }
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
