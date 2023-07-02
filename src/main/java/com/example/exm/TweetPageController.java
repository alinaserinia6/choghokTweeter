package com.example.exm;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

public class TweetPageController {

	@FXML
	private MFXScrollPane sp;
	@FXML
	private AnchorPane frontAnchor;
	@FXML
	private AnchorPane panel;

	public void initialize() {
		Tweet tweet = Client.tweets.get(Client.selectedTweet);
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

	public void panelUp() {
		frontAnchor.setManaged(true);
		frontAnchor.setVisible(true);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), new KeyValue(panel.translateYProperty(), -47));
		Timeline t = new Timeline();
		t.setCycleCount(1);
		t.getKeyFrames().add(keyFrame);
		t.play();
	}

	@FXML
	private void panelDown() {
		frontAnchor.setManaged(false);
		frontAnchor.setVisible(false);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), new KeyValue(panel.translateYProperty(), 53));
		Timeline t = new Timeline();
		t.setCycleCount(1);
		t.getKeyFrames().add(keyFrame);
		t.play();
	}

	@FXML
	void retweet(MouseEvent e) throws IOException {
		System.out.println(Client.selectedTweet);
		Client.focusTweetControllers.get(Client.selectedTweet).selectRetweet();
	}

	@FXML
	void quote(MouseEvent e) throws IOException {
		Client.focusTweetControllers.get(Client.selectedTweet).selectQuote(e);
	}
}
