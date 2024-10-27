package com.example.economic.development.model;

import javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;

public class CountryData {

    private final SimpleStringProperty name;
    private final SimpleStringProperty code;
    private final HashMap<Integer, EconomicalData> yearData = new HashMap<>();

    public CountryData(String name, String code) {
        this.name = new SimpleStringProperty(name);
        this.code = new SimpleStringProperty(code);
    }

    public String getName() {
        return name.get();
    }

    public String getCode() {
        return code.get();
    }

    public void addYearData(int year, EconomicalData data) {
        yearData.put(year, data);
    }

    public EconomicalData getYearData(int year) {
        return yearData.get(year);
    }
}
