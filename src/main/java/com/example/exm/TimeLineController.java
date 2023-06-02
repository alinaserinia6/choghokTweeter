package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class TimeLineController {
	@FXML
	private ScrollPane sp;
	@FXML
	private Avatar avatar;
	private boolean shutdown;

	public void initialize() {
		System.out.println("initialize timeLine");
		shutdown = false;
		Label l = new Label();
		l.setText("In the name of GOD");
		Client.timeline.getChildren().add(l);
        sp.setContent(Client.timeline);
		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						//Background work
						System.out.println("IN SERVICE");
                        Client.out.writeObject(new Request(RM.GET_TWEETS));
						while (!shutdown) {
							System.out.println("in while");
							getTweets();
							Thread.currentThread().sleep(4000);
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
            Tweet i = (Tweet) Client.getObject();
            System.out.println("i am in: " + i.getText() + ": ");
            Pane pane = i.tweetToPane();
            Platform.runLater(() -> {
                Client.timeline.getChildren().add(pane);
                try {
                    Client.out.writeObject(new Request(RM.LAST_SEEN_TIME, i.getUser().getUsername(), i.getDt()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
		} catch (IOException ignore) {
			System.err.println("get IOException");
		}
        System.err.println("finish getting tweet");
	}

	@FXML
	void addTweet(MouseEvent e) throws IOException {
		shutdown = true;
		HelloApplication.ChangePage(e, "a6");
	}

	@FXML
	void setting(MouseEvent e) throws IOException {
		shutdown = true;
		HelloApplication.ChangePage(e, "a7");
	}


}
