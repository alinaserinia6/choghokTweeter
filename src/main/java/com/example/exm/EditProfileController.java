package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
	private ImageView avatar;
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
	@FXML
	private Label error;
	private boolean isForAvatar;
	private String avatarPath;
	private String headerPath;

	public void initialize() {
		if (Client.user.getHeader() != null) header.setImage(Client.user.getHeader());
		avatar.setImage(Client.user.getAvatar());
		firstname.setText(Client.user.getFirstName());
		lastname.setText(Client.user.getLastName());
		bio.setText(Client.user.getBio());
		city.setText(Client.user.getLocation());
		website.setText(Client.user.getWebsite());
		avatarPath = Client.user.getAvatarAsString();
		headerPath = Client.user.getHeaderAsString();
	}

	@FXML
	void backButton(MouseEvent e) throws IOException {
		HelloApplication.ChangePage(e, "a7");
	}

	@FXML
	void saveButton(ActionEvent e) throws IOException {
		if (firstname.getText().isEmpty()) {
			error.setText("first name should not be empty");
		}
		if (lastname.getText().isEmpty()) {
			error.setText("last name should not be empty");
		}
		Client.user.setFirstName(firstname.getText());
		Client.user.setLastName(lastname.getText());
		System.out.println(headerPath + " " + avatarPath + " ://///////////////");
		Client.user.setHeader(headerPath);
		Client.user.setAvatar(avatarPath);
		Client.user.setBio(bio.getText());
		Client.user.setLocation(city.getText());
		Client.user.setWebsite(website.getText());
		Client.out.writeObject(new Request(RM.UPDATE_USER, Client.user));
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
		String path = file.getName();
		if (!file.exists() || !file.isFile()) {
			error.setText("choose a true file");
			return;
		}
		if (getFileSizeMegaBytes(file) > 2) {
			error.setText("size of your image is bigger than 2MB (" + getFileSizeMegaBytes(file) + ")");
			return;
		}
		Image image = new Image (file.toURI().toString());
		if (image.getHeight() > 500 || image.getWidth() > 1500) {
			error.setText("size of width and height image is bigger than 500 * 1500");
			return;
		}
		if (isForAvatar) {
			avatar.setImage(image);
			avatarPath = path;
		}
		else {
			header.setImage(image);
			headerPath = path;
		}
		System.out.println(path);
		movePanel(false);
	}

	public void movePanel(boolean up) {
		int y = up ? 0 : 130;
		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), new KeyValue(chooseImagePanel.translateYProperty(), y));
		Timeline t = new Timeline();
		t.setCycleCount(1);
		t.getKeyFrames().add(keyFrame);
		t.play();
	}

	private static double getFileSizeMegaBytes(File file) {
		return (double) file.length() / (1024 * 1024);
	}
}