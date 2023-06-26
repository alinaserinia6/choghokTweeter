package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

public class LikeNotificationController {
	@FXML
	private Label name;
	@FXML
	private Label username;
	@FXML
	private Avatar avatar;
	@FXML
	private JFXTextArea text;
	public void build(User user, Tweet tweet) {
		this.name.setText(user.getFirstName() + " " + user.getLastName());
		this.avatar.setImage(user.getAvatar());
		this.username.setText(user.getUsername());
		this.text.setText(tweet.getText());
	}
}
