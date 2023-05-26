package com.example.exm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class signUpController {
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label userName;
    @FXML
    private Label email;
    @FXML
    private Label password;
    @FXML
    private Label repeatPassword;
    @FXML
    private Label birthDate;
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
        if (birthDate.getText().isEmpty()) {
            singUpError.setText("تاریخ تولد نباید خالی باشد");
            singUpError.setVisible(true);
            return;
        }

    }
}
