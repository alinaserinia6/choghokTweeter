package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class searchController {
    @FXML
    private MFXTextField searchBox;
    @FXML
    private Avatar avatar;
    @FXML
    private MFXScrollPane sp;

    public void initialize() {
        sp.setContent(Client.contacts);
    }
    @FXML
    void timeLineButton(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a5");
    }

    @FXML
    void setting(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a7");
    }

}
