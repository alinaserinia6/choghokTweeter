module com.example.exm {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
	requires com.jfoenix;
	requires com.gluonhq.charm.glisten;
	requires com.gluonhq.attach.util;
    requires MaterialFX;

    opens com.example.exm to javafx.fxml;
    exports com.example.exm;
}