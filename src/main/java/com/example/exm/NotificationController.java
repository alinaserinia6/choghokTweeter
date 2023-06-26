package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class NotificationController {

	@FXML
	private MFXScrollPane sp;
	@FXML
	private Avatar avatar;

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

}