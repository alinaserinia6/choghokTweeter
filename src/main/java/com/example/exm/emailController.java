package com.example.exm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class emailController {
    @FXML
    private TextField email;
    @FXML
    private Label error;

    @FXML
    public void emailAction(ActionEvent e) throws IOException {
        if (email.getText().isEmpty()) {
            error.setText("آدرس ایمیل نمیتواند خالی باشد");
            error.setVisible(true);
            return;
        }
        String email = "example@email.com";
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        if (!email.matches(regex)) {
            error.setText("آدرس ایمیل نامعتبر است");
            error.setVisible(true);
            return;

        }

        HelloApplication.ChangePage(e, "a3");

    }
}