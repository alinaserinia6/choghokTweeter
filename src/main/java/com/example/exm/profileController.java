package com.example.exm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class profileController {
    @FXML
    private ImageView IM;
    @FXML
    void backtotimeline(ActionEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a5");
    }
}
