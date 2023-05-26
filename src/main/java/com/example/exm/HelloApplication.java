package com.example.exm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application implements Runnable {
    private HelloController hi;
    private ModuleLayer.Controller h;

    public HelloController getHi() {
        return hi;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("a2.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        h = fxmlLoader.getController();
        stage.setTitle("Choghok");
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
}