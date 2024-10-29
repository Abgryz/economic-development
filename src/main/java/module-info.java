module com.example.economic.development {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.opencsv;
    requires static lombok;
    requires smile.core;

    opens com.example.economic.development.model to javafx.base;
    opens com.example.economic.development to javafx.fxml;
    exports com.example.economic.development;
}