module com.example.exm {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.exm to javafx.fxml;
    exports com.example.exm;
}