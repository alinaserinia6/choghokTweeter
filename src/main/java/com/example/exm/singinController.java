package com.example.exm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class singinController {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label error;

    @FXML
    public void singInbuttonAction(ActionEvent e) throws IOException {
        if (username.getText().isEmpty()) {
            error.setText("نام کاربری نباید خالی باشد");
            error.setVisible(true);
            return;
        }
        if (password.getText().isEmpty()) {
            error.setText("گذرواژه نباید خالی باشد");
            error.setVisible(true);
            return;
        }
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("a1.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}