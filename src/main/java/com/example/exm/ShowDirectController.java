package com.example.exm;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ShowDirectController {
	@FXML
	private Label detail;
	@FXML
	private Label text;
	@FXML
	private ImageView avatar;

	private Direct direct;

	public void build(Direct direct) {
		this.direct = direct;
		User user = direct.getUser();
		Massage massage = direct.getMassages().get(direct.getMassages().size() - 1);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy MMM");
		String detail = user.getFirstName() + " " + user.getLastName() + " " + user.getUsername() + " " + massage.getTime().format(format);
		this.detail.setText(detail);
		this.avatar.setImage(user.getAvatar());
		this.text.setText(massage.getText());
	}

	@FXML
	void writeMassagePage(MouseEvent e) throws IOException {
		WriteMassageController controller = direct.getController();
		Stage stage = HelloApplication.getStage();
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
}
