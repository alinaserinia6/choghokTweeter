package com.example.exm;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TweetPageController {

	@FXML
	private MFXScrollPane sp;

	public void initialize() {
		Tweet tweet = Client.selectedTweet;
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

}
