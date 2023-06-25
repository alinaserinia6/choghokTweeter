package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import com.jfoenix.controls.JFXTextArea;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
public class EditProfileController {
	@FXML
	private Avatar avatar;
	@FXML
	private ImageView header;
	@FXML
	private MFXTextField firstname;
	@FXML
	private MFXTextField lastname;
	@FXML
	private JFXTextArea bio;
	@FXML
	private MFXTextField city;
	@FXML
	private MFXTextField website;
	@FXML
	private AnchorPane chooseImagePanel;

	public void initialize() {
		header.setImage(Client.user.getHeader());
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
		chooseImagePanel.setVisible(true);
	}
	@FXML
	void changeAvatar(MouseEvent e) throws IOException {

	}

	@FXML
	void cancel(MouseEvent e) {
		chooseImagePanel.setVisible(false);
		chooseImagePanel.setManaged(false);
	}

	@FXML
	void chooseImageButton(MouseEvent e) {
		FileChooser f = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png");
		f.getExtensionFilters().add(extFilter);
//		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow(); which one is better?
		Stage stage = new Stage();
		stage.setTitle("select picture");
		File file = f.showOpenDialog(stage);
		if (file == null) return;
		Image image = new Image (file.toURI().toString(), 285, 149, false, true, true);
		header.setImage(image);
	}
}