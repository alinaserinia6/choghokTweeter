package com.example.exm;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class NotificationController {

	@FXML
	private MFXScrollPane sp;
	@FXML
	private ImageView avatar;

	public void initialize() {
		sp.setContent(Client.notification);
		avatar.setImage(Client.user.getAvatar());
	}

	@FXML
	void setting(MouseEvent e) throws IOException {
		HelloApplication.ChangePage(e, "a7");
	}

	@FXML
	void searchButton(MouseEvent e) throws IOException {
		HelloApplication.ChangePage(e, "a8");
	}
	@FXML
	void back1(MouseEvent e) throws IOException {
		HelloApplication.ChangePage(e, "a8");
	}

	@FXML
	void directButton(MouseEvent e) throws IOException {
		HelloApplication.ChangePage(e, "aDirect");
	}

	@FXML
	void timelineButton(MouseEvent e) throws IOException {
		HelloApplication.ChangePage(e, "a5");
	}



}
