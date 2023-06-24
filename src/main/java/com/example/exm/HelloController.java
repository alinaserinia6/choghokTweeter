package com.example.exm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.*;

import static java.lang.Thread.sleep;

public class HelloController {

    @FXML
    private ImageView icon;
    @FXML
    private TextField phoneNumber;
    @FXML
    private Button nextButton;
    @FXML
    private CheckBox acceptRule;
    @FXML
    private Label emailChoose;
    @FXML
    private Label error;

    private String [] ISO = Locale.getISOCountries();
    private ObservableList observOfISO;
    @FXML
    private ComboBox<String> phoneNumberCountry = new ComboBox<>();


    public void initialize() {
        ArrayList<String> c = new ArrayList<>();
        for (int i = 0; i < ISO.length; i++) {
            ISO[i] = PhoneNumberCountry.res(ISO[i]);
            if (ISO[i].equals("error")) continue;
            c.add(ISO[i]);
        }
        observOfISO = FXCollections.observableArrayList(c);
        phoneNumberCountry.setItems(observOfISO);
        System.out.println("initialize");
    }

    @FXML
    public void nexti(ActionEvent e) throws IOException {
        if (phoneNumberCountry.getSelectionModel().isEmpty()) {
            error.setText("Enter your country");
            error.setVisible(true);
            return;
        }
        if (phoneNumber.getText().length() != 10) {
            error.setText("Mobile number must be 10 digits");
            error.setVisible(true);
            return;
        }
        if (!acceptRule.isSelected()) {
            error.setText("You must agree to the security policies of Choghok");
            error.setVisible(true);
            return;
        }
        String key = phoneNumberCountry.getValue() + phoneNumber.getText();
        Request r = new Request(RM.DUPLICATE_KEY, key);
        Client.out.writeObject(r);
        boolean b = (boolean) Client.getObject();
        if (b) {
            error.setText("The entered phone number is already registered");
            error.setVisible(true);
            return;
        }
        Client.user.setPhoneNumber(key);
        HelloApplication.ChangePage(e, "a3");
    }

    @FXML
    public void emailClicked(MouseEvent e) throws IOException {
        System.out.println("clicked email");
        HelloApplication.ChangePage(e, "a2email");
    }

    public void asingin(ActionEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a4");

    }

}