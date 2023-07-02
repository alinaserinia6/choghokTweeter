package com.example.exm;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class Quote extends Tweet {
	int quoteId;

	public Quote(String text, String username) {
		super(text, username);
	}

	public Quote(String text, String username, int quoteId) {
		super(text, username);
		this.quoteId = quoteId;
	}

	public int getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(int quoteId) {
		this.quoteId = quoteId;
	}
	@Override
	public GridPane toShow() throws IOException {
		updateTweet(Client.tweets.get(id));
		super.makeController();
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
		Tweet j = Client.tweets.get(quoteId);
		p.add(j.toShow(), 1, 3);
		controller.build(this, name, likes.size(), comments.size(), retweet.size(), avatar, id, username);
		if (firstTime) controller.run();
		return p;
	}
}
