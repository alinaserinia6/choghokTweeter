package com.example.exm;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class LikeNotificationController {
	@FXML
	private Label name;
	@FXML
	private Label username;
	@FXML
	private ImageView avatar;
	@FXML
	private JFXTextArea text;
	public void build(User user, Tweet tweet) {
		this.name.setText(user.getFirstName() + " " + user.getLastName());
		this.avatar.setImage(user.getAvatar());
		this.username.setText(user.getUsername());
		this.text.setText(tweet.getText());
	}
}
