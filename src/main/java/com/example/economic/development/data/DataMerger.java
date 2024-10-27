package com.example.economic.development.data;

import com.example.economic.development.model.CountryData;
import com.example.economic.development.model.EconomicalData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class DataMerger {

    public static final LinkedHashMap<String, CountryData> COUNTRY_DATA = new LinkedHashMap<>();

    public static ObservableList<CountryData> loadData() {

        var csvLoader = new CsvLoader("src/main/resources");
        var gdpData = csvLoader.loadCSV("gdp.csv");
        var unemploymentData = csvLoader.loadCSV("unemployment.csv");
        var inflationData = csvLoader.loadCSV("inflation.csv");

        for (int i = 1; i < gdpData.size(); i++) {

            var countryData = new CountryData(gdpData.get(i)[0], gdpData.get(i)[1]);

            for (int j = 2; j < gdpData.get(i).length; j++) {
                var gdp = gdpData.get(i)[j];
                var unemployment = unemploymentData.get(i)[j];
                var inflation = inflationData.get(i)[j];

                countryData.addYearData(Integer.parseInt(gdpData.get(0)[j]), new EconomicalData(
                        gdp.isEmpty() ? null : Double.parseDouble(gdp),
                        unemployment.isEmpty() ? null : Double.parseDouble(unemployment),
                        inflation.isEmpty() ? null : Double.parseDouble(inflation)
                ));
            }

            COUNTRY_DATA.put(countryData.getName(), countryData);
        }

        return FXCollections.observableList(COUNTRY_DATA.values().stream().toList());
    }
}
