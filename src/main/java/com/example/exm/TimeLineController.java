package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.io.IOException;
import io.github.palexdev.materialfx.controls.MFXScrollPane;

public class TimeLineController {
	@FXML
	private MFXScrollPane sp;
	@FXML
	private Avatar avatar;
	private boolean shutdown;
//	private Service<Void> service;
	private Thread thread;

	private final int MAX_READ = 5 + 1;

	public void initialize() {
		System.out.println("initialize timeLine");
		shutdown = false;
		Label l = new Label();
		l.setText("In the name of GOD");
		Client.timeline.getChildren().add(0,l);
        sp.setContent(Client.timeline);
//		service = new Service<Void>() {
//			@Override
//			protected Task<Void> createTask() {
//			return new Task<Void>() {
//			@Override
//			protected Void call() throws Exception {
//				System.out.println("IN SERVICE");
//				while (!shutdown) {
//					Client.out.writeObject(new Request(RM.GET_TWEETS));
//					System.out.println("in while");
//					getTweets();
//					Thread.sleep(4000); // time of sleep between get tweet
//				}
//				return null;
//			}
//			};
//			}
//		};
//		service.start();
		thread = new Thread(() -> {
			while (!shutdown) {
				try {
					Client.out.writeObject(new Request(RM.GET_TWEETS));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				System.out.println("in while");
				getTweets();
				try {
					Thread.sleep(4000); // time of sleep between get tweet
				} catch (InterruptedException e) {
					System.err.println("\t\t\t{interrupted}");
					break;
				}
			}
			System.out.println("finish thread");
		});
		thread.start();
		System.err.println("hey");
	}

	private void getTweets() {
		System.out.println("\t".repeat(7) + "{getTweet}");
		for (int t = 0; t < MAX_READ; t++) {
			try {
				Tweet i = (Tweet) Client.in.readObject();
				if (i.getId() == -1) {
					System.err.println("\t".repeat(7) + "{Finish}");
					return;
				}
				System.out.println("i am in: " + i.getText() + ": ");
				Pane pane = i.tweetToPane();
				Platform.runLater(() -> Client.timeline.getChildren().add(0, pane));
				Client.out.writeObject(new Request(RM.LAST_SEEN_TIME, i.getUsername(), i.getDt()));
			} catch (IOException | ClassNotFoundException e) {
				System.err.println("get IOException");
				e.printStackTrace();
			}
		}
		System.err.println("finish getting tweet");
	}

	@FXML
	void addTweet(MouseEvent e) throws IOException {
		shutdown = true;
//		service.cancel();
		thread.interrupt();
		HelloApplication.ChangePage(e, "a6");
	}

	@FXML
	void setting(MouseEvent e) throws IOException {
		shutdown = true;
		thread.interrupt();
//		service.cancel();
		HelloApplication.ChangePage(e, "a7");
	}

	@FXML
	void searchButton(MouseEvent e) throws IOException {
		HelloApplication.ChangePage(e, "a8");
	}

}
