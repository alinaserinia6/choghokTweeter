package com.example.exm;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Tweet implements Serializable {
	protected int id;
	protected String avatar;
	protected String name;
	protected String username;
	private String text;
	protected LocalDateTime dt;
	protected ArrayList<String> likes;
	public ArrayList<Comment> comments;
	protected ArrayList<String> retweet;
	public ArrayList<Quote> quotes;
	private String retweetByUser;
	protected transient TweetController controller;
	private transient FocusTweetController focusController;
	protected transient boolean firstTime;

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
		quotes = new ArrayList<>();
		avatar = "PdefaultAvatar.png";
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

	public void updateUser(String avatar, String name) { // TODO UPDATE WHEN EDIT PROFILE
		this.avatar = avatar;
		this.name = name;
	}

	public void updateTweet(Tweet t) {
		if (t == null) return;
		likes = t.getLikes();
		retweet = t.getRetweet();
		comments = t.getComments();
	}

	public GridPane toShow() throws IOException {
		updateTweet(Client.tweets.get(id));
		makeController();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showTweet.fxml"));
		fxmlLoader.setController(controller);
		GridPane p = fxmlLoader.load();
		JFXTextArea txt = new JFXTextArea();
		fitTextArea(txt);
		txt.setCursor(Cursor.HAND);
		txt.setOnMouseClicked(e -> {
			try {
				Client.selectedTweet = id;
				HelloApplication.ChangePage(e, "aFocusTweet");
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		});
		if (retweetByUser != null) {
			ImageView ret = new ImageView();
			Image r = new Image(String.valueOf(getClass().getResource("Pretweet.png")));
			ret.setImage(r);
			p.add(ret, 0, 0);
			Label name = new Label();
			name.setText("Retweeted " + this.name);
			p.add(name, 1, 0);
		}
		p.add(txt, 1, 2);
		controller.build(this, name, likes.size(), comments.size(), retweet.size(), avatar, id, username);
		if (firstTime) controller.run();
		return p;
	}
	public GridPane toFocus() throws IOException {
		updateTweet(Client.tweets.get(id));
		Client.selectedTweet = id;
		focusController = makeFocusController();
		Client.focusTweetControllers.put(id, focusController);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("focusTweet.fxml"));
		fxmlLoader.setController(focusController);
		GridPane p = fxmlLoader.load();
		JFXTextArea txt = new JFXTextArea();
		fitTextArea(txt);
		p.add(txt, 0, 1); // TODO QUOTE
		focusController.focusBuild(this);
		return p;
	}

	protected void makeController() {
		controller = Client.tweetControllers.get(id);
		if (controller == null) {
			System.err.println("making new controller for " + id + " tweet");
			controller = new TweetController();
			Client.tweetControllers.put(id, controller);
			firstTime = true;
		} else {
			firstTime = false;
		}
	}
	private FocusTweetController makeFocusController() {
		FocusTweetController f = Client.focusTweetControllers.get(id);
		if (f == null) {
			System.err.println("making new controller for " + id + " tweet");
			f = new FocusTweetController();
			Client.focusTweetControllers.put(id, focusController);
		}
		return f;
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

	public void makeController(TweetController controller) {
		this.controller = controller;
	}

	public String getRetweetByUser() {
		return retweetByUser;
	}

	public void setRetweetByUser(String retweetByUser) {
		this.retweetByUser = retweetByUser;
	}

	@Override
	public String toString() {
		return username + ": " + text + " " + likes.size();
	}
}