package org.example;

import org.example.model.AbstractPhilosopher;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoxPlot {
    public static void createBoxPlot(Map<Class<? extends AbstractPhilosopher>, Map<Integer, List<Double>>> results) {
        DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

        results.forEach((philosopherClass, philosopherResults) -> {
            List<Double> allTimes = new ArrayList<>();
            philosopherResults.values().forEach(allTimes::addAll);
            dataset.add(allTimes, philosopherClass.getSimpleName(), philosopherClass.getSimpleName());
        });

        JFreeChart chart = ChartFactory.createBoxAndWhiskerChart(
                "Average Wait Time by Philosopher Class",
                "Philosopher Class",
                "Time (ns)",
                dataset,
                true
        );

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setDomainAxis(new CategoryAxis("Philosopher Class"));
        plot.setRangeAxis(new NumberAxis("Time (ns)"));
        plot.setRenderer(new BoxAndWhiskerRenderer());

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Box Plot Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new ChartPanel(chart));
            frame.pack();
            frame.setVisible(true);
        });
    }
}
