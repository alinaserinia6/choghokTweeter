package com.example.exm;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.format.DateTimeFormatter;

public class ShowMassageController {
    @FXML
    private JFXTextArea text;
    @FXML
    private Label date;
    public void build(Massage massage) {
        text.setText(massage.getText());
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM dd HH:mm");
        date.setText(massage.getTime().format(format));
    }

}