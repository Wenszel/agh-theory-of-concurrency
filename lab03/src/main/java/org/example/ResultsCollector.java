package org.example;

import org.example.model.AbstractPhilosopher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResultsCollector {
    private final int numberOfSimulations;

    public ResultsCollector(int numberOfSimulations) {
        this.numberOfSimulations = numberOfSimulations;
    }

    public Map<Class<? extends AbstractPhilosopher>, Map<Integer, List<Double>>> collectResultsOfSimulations
            (List<Class<? extends AbstractPhilosopher>> classes, Simulation simulation) {

        Map<Class<? extends AbstractPhilosopher>, Map<Integer, List<Double>>> resultsOfSimulations = new LinkedHashMap<>();

        classes.forEach(clazz -> {
            Map<Integer, List<Double>> listOfResultsForEachPhilosopher = new LinkedHashMap<>();

            for (int i = 0; i < numberOfSimulations; i++) {
                Map<Integer, Double> averageTimeWaitedForEachPhilosopher = simulation.run(clazz);
                putResultsOfPhilosopherToListOfAllResults(averageTimeWaitedForEachPhilosopher, listOfResultsForEachPhilosopher);
            }

            resultsOfSimulations.put(clazz, listOfResultsForEachPhilosopher);
        });
        return resultsOfSimulations;
    }

    public void putResultsOfPhilosopherToListOfAllResults(Map<Integer, Double> averageTimeWaitedForEachPhilosopher,
                                                          Map<Integer, List<Double>> listOfResultsForEachPhilosopher) {
        averageTimeWaitedForEachPhilosopher.forEach((key, value) -> {
            if (listOfResultsForEachPhilosopher.containsKey(key)) {
                listOfResultsForEachPhilosopher.get(key).add(value);
            } else {
                List<Double> list = new ArrayList<>();
                list.add(value);
                listOfResultsForEachPhilosopher.put(key, list);
            }
        });
    }
}
