package com.example.exm;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

public class TimeLineController {
	@FXML
	private ScrollPane sp;

	public void initialize() throws IOException {
		Client.out.writeObject(new Request(RM.GET_TWEETS, Client.user.following));
		ArrayList<Pane> twPane = (ArrayList<Pane>) Client.getObject();
		for (Pane i : twPane) sp.setContent(i);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				while (sp.getScene().getWindow().getScene() == sp.getScene()) {

				}
			}
		});

		//		sp.setContent();
	}

	@FXML
	void addTweet(ActionEvent e) throws IOException {
		HelloApplication.ChangePage(e, "a6");
	}
}
