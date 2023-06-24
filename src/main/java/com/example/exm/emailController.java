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
        String key = email.getText();
        if (key.isEmpty()) {
            error.setText("The email address cannot be empty");
            error.setVisible(true);
            return;
        }
        if (!Pattern.compile(EMAIL_PATTERN).matcher(key).matches()) {
            error.setText("The email address is invalid");
            error.setVisible(true);
            return;
        }
        Request r = new Request(RM.DUPLICATE_KEY, key);
        Client.out.writeObject(r);
        boolean b = (boolean) Client.getObject();
        if (b) {
            error.setText("The email entered has already been registered");
            error.setVisible(true);
            return;
        }
        Client.user.setEmail(email.getText());
        HelloApplication.ChangePage(e, "a3");
    }
    public void backnumber(ActionEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a2");
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