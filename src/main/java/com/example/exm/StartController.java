package com.example.exm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class StartController {
    @FXML
    public void letUsGo(ActionEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a2");
    }

}
