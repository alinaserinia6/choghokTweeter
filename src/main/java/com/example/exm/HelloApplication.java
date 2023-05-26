package com.example.exm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application implements Runnable {
    private HelloController hi;

    public HelloController getHi() {
        return hi;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("a2.fxml"));
//        AnchorPane anchorPane = new AnchorPane();
//        fxmlLoader.setRoot(anchorPane);
        Scene scene = new Scene(fxmlLoader.load());
        hi = fxmlLoader.getController();
        stage.setTitle("Choghok");
        stage.setScene(scene);
        hi.setScene(scene);
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