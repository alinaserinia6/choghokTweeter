package com.example.exm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
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
    private Button nextButton;
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
    public void nexti(ActionEvent e) throws IOException {
//        if (phoneNumberCountry.getSelectionModel().isEmpty()) {
//            shouldAcceptRule.setText("کشور خود را وارد کنید");
//            shouldAcceptRule.setVisible(true);
//            return;
//        }
//        if (phoneNumber.getText().length() != 10) {
//            shouldAcceptRule.setText("شماره همراه باید ۱۰ رقمی باشد");
//            shouldAcceptRule.setVisible(true);
//            return;
//        }
//        if (!acceptRule.isSelected()) {
//            shouldAcceptRule.setText("باید با سیاست های فوق امنیتی چغک موافقت کنید");
//            shouldAcceptRule.setVisible(true);
//            return;
//        }
        user.setPhoneNumber(phoneNumberCountry.getValue() + phoneNumber.getText());

        Stage stage = (Stage) nextButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("a3.fxml"));
//        nextButton.getScene().setRoot(root);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}