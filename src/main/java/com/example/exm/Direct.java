package com.example.exm;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Direct {
	private User user;
	private ArrayList<Massage> Massages;
	private WriteMassageController controller;

	public Direct(User user) {
		this.user = user;
		Massages = new ArrayList<>();
		controller = new WriteMassageController();
	}

	public Pane toPane() throws IOException {
		ShowDirectController controller = new ShowDirectController();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showDirect.fxml"));
		fxmlLoader.setController(controller);
		Pane p = fxmlLoader.load();
		System.out.println((p.getChildren().get(0))); // TODO WTF?
		controller.build(this);
		return p;
	}

	public User getUser() {
		return user;
	}

	public ArrayList<Massage> getMassages() {
		return Massages;
	}

	public WriteMassageController getController() {
		return controller;
	}
}
