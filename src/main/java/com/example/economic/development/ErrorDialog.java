package com.example.economic.development;

import javafx.scene.control.Alert;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ErrorDialog {

    private final String title;
    private final String headerText;

    public void show(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }
}
