package com.example.economic.development.data;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvLoader {
    private String directory;

    public CsvLoader(String directory) {
        this.directory = directory;
    }

    public List<String[]> loadCSV(String filePath) {
        filePath = directory + "/" + filePath;
        List<String[]> data = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                data.add(line);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return data;
    }
}
