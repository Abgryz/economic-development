package com.example.economic.development;

import com.example.economic.development.clasterisation.ClusterAnalysis;
import com.example.economic.development.data.DataMerger;
import com.example.economic.development.model.CountryData;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApplicationController {

    @FXML
    private TableView<CountryData> countryTable;

    @FXML
    private TableColumn<CountryData, String> countryCode;

    @FXML
    private TableColumn<CountryData, String> countyName;

    @FXML
    private TableColumn<CountryData, Double> gdp;

    @FXML
    private TableColumn<CountryData, Double> inflation;

    @FXML
    private TableColumn<CountryData, Double> unemployment;


    @FXML
    private TableView<Map.Entry<Integer, String>> clusterTable;

    @FXML
    private Button clusterButton;

    @FXML
    private TextField yearField;

    @FXML
    private TextField clusterCount;

    private Integer selectedYear = 2023;

    @FXML
    public void initialize() {
        countyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryCode.setCellValueFactory(new PropertyValueFactory<>("code"));

        initTableColumns();
        countryTable.setItems(DataMerger.loadData());

        clusterButton.setOnAction(event -> onClusterButtonClick());
    }

    private void onClusterButtonClick() {

        var errorDialog = new ErrorDialog("Помилка", "Помилка валідації даних");
        selectedYear = getNumberValue(yearField);
        if (selectedYear < 1960 || selectedYear > 2023) {
            errorDialog.show("Введіть коректний рік");
            return;
        }

        initTableColumns();
        countryTable.refresh();

        var clusterAnalysis = new ClusterAnalysis();
        var selectedClusterCount = getNumberValue(clusterCount);
        if (selectedClusterCount <= 1) {
            errorDialog.show("Введіть коректну кількість кластерів");
            return;
        }
        var clusters = clusterAnalysis.clusterCountriesHierarchically(DataMerger.getCountryData(), selectedYear, selectedClusterCount);
        createClusterTable(clusters);
    }

    private void initTableColumns() {

        gdp.setCellValueFactory(cellData -> {
            var countryData = cellData.getValue();
            var economicalData = countryData.getYearData(selectedYear);
            if (economicalData != null && economicalData.getGdp() != 0) {
                return new SimpleDoubleProperty(economicalData.getGdp()).asObject();
            }
            return null;
        });

        inflation.setCellValueFactory(cellData -> {
            var countryData = cellData.getValue();
            var economicalData = countryData.getYearData(selectedYear);
            if (economicalData != null && economicalData.getInflation() != 0) {
                return new SimpleDoubleProperty(economicalData.getInflation()).asObject();
            }
            return null;
        });

        unemployment.setCellValueFactory(cellData -> {
            var countryData = cellData.getValue();
            var economicalData = countryData.getYearData(selectedYear);
            if (economicalData != null && economicalData.getUnemployment() != 0) {
                return new SimpleDoubleProperty(economicalData.getUnemployment()).asObject();
            }
            return null;
        });
    }

    private int getNumberValue(TextField text) {
        var value = text.getText();
        if (!value.isEmpty() && value.matches("\\d+")) {
            return Integer.parseInt(value);
        }
        return 0;
    }

    private void createClusterTable(Map<Integer, List<CountryData>> clusterMap) {

        clusterTable.getColumns().clear();
        setClusterTableColumns(clusterMap);

        int maxRows = clusterMap.values().stream()
                .mapToInt(List::size)
                .max()
                .orElse(0);

        List<Map.Entry<Integer, String>> rows = new ArrayList<>(maxRows);
        for (int i = 0; i < maxRows; i++) {
            rows.add(new AbstractMap.SimpleEntry<>(i, ""));
        }

        clusterTable.getItems().setAll(rows);
    }

    private void setClusterTableColumns(Map<Integer, List<CountryData>> clusterMap){

        for (var entry : clusterMap.entrySet()) {
            var clusterId = entry.getKey();
            var countries = entry.getValue();

            TableColumn<Map.Entry<Integer, String>, String> clusterColumn = new TableColumn<>("Кластер " + (clusterId + 1));
            clusterColumn.setCellValueFactory(cellData -> {
                int rowIndex = clusterTable.getItems().indexOf(cellData.getValue());
                if (rowIndex >= 0 && rowIndex < countries.size()) {
                    return new SimpleStringProperty(countries.get(rowIndex).getName());
                } else {
                    return new SimpleStringProperty("");
                }
            });

            clusterTable.getColumns().add(clusterColumn);
        }
    }
}
