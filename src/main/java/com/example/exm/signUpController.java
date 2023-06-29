package com.example.exm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Pattern;


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

    private final Image defaultHeader = new Image(getClass().getResource("Pheader.png").toString());

    @FXML
    public void singUpbuttonAction(ActionEvent e) throws IOException { // TODO uncomment these text ۷
        if (firstName.getText().isEmpty()) {
            singUpError.setText("The first name must not be empty");
            singUpError.setVisible(true);
            return;
        }
        if (lastName.getText().isEmpty()) {
            singUpError.setText("The last name must not be empty");
            singUpError.setVisible(true);
            return;
        }
        if (userName.getText().isEmpty()) {
            singUpError.setText("The username must not be empty");
            singUpError.setVisible(true);
            return;
        }
        if (password.getText().isEmpty()) {
            singUpError.setText("The password must not be empty");
            singUpError.setVisible(true);
            return;
        }
        if (repeatPassword.getText().isEmpty()) {
            singUpError.setText("The repeat password must not be empty");
            singUpError.setVisible(true);
            return;
        }
        if (birthDate.getValue() == null) {
            singUpError.setText("The Date of birth must not be empty");
            singUpError.setVisible(true);
            return;
        }
        if (!password.getText().equals(repeatPassword.getText())) {
            singUpError.setText("Repeat password is not correct");
            singUpError.setVisible(true);
            return;
        }
        if (!checkPassword(password.getText())) {
            singUpError.setText("The password is invalid");
            singUpError.setVisible(true);
            return;
        }
        Request r = new Request(RM.DUPLICATE_ID, userName.getText());
        Client.out.writeObject(r);
        boolean b = (boolean) Client.getObject();
        if (b) {
            singUpError.setText("Username is already in use");
            singUpError.setVisible(true);
            return;
        }
        String birth = birthDate.getValue().format(DateTimeFormatter.ofPattern("MMM dd"));
        Client.user = new User(firstName.getText(), lastName.getText(), userName.getText(), LocalDateTime.now(), password.getText(), birth);
        Client.user.setHeader(defaultHeader);
        if (Pattern.compile("-?\\d+(\\.\\d+)?").matcher(Client.key).matches()) {
            Client.user.setPhoneNumber(Client.key);
        } else {
            Client.user.setEmail(Client.key);
        }
        r = new Request(RM.ADD_USER, Client.user, Client.key);
        Client.out.writeObject(r);
        r = new Request(RM.DISCOVER_USERS, Client.LAST_USER_SEEN);
        Client.out.writeObject(r);
        Client.LAST_USER_SEEN = (LocalDateTime) Client.getObject();
        ArrayList<User> userList = (ArrayList<User>) Client.getObject();
        for (User u : userList) {
            System.out.println(u.getUsername());
            Client.contacts.getChildren().add(u.usertoPane(Client.user.following.get(u.getUsername())));
        }
        HelloApplication.ChangePage(e, "a5");
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
