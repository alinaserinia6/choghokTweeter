package com.example.exm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.ObjectOutputStream;
import java.util.*;

public class HelloController {
    private ObjectOutputStream out;

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    private User user = new User();
    private Scene scene;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @FXML
    private ImageView icon;
    @FXML
    private TextField phoneNumber;
    @FXML
    private CheckBox acceptRule;
    @FXML
    private Label shouldAcceptRule;

    private String [] ISO = Locale.getISOCountries();
    private ObservableList observOfISO;
    @FXML
    private ComboBox<String> phoneNumberCountry = new ComboBox<>();

    private Image Ico = new Image(getClass().getResourceAsStream("icon.png"));

    public void initialize() {
        ArrayList<String> c = new ArrayList<>();
        for (int i = 0; i < ISO.length; i++) {
            ISO[i] = PhoneNumberCountry.res(ISO[i]);
            if (ISO[i].equals("error")) continue;
            c.add(ISO[i]);
        }
        observOfISO = FXCollections.observableArrayList(c);
        phoneNumberCountry.setItems(observOfISO);
        icon.setImage(Ico);
        System.out.println("initialize");
    }

    @FXML
    public void nexti(ActionEvent e) {
        if (phoneNumberCountry.getSelectionModel().isEmpty()) {
            shouldAcceptRule.setText("کشور خود را وارد کنید");
            shouldAcceptRule.setVisible(true);
            return;
        }
        if (phoneNumber.getText().length() != 10) {
            shouldAcceptRule.setText("شماره همراه باید ۱۰ رقمی باشد");
            shouldAcceptRule.setVisible(true);
            return;
        }
        if (!acceptRule.isSelected()) {
            shouldAcceptRule.setText("باید با سیاست های فوق امنیتی چغک موافقت کنید");
            shouldAcceptRule.setVisible(true);
            return;
        }
        user.setPhoneNumber(phoneNumberCountry.getValue() + phoneNumber.getText());

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view-with-gridpane.fxml"));
        AnchorPane anchorPane = new AnchorPane();
        fxmlLoader.setRoot(anchorPane);
        ((Node) e.getSource()).getScene().setRoot(fxmlLoader.getRoot());
//        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
//        scene.setRoot(anchorPane);
//        scene = new Scene(fxmlLoader.getRoot());
//        stage.setScene(scene);
//        stage.show();

    }

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