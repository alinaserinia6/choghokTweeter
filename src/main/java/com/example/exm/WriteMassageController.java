package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import com.jfoenix.controls.JFXTextArea;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;

public class WriteMassageController {
	@FXML
	private ImageView avatar;
	@FXML
	private Label name;
	@FXML
	private Label username;
	@FXML
	private MFXScrollPane sp;
	@FXML
	private JFXTextArea text;
	private Direct direct;
	private static VBox massages;
	private Image image = new Image(Server.class.getResource("PchoghockIcon.png").toString());

	public WriteMassageController() {
		massages = new VBox();
	}

	public void initialize() throws IOException {
		User user = direct.getUser();
		avatar.setImage(user.getAvatar());
		name.setText(user.getFirstName() + " " + user.getLastName());
		username.setText(user.getUsername());
		sp.setContent(massages);
		sp.setVvalue(1.0);
	}

	public void addMassage(Massage massage) {
		Platform.runLater(() -> {
			try {
				HBox h = new HBox();
				ImageView icon = new ImageView();
				icon.setImage(image);
				icon.setFitHeight(40);
				icon.setFitWidth(40);
				h.getChildren().add(massage.toPane());
				h.getChildren().add(icon);
				if (massage.getUsername().equals(Client.user.getUsername())) {
					h.setAlignment(Pos.CENTER_RIGHT);
					System.out.print("right -> ");
				} else {
					h.setAlignment(Pos.CENTER_LEFT);
					System.out.print("left -> ");
				}
				massages.getChildren().add(h);
				massages.setSpacing(10);
				System.out.println("massage " + massage.getText() + " is added to VBox!");
			} catch (IOException e) {
				System.err.println("cant add massage to VBox");
			}
		});
	}

	@FXML
	void sendMassageButton(MouseEvent e) throws IOException {
		Massage massage = new Massage(Client.user.getUsername(), text.getText(), LocalDateTime.now());
		direct.getMassages().add(massage);
		User user = direct.getUser();
		Client.out.writeObject(new Request(RM.DIRECT_MASSAGE, user, massage));
		Client.directs.put(user.getUsername(), direct);
		ImageView icon = new ImageView();
		icon.setImage(image);
		icon.setFitHeight(40);
		icon.setFitWidth(40);
		Region region = new Region();
		HBox.setHgrow(region, Priority.ALWAYS);
		HBox h = new HBox(massage.toPane(), region, icon);
		h.setAlignment(Pos.CENTER_RIGHT);
		Platform.runLater(() -> {
			massages.getChildren().add(h);
			h.setAlignment(Pos.CENTER_RIGHT);
			sp.setVvalue(1.0);
		});
		text.setText("");
	}
	@FXML
	void backButton(MouseEvent e) throws IOException {
		HelloApplication.ChangePage(e, "aDirect");
	}

	public void setDirect(Direct direct) {
		this.direct = direct;
	}
}
