package com.example.exm;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class FocusTweetController {
	@FXML
	private Label name;
	@FXML
	private Label usernameLabel;
	@FXML
	private Label like;
	@FXML
	private Label retweet;
	@FXML
	private Label quote;
	@FXML
	private ImageView avatar;
	@FXML
	private Label date;
	@FXML
	private ImageView likePicture;
	@FXML
	private ImageView retweetPicture;
	private Image liked = new Image(getClass().getResource("Pliked.png").toString());
	private Image disliked = new Image(getClass().getResource("Plike.png").toString());
	private Image notRetweet = new Image(getClass().getResource("Pretweet.png").toString());
	private Image retweeted = new Image(getClass().getResource("Pretweeted.png").toString());
	private boolean itsMe;
	private int id;
	private String username;
	private Tweet tweet;
	private TweetController tc;

	public void focusBuild(Tweet tweet) {
		this.tweet = tweet;
		this.name.setText(tweet.getName());
		this.like.setText(String.valueOf(tweet.getLikes().size()));
		this.retweet.setText(String.valueOf(tweet.getRetweet().size()));
		this.quote.setText(String.valueOf(tweet.getRetweet().size())); // TODO QUOTE
		this.avatar.setImage(tweet.getAvatar());
		this.id = tweet.getId();
		this.usernameLabel.setText(tweet.getUsername());
		this.username = tweet.getUsername();
		this.tc = Client.tweetControllers.get(id);
		this.itsMe = username.equals(Client.user.getUsername());
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy MMM");
		this.date.setText(tweet.getDt().format(format));
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
	}

	public void update(Tweet tweet) { // don't add constant field
		this.tweet = tweet;
		debugUpdate();
		Platform.runLater(() -> {
			this.name.setText(tweet.getName());
			this.like.setText(String.valueOf(tweet.getLikes().size()));
			this.retweet.setText(String.valueOf(tweet.getRetweet().size()));
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
		if (tweet.getRetweet().contains(Client.user.getUsername()) && !Client.user.tweets.containsKey(id)) {
			tweet.getRetweet().remove(Client.user.getUsername());
			retweetPicture.setImage(retweeted);
		}
		if (!tweet.getRetweet().contains(Client.user.getUsername()) && Client.user.tweets.containsKey(id) && !itsMe) {
			tweet.getRetweet().add(Client.user.getUsername());
			retweetPicture.setImage(notRetweet);
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
	void commentButton(MouseEvent e) throws IOException {
		HelloApplication.ChangePage(e, "aFocusTweet");
	}
	@FXML
	void likeButton(MouseEvent e) throws IOException {
		Client.out.writeObject(new Request(RM.LIKE_TWEET, username, id));
		if (Client.user.likes.contains(id)) { // dislike
			Client.user.likes.remove(id);
			likeChange(-1);
			tc.likeChange(-1);
		} else { // like
			Client.user.likes.add(id);
			likeChange(1);
			tc.likeChange(1);
		}
	}
	@FXML
	void retweetButton(MouseEvent e) throws IOException {
		if (itsMe) return;
		Client.tp.panelUp();
	}

	public void selectRetweet() throws IOException {
		Client.out.writeObject(new Request(RM.RETWEET, username, id));
		if (Client.user.tweets.containsKey(id)) {
			Client.user.tweets.remove(id);
			retweetPicture.setImage(notRetweet);
			retweetChange(-1);
			tc.retweetChange(-1);
		} else {
			Client.user.tweets.put(id, tweet);
			retweetPicture.setImage(retweeted);
			retweetChange(1);
			tc.retweetChange(1);
		}
	}

	public void selectQuote(MouseEvent e) throws IOException {
		HelloApplication.ChangePage(e, "aQuote");
	}

}
