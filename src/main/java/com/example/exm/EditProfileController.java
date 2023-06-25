package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import com.jfoenix.controls.JFXTextArea;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
	private MFXTextField location;
	@FXML
	private MFXTextField website;

	public void initialize() {
		header.setImage(Client.user.getHeader());
		avatar.setImage(Client.user.getAvatar());
		firstname.setText(Client.user.getFirstName());
		lastname.setText(Client.user.getLastName());
		bio.setText(Client.user.getBio());
		location.setText(Client.user.getLocation());
		website.setText(Client.user.getWebsite());
	}

	@FXML
	void backButton(MouseEvent e) throws IOException {
		HelloApplication.ChangePage(e, "a7");
	}

	@FXML
	void saveButton(MouseEvent e) throws IOException {
		// TODO MAKE CLASS TO CHANGE CLIENT AND SERVER
		HelloApplication.ChangePage(e, "a7");
	}

	@FXML
	void changeHeader(MouseEvent e) throws IOException {
		// TODO make javafx window for select or give path
		FileChooser f = new FileChooser();
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		File file = f.showOpenDialog(stage);
		System.out.println(file.getAbsolutePath());
	}
	@FXML
	void changeAvatar(MouseEvent e) throws IOException {

	}
}