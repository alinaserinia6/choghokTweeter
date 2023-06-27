package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DirectController {

    @FXML
    private MFXScrollPane sp;
    @FXML
    private Avatar avatar;
    private Thread thread;
    private boolean shutdown = false;

    public void initialize() throws IOException {
        avatar.setImage(Client.user.getAvatar());
        VBox directs = new VBox();
        sp.setContent(directs);
        for (Direct direct : Client.directs.values()) {
            Platform.runLater(()-> {
                try {
                    directs.getChildren().add(0, direct.toPane());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        thread = new Thread(() -> {
            while (!shutdown) {
                try {
                    Client.out.writeObject(new Request(RM.GET_MASSAGES));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("in while");
                getMassage();
                try {
                    Thread.sleep(4000); // time of sleep between get tweet
                } catch (InterruptedException e) {
                    System.err.println("\t\t\t{interrupted}");
                    break;
                }
            }
            System.out.println("finish thread");
        });
        thread.start();
    }

    private void getMassage() {
        System.out.println("\t".repeat(7) + "{getMassage}");
        Object o = Client.getObject();
        if (o instanceof Massage) {
            System.out.println(((Massage) o).getText());
        } else {
            System.out.println("\u001B[32m" + "\t".repeat(7) + "{" + o.getClass() + "}\u001B[0m");
        }
        System.err.println("\t".repeat(7) + "{Finish}");
    }

    @FXML
    void setting(MouseEvent e) throws IOException {
        shutdown = true;
        thread.interrupt();
        HelloApplication.ChangePage(e, "a7");
    }

    @FXML
    void searchButton(MouseEvent e) throws IOException {
        shutdown = true;
        thread.interrupt();
        HelloApplication.ChangePage(e, "a8");
    }

    @FXML
    void notificationButton(MouseEvent e) throws IOException{
        shutdown = true;
        thread.interrupt();
        HelloApplication.ChangePage(e, "aNotification");
    }

    @FXML
    void timeLineButton(MouseEvent e) throws IOException {
        shutdown = true;
        thread.interrupt();
        HelloApplication.ChangePage(e, "a5");
    }

}
