package com.example.exm;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;

import java.io.IOException;
import java.util.ArrayList;

public class TimeLineController {
	@FXML
	private ScrollPane sp;
	@FXML
	private Shape qw;
	@FXML
	private VBox vb;
	private boolean shutdown;

	public void initialize() throws IOException {
		System.out.println("initialize timeLine");
		shutdown = false;
		Client.out.writeObject(new Request(RM.GET_TWEETS));
		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						//Background work
						System.out.println("IN SERVICE");
						while (!shutdown) {
							System.out.println("in while");
							getTweets();
							Thread.currentThread().sleep(5000);
						}
						return null;
					}
				};
			}
		};
		service.start();
		System.err.println("hey");
	}

	private void getTweets() {
		System.out.println("\t".repeat(7) + "{getTweet}");
		try {
			ArrayList<Tweet> tw = (ArrayList<Tweet>) Client.in.readObject();
			System.out.println("tw size: " + tw.size());
			for (Tweet i : tw) vb.getChildren().add(i.tweetToPane());
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	void addTweet(MouseEvent e) throws IOException, InterruptedException {
		shutdown = true;
		HelloApplication.ChangePage(e, "a6");
	}

	@FXML
	void setting(MouseEvent e) throws IOException {
		shutdown = true;
		HelloApplication.ChangePage(e, "a7");
	}


}
