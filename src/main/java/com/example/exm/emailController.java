package com.example.exm;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    private final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @FXML
    public void emailAction(ActionEvent e) throws IOException {
        if (email.getText().isEmpty()) {
            error.setText("آدرس ایمیل نمیتواند خالی باشد");
            error.setVisible(true);
            return;
        }
        if (!Pattern.compile(EMAIL_PATTERN).matcher(email.getText()).matches()) {
            error.setText("آدرس ایمیل نا معتبر است");
            error.setVisible(true);
            return;
        }

        HelloApplication.ChangePage(e, "a3");

    }
}

class EmailValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    public boolean validate(final String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}