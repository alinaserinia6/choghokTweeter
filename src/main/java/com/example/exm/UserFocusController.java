package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class UserFocusController {
	@FXML
	private Label name;
	@FXML
	private Label username;
	@FXML
	private Label joinDate;
	@FXML
	private Label follower;
	@FXML
	private Label following;
	@FXML
	private TextArea bio;
	@FXML
	private Avatar avatar;

	private User user;

	public void initialize() {
		name.setText(user.getFirstName() + " " + user.getLastName());
		username.setText("@"+ user.getUsername());
		bio.setText(user.getBio());
		follower.setText(String.valueOf(user.followers.size()));
		following.setText(String.valueOf(user.following.size()));
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy MMM");
		joinDate.setText(user.getJoinDate().format(format));
		avatar.setImage(user.getAvatar());
	}

	@FXML
	void backtotimeline(MouseEvent e) throws IOException {
		HelloApplication.ChangePage(e, "a5");
	}

	@FXML
	void searchButton(MouseEvent e) throws IOException {
		HelloApplication.ChangePage(e, "a8");
	}

	public void setUser(User user) {
		this.user = user;
	}
}
