package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
public class EditProfileController {
	@FXML
	private Avatar avatar;
	@FXML
	private ImageView header;
	@FXML
	private TextField firstname;
	@FXML
	private TextField lastname;
	@FXML
	private TextField bio;
	@FXML
	private TextField city;
	@FXML
	private TextField website;
	@FXML
	private AnchorPane chooseImagePanel;
	private boolean isForAvatar;

	public void initialize() {
		if (Client.user.getHeader() != null) header.setImage(Client.user.getHeader());
		avatar.setImage(Client.user.getAvatar());
		firstname.setText(Client.user.getFirstName());
		lastname.setText(Client.user.getLastName());
		bio.setText(Client.user.getBio());
		city.setText(Client.user.getLocation());
		website.setText(Client.user.getWebsite());
	}

	@FXML
	void backButton(MouseEvent e) throws IOException {
		HelloApplication.ChangePage(e, "a7");
	}

	@FXML
	void saveButton(ActionEvent e) throws IOException {
		// TODO MAKE CLASS TO CHANGE CLIENT AND SERVER
		HelloApplication.ChangePage(e, "a7");
	}

	@FXML
	void changeHeader(MouseEvent e) throws IOException {
		isForAvatar = false;
		movePanel(true);
	}
	@FXML
	void changeAvatar(MouseEvent e) throws IOException {
		isForAvatar =  true;
		movePanel(true);
	}

	@FXML
	void cancel(MouseEvent e) {
		movePanel(false);
	}

	@FXML
	void chooseImageButton(MouseEvent e) {
		FileChooser f = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png");
		f.getExtensionFilters().add(extFilter);
		Stage stage = new Stage();
		stage.setTitle("select picture");
		File file = f.showOpenDialog(stage);
		if (file == null) return;
		Image image = new Image (file.toURI().toString(), 285, 149, false, true, true);
		if (isForAvatar) avatar.setImage(image);
		else header.setImage(image);
	}

	public void movePanel(boolean up) {
		int y = up ? 0 : 130;
		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), new KeyValue(chooseImagePanel.translateYProperty(), y));
		Timeline t = new Timeline();
		t.setCycleCount(1);
		t.getKeyFrames().add(keyFrame);
		t.play();
	}
}