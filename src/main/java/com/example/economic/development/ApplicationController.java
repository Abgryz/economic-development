package com.example.economic.development;

import com.example.economic.development.data.DataMerger;
import com.example.economic.development.model.CountryData;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class ApplicationController {

    @FXML
    private TableColumn<CountryData, String> countryCode;

    @FXML
    private TableView<CountryData> countryTable;

    @FXML
    private TableColumn<CountryData, String> countyName;

    @FXML
    private TableColumn<CountryData, Double> gdp;

    @FXML
    private TableColumn<CountryData, Double> inflation;

    @FXML
    private TableColumn<CountryData, Double> unemployment;

    @FXML
    private Button yearButton;

    @FXML
    private TextField yearField;

    private Integer selectedYear = 2023;

    @FXML
    public void initialize() {
        countyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryCode.setCellValueFactory(new PropertyValueFactory<>("code"));

        initTableColumns();
        countryTable.setItems(DataMerger.loadData());

        yearButton.setOnAction(event -> onYearButtonClick());
    }

    private void onYearButtonClick() {

        var yearText = yearField.getText();
        if (!yearText.isEmpty() && yearText.matches("\\d+")) {
            selectedYear = Integer.parseInt(yearText); // Оновлюємо обраний рік
            initTableColumns();
            countryTable.refresh(); // Оновлюємо відображення таблиці
        }
    }

    private void initTableColumns() {

        gdp.setCellValueFactory(cellData -> {
            var countryData = cellData.getValue();
            return Optional.ofNullable(countryData.getYearData(selectedYear))
                    .map(data -> new SimpleDoubleProperty(data.getGdp()).asObject())
                    .orElse(null);
        });

        inflation.setCellValueFactory(cellData -> {
            var countryData = cellData.getValue();
            return Optional.ofNullable(countryData.getYearData(selectedYear))
                    .map(data -> new SimpleDoubleProperty(data.getInflation()).asObject())
                    .orElse(null);
        });

        unemployment.setCellValueFactory(cellData -> {
            var countryData = cellData.getValue();
            return Optional.ofNullable(countryData.getYearData(selectedYear))
                    .map(data -> new SimpleDoubleProperty(data.getUnemployment()).asObject())
                    .orElse(null);
        });
    }
}
