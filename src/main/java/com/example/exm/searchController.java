package com.example.exm;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.*;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class searchController {
    @FXML
    private TextField searchBox;
    @FXML
    private ImageView avatar;
    @FXML
    private MFXScrollPane shashtag;
    @FXML
    private MFXScrollPane sprofile;

    public void initialize() throws IOException { // TODO get all online
        System.out.println("GET_HASHTAG");
        Client.out.writeObject(new Request(RM.GET_HASHTAG));
        sprofile.setContent(Client.contacts);
        VBox v = new VBox();
        shashtag.setContent(v);
        HashMap<String, Integer> hashtagList = (HashMap<String, Integer>) Client.getObject();
        Object[] a = hashtagList.entrySet().toArray();
        Arrays.sort(a, (Comparator) (o1, o2) -> ((Map.Entry<String, Integer>) o2).getValue()
                .compareTo(((Map.Entry<String, Integer>) o1).getValue()));
        for (Object e : a) {
            String name = ((Map.Entry<String, Integer>) e).getKey();
            Integer num = ((Map.Entry<String, Integer>) e).getValue();
            Platform.runLater(() -> {
                ShowHashtagController controller = new ShowHashtagController();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showHashtag.fxml"));
                fxmlLoader.setController(controller);
                Pane p;
                try {
                    p = fxmlLoader.load();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                controller.build(name, num);
                v.getChildren().add(p);
            });
        }

    }

    @FXML
    void timeLineButton(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a5");
    }

    @FXML
    void setting(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "a7");
    }

    @FXML
    void notificationButton(MouseEvent e) throws IOException{
        HelloApplication.ChangePage(e, "aNotification");
    }

    @FXML
    void directButton(MouseEvent e) throws IOException {
        HelloApplication.ChangePage(e, "aDirect");
    }

}
