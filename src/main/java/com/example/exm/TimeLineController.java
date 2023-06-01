package com.example.exm;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class TimeLineController {
	@FXML
	private ScrollPane sp;
	@FXML
	private VBox vb;

	private Timeline timeline;

	public void initialize() throws IOException {
		System.out.println("initialize timeLine");
		InputStream stream = new FileInputStream("/home/ali/B/exm/src/main/resources/com/example/exm/header.jpg");
		Image image = new Image(stream);
		ImageView j = new ImageView();
		j.setImage(image);
		vb.getChildren().add(j);
		Client.out.writeObject(new Request(RM.GET_TWEETS, Client.user.following));
		ArrayList<Tweet> twPane = (ArrayList<Tweet>) Client.getObject();
		System.out.println(twPane.size() + ": ");
		for (Tweet i : twPane) {
			System.out.println(i.getText());
		}
		for (Tweet i : twPane) vb.getChildren().add(i.tweetToPane());

		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		KeyFrame kf1 = new KeyFrame(Duration.ZERO, e -> getTweets());
		timeline.getKeyFrames().add(kf1);
		timeline.play();
		System.err.println("hey");
	}

	private void getTweets() {
		System.out.println("in timeline");
	}

	@FXML
	void addTweet(MouseEvent e) throws IOException {
		HelloApplication.ChangePage(e, "a6");
	}

	@FXML
	void setting(ActionEvent e) throws IOException {
		HelloApplication.ChangePage(e, "a7");
	}


}
