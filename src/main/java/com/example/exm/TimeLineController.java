package com.example.exm;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

public class TimeLineController {
	@FXML
	private ScrollPane sp;


	public void initialize() throws IOException {
		System.out.println("initialize timeLine");
		Client.out.writeObject(new Request(RM.GET_TWEETS, Client.user.following));
		ArrayList<Tweet> twPane = (ArrayList<Tweet>) Client.getObject();
		for (Tweet i : twPane) sp.setContent(i.tweetToPane());
//		Platform.runLater(new Runnable() {
//			@Override
//			public void run() {
//				while (sp.getScene().getWindow().getScene() == sp.getScene()) {
//
//				}
//			}
//		});



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
