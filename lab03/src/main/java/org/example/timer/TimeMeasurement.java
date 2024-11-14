package org.example.timer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class TimeMeasurement {
    private static final ConcurrentHashMap<Integer, AtomicLong> countMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Integer, AtomicLong> totalTimeMap = new ConcurrentHashMap<>();


    public static void addTime(int key, long time) {
        countMap.putIfAbsent(key, new AtomicLong(0));
        totalTimeMap.putIfAbsent(key, new AtomicLong(0));

        totalTimeMap.get(key).addAndGet(time);
        countMap.get(key).incrementAndGet();
    }

    public static double getAverageTime(int key) {
        long count = countMap.getOrDefault(key, new AtomicLong(0)).get();
        long totalTime = totalTimeMap.getOrDefault(key, new AtomicLong(0)).get();

        if (count == 0) {
            return 0.0;
        }
        return (double) totalTime / count;
    }

    public static Map<Integer, Double> getAverageTimesAndReset() {
        Map<Integer, Double> averageTimes = new HashMap<>();
        for (Integer key : countMap.keySet()) {
            averageTimes.put(key, getAverageTime(key));
        }
        countMap.clear();
        totalTimeMap.clear();
        return averageTimes;
    }
}
