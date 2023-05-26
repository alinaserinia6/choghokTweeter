package com.example.exm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    @FXML
    public void letUsGo(ActionEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a2.fxml");
    }
}
