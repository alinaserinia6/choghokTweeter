package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class UserFocusController {
	@FXML
	private ImageView avatar;
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

	@FXML
	void directMassage(MouseEvent e) throws IOException {
		Stage stage = HelloApplication.getStage();
		Direct direct = Client.directs.get(user.getUsername());
		if (direct == null) direct = new Direct(user);
		WriteMassageController controller = direct.getController();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("aWriteMassage.fxml"));
		fxmlLoader.setController(controller);
		controller.setDirect(direct);
		Parent root = fxmlLoader.load();
		root.setId("WriteMassage");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		Application a = new Application() {@Override public void start(Stage stage) throws Exception {}};
		scene.getStylesheets().addAll(a.getClass().getResource("anchor.css").toExternalForm());
		stage.show();
	}

	public void setUser(User user) {
		this.user = user;
	}
}
