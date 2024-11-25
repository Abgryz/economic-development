package com.example.economic.development.clasterisation;

import com.example.economic.development.model.CountryData;
import smile.clustering.HierarchicalClustering;
import smile.clustering.linkage.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ClusterAnalysis {

    public Map<Integer, List<CountryData>> clusterCountriesHierarchically(List<CountryData> countryDataList, int year, int clustersCount) {
        var data = getData(countryDataList, year);

        var proximity = Linkage.proximity(data);
        Linkage linkage = new WardLinkage(data.length, proximity);
        HierarchicalClustering hc = HierarchicalClustering.fit(linkage);

        int[] clusters = hc.partition(clustersCount);
        System.out.println("Кластери (ієрархічна кластеризація):");
        for (int i = 0; i < countryDataList.size(); i++) {
            System.out.println(countryDataList.get(i).getName() + " -> Кластер " + clusters[i]);
        }

        return clusterCountries(clusters, countryDataList);
    }

    private double[][] getData(List<CountryData> countryDataList, int year) {
        return countryDataList.stream()
                .map(country -> {
                    var economicalData = country.getYearData(year);
                    if (economicalData != null) {
                        return new double[]{
                                economicalData.getGdp(),
                                economicalData.getInflation(),
                                economicalData.getUnemployment()
                        };
                    } else {
                        return new double[]{0.0, 0.0, 0.0}; // Якщо даних для року немає
                    }
                })
                .toArray(double[][]::new);
    }

    private Map<Integer, List<CountryData>> clusterCountries(int[] clusters, List<CountryData> countryDataList) {
        return IntStream.range(0, countryDataList.size())
                .boxed()
                .collect(Collectors.groupingBy(
                        i -> clusters[i],
                        Collectors.mapping(countryDataList::get, Collectors.toList())
                ));
    }
}
