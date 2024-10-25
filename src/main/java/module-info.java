module com.example.economicdevelopment {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.economicdevelopment to javafx.fxml;
    exports com.example.economicdevelopment;
}