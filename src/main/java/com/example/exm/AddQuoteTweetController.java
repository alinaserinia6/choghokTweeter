package com.example.exm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AddQuoteTweetController {
	@FXML
	private TextArea text;
	@FXML
	private Button postButton;
	@FXML
	private VBox quoteTweet;

	private Tweet t;
	public void initialize() throws IOException {
		t = Client.tweets.get(Client.selectedTweet);
		quoteTweet.getChildren().add(t.toShow());
	}

	@FXML
	void post(ActionEvent e) throws IOException {
		if (text.getText().isEmpty()) return;
		Quote quote = new Quote(text.getText(), Client.user.getUsername());
		quote.updateUser(Client.user.getAvatarAsString(), Client.user.getName());
		Client.out.writeObject(new Request(RM.QUOTE, t.getUsername(), t.getId(), quote));
		int id = (Integer) Client.getObject();
		quote.setId(id);
		quote.setQuoteId(t.getId());
		Client.user.tweets.put(id, quote);
		Client.tweets.put(id, quote);
		Client.timeline.getChildren().add(0, quote.toShow());
		HelloApplication.ChangePage(e, "a5");
	}

	@FXML
	void back(MouseEvent e) throws IOException {
		HelloApplication.ChangePage(e, "a5");
	}

	@FXML
	void postButtonAppear() {
		if (text.getText().isEmpty()) {
			postButton.setOpacity(0.5);
		} else {
			postButton.setOpacity(1);
		}
	}
}
