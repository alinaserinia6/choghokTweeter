package com.example.exm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ShowUserController {
	@FXML
	private Label name;
	@FXML
	private Label username;
	@FXML
	private JFXTextArea bio;
	@FXML
	private JFXButton follow;
	@FXML
	private ImageView avatar;
	private UserController uc;
	private Following f;
	private User user;
	public void showBuild(User user) {
		this.user = user;
		name.setText(user.getFirstName() + " " + user.getLastName());
		username.setText("@"+ user.getUsername());
		bio.setText(user.getBio());
		avatar.setImage(user.getAvatar());
		makeFollowing();
		if (f.isFollowing()) {
			follow.setText("Following");
		} else {
			follow.setText("Follow");
		}
	}

	public void makeFollowing() {
		f = Client.user.following.get(user.getUsername());
		if (f == null) {
			f = new Following(user.getUsername());
			f.setUser(user);
			Client.user.following.put(user.getUsername(), f);
		}
	}

	private void makeController() {
		uc = Client.userControllers.get(user.getUsername());
		if (uc == null) {
			uc = new UserController();
			Client.userControllers.put(user.getUsername(), uc);
		}
	}

	@FXML
	void seeProfile(MouseEvent e) throws IOException{
		makeController();
		TimeLineController.stop();
		Client.out.writeObject(new Request(RM.SEE_USER, user.getUsername()));
		User u = (User) Client.getObject();
		user = u;
		HelloApplication.ChangePage(e, "aUserFocus", uc);
		uc.focusBuild(u);
	}

	@FXML
	void followButton(ActionEvent e) throws IOException {
		Client.out.writeObject(new Request(RM.FOLLOW_REQUEST, user.getUsername()));
		Following f = Client.user.following.get(user.getUsername());
		if (f.isFollowing()) {
			follow.setText("Follow");
			user.followers.remove(Client.user.getUsername());
		} else {
			follow.setText("Following");
			user.followers.add(Client.user.getUsername());
		}
		f.follow();
	}

	public void followChange() {
		Following f = Client.user.following.get(user.getUsername());
		if (f.isFollowing()) {
			follow.setText("Follow");
			user.followers.remove(Client.user.getUsername());
		} else {
			follow.setText("Following");
			user.followers.add(Client.user.getUsername());
		}
	}
}
