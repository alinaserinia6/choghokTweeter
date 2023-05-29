package com.example.exm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class signUpController {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField userName;
    @FXML
    private TextField password;
    @FXML
    private TextField repeatPassword;
    @FXML
    private DatePicker birthDate;
    @FXML
    private Label singUpError;

    @FXML
    public void singUpbuttonAction(ActionEvent e) throws IOException {
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
        if (birthDate.getValue() == null) {
            singUpError.setText("تاریخ تولد نباید خالی باشد");
            singUpError.setVisible(true);
            return;
        }
        if (!password.getText().equals(repeatPassword.getText())) {
            singUpError.setText("تکرار گذر واژه صحیح نیست");
            singUpError.setVisible(true);
            return;
        }
        if (!checkPassword(password.getText())) {
            singUpError.setText("گذرواژه نامعتبر است");
            singUpError.setVisible(true);
            return;
        }
        Request r = new Request(RM.DUPLICATE_ID, userName.getText());
        Client.out.writeObject(r);
        boolean b = (boolean) Client.getObject();
        if (b) {
            singUpError.setText("نام کاربری قبلا استفاده شده است");
            singUpError.setVisible(true);
            return;
        }

        HelloApplication.ChangePage(e, "a5");

        Client.user.setFirstName(firstName.getText());
        Client.user.setLastName(lastName.getText());
        Client.user.setUsername(userName.getText());
        String date = new SimpleDateFormat("yyy MM").format(new Date());
        Client.user.setJoinDate(date);
        Client.user.setUsername(userName.getText());
        Client.user.setPassword(password.getText());
        String birth = birthDate.getValue().format(DateTimeFormatter.ofPattern("MMM dd"));
        Client.user.setBirthDate(birth);
        String key = Client.user.getEmail() == null ? Client.user.getPhoneNumber() : Client.user.getEmail();
        r = new Request(RM.ADD_USER, Client.user, key);
        Client.out.writeObject(r);
    }

    public void back(ActionEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a2");
    }
    public static boolean checkPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            }
            if (hasUppercase && hasLowercase) {
                return true;
            }
        }
        return false;

    }


}
