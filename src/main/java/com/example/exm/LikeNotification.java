package com.example.exm;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LikeNotification {
	private ShowUser user;
	private Tweet tweet;
	private LikeNotificationController controller;

	public LikeNotification(ShowUser user, Tweet tweet) {
		this.user = user;
		this.tweet = tweet;
	}

	public Pane toPane() throws IOException {
		LikeNotificationController controller = new LikeNotificationController();
		this.controller = controller;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("aLikeNotification.fxml"));
		fxmlLoader.setController(controller);
		Pane p = fxmlLoader.load();
		controller.build(user, tweet);
		return p;
	}
}
