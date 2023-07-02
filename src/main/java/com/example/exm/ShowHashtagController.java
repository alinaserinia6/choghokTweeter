package com.example.exm;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ShowHashtagController {
    @FXML
    private Label name;
    @FXML
    private Label num;

    public void build(String name, Integer num) {
        this.name.setText(name);
        this.num.setText(String.valueOf(num));
    }
}
