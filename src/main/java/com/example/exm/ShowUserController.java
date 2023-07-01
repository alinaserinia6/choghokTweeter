package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

public class ShowUserController {
	@FXML
	private Label name;
	@FXML
	private Label username;
	@FXML
	private Label bio;
	@FXML
	private JFXButton follow;
	@FXML
	private ImageView avatar;
	private boolean isFollow;
	private Following following;
	private User user;

	public void build(User user, Following following) {
		this.name.setText(user.getFirstName() + user.getLastName());
		this.username.setText(user.getUsername());
		this.bio.setText(user.getBio());
		this.avatar.setImage(user.getAvatar());
		this.following = following;
		this.user = user;
		isFollow = following != null && following.isFollowing();
		if (isFollow) {
			follow.setText("دنبال شده");
		} else {
			follow.setText("دنبال کردن");
		}
	}
	@FXML
	void followButton(ActionEvent e) throws IOException {
		Client.out.writeObject(new Request(RM.FOLLOW_REQUEST, username.getText()));
		if (following == null) {
			following = new Following(username.getText(), LocalDateTime.MIN);
			Client.user.following.put(username.getText(), following);
		} else {
			following.follow();
		}
		if (isFollow) {
			Client.user.following.remove(username.getText());
			follow.setText("دنبال کردن");
		} else {
			Client.user.following.put(username.getText(), following);
			follow.setText("دنبال شده");
		}
		isFollow = !isFollow;
	}

	@FXML
	void seeProfile(MouseEvent e) throws IOException {
		UserFocusController controller = new UserFocusController();
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("aUserFocus.fxml"));
		fxmlLoader.setController(controller);
		controller.setUser(user);
		Parent root = fxmlLoader.load();
		root.setId("aUserFocus");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		Application a = new Application() {@Override public void start(Stage stage) throws Exception {}};
		scene.getStylesheets().addAll(a.getClass().getResource("anchor.css").toExternalForm());
		stage.show();
	}

}