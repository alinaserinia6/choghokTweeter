package com.example.exm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.ObjectOutputStream;
import java.time.LocalDate;

public class signUpController {
    private Scene scene;
    private ObjectOutputStream out;

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField userName;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField repeatPassword;
    @FXML
    private DatePicker birthDate;
    @FXML
    private Label singUpError;

    @FXML
    public void singUpbuttonAction(ActionEvent e) {
        if (firstName.getText().isEmpty()) {
            singUpError.setText("نام نباید خالی باشد");
            singUpError.setVisible(true);
            return;
        }
        if (lastName.getText().isEmpty()) {
            singUpError.setText("نام خانوادگی نباید خالی باشد");
            singUpError.setVisible(true);
            return;
        }
        if (userName.getText().isEmpty()) {
            singUpError.setText("نام کاربری نباید خالی باشد");
            singUpError.setVisible(true);
            return;
        }
        if (email.getText().isEmpty()) {
            singUpError.setText("ایمیل نباید خالی باشد");
            singUpError.setVisible(true);
            return;
        }
        if (password.getText().isEmpty()) {
            singUpError.setText("گذرواژه نباید خالی باشد");
            singUpError.setVisible(true);
            return;
        }
        if (repeatPassword.getText().isEmpty()) {
            singUpError.setText("تکرار گذرواژه نباید خالی باشد");
            singUpError.setVisible(true);
            return;
        }
//        if (birthDate.().isEmpty()) {
//            singUpError.setText("تاریخ تولد نباید خالی باشد");
//            singUpError.setVisible(true);
//            return;
//        }

    }
}
