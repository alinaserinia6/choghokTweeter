package com.example.exm;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application implements Runnable {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("a1.fxml"));
        Parent myPane = fxmlLoader.load();
        myPane.setId("pane");//igyogyoigyi
        Scene scene = new Scene(myPane);
        stage.setTitle("Choghok");
        scene.getStylesheets().addAll(this.getClass().getResource("anchor.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void run() {
        Stage stage = new Stage();
        try {
            start(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public static void ChangePage(Event e, String fxmlFile) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource(fxmlFile + ".fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}