package org.example;

import org.example.model.AbstractPhilosopher;
import org.example.model.philosopher.*;
import org.example.timer.TimeMeasurement;

import java.util.*;

public class Simulation {
    private static final int numberOfPhilosophers =5    ;
    private static final int numberOfSimulations = 1;
    private static final int durationOfSimulationInMs = 5000;

    public static void main (String[] args) {
        Simulation simulation = new Simulation();

        List<Class<? extends AbstractPhilosopher>> classes = List.of(
                PhilosopherTask2.class,
                PhilosopherTask3.class,
                PhilosopherTask4.class,
                PhilosopherTask5.class,
                PhilosopherTask6.class);
        /* This map contains:
        Key: Class of philosopher
        Value: Map where key is philosopher id and value is list of average times waited for each simulation */
        ResultsCollector resultsCollector = new ResultsCollector(numberOfSimulations);
        Map<Class<? extends AbstractPhilosopher>, Map<Integer, List<Double>>>
                resultsOfSimulation = resultsCollector.collectResultsOfSimulations(classes, simulation);

        BoxPlot.createBoxPlot(resultsOfSimulation);
    }

    public Map<Integer, Double> run(Class<? extends AbstractPhilosopher> philosopherClass) {
        System.out.println("Running simulation for: " + philosopherClass.getName());

        PhilosophersInitializer initializer = new PhilosophersInitializer(numberOfPhilosophers);
        List<AbstractPhilosopher> philosophers = initializer.initPhilosophers(philosopherClass);

        philosophers.forEach(Thread::start);

        interruptThreads(philosophers);

        return TimeMeasurement.getAverageTimesAndReset();
    }

    private static void interruptThreads(List<? extends Thread> threads) {
        try {
            Thread.sleep(durationOfSimulationInMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threads.forEach(Thread::interrupt);
    }
}
