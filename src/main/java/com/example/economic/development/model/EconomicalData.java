package com.example.economic.development.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EconomicalData {

    private Double gdp;
    private Double unemployment;
    private Double inflation;
}
