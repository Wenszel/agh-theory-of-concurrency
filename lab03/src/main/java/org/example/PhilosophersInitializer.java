package org.example;

import org.example.model.AbstractPhilosopher;
import org.example.model.Fork;
import org.example.model.Waiter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhilosophersInitializer {

    private final int numberOfPhilosophers;

    public PhilosophersInitializer(int numberOfPhilosophers) {
        this.numberOfPhilosophers = numberOfPhilosophers;
    }

    public List<AbstractPhilosopher> initPhilosophers(Class<? extends AbstractPhilosopher> philosopherClass) {
        List<AbstractPhilosopher> philosophers = new ArrayList<>();
        List<Fork> forks = initForks();
        try {
            for (int id = 0; id < numberOfPhilosophers; id++) {
                Fork leftFork = forks.get(id);
                Fork rightFork = forks.get((id + 1) % numberOfPhilosophers);
                AbstractPhilosopher philosopher = initPhilosopher(philosopherClass, id, leftFork, rightFork);
                philosophers.add(philosopher);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return philosophers;
    }

    private AbstractPhilosopher initPhilosopher
            (Class<? extends AbstractPhilosopher> philosopherClass, int id, Fork leftFork, Fork rightFork)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        AbstractPhilosopher philosopher;
        if (!doesPhilosopherClassContainWaiter(philosopherClass)) {
            philosopher = getPhilosopherWithoutWaiter(philosopherClass, id, leftFork, rightFork);
        } else {
            philosopher = getPhilosopherWithWaiter(philosopherClass, id, leftFork, rightFork);
        }
        return philosopher;
    }

    private AbstractPhilosopher getPhilosopherWithoutWaiter(Class<? extends AbstractPhilosopher> philosopherClass, int id, Fork leftFork, Fork rightFork)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        AbstractPhilosopher philosopher;
        Constructor<? extends AbstractPhilosopher> constructor = philosopherClass.getConstructor(int.class, Fork.class, Fork.class);
        philosopher = constructor.newInstance(id, leftFork, rightFork);
        return philosopher;
    }

    private AbstractPhilosopher getPhilosopherWithWaiter(Class<? extends AbstractPhilosopher> philosopherClass, int id, Fork leftFork, Fork rightFork)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        AbstractPhilosopher philosopher;
        Constructor<? extends AbstractPhilosopher> constructor = philosopherClass.getConstructor(int.class, Fork.class, Fork.class, Waiter.class);
        Waiter waiter = new Waiter(numberOfPhilosophers - 1);
        philosopher = constructor.newInstance(id, leftFork, rightFork, waiter);
        return philosopher;
    }


    private boolean doesPhilosopherClassContainWaiter(Class<? extends AbstractPhilosopher> philosopherClass) {
        return Arrays.stream(philosopherClass.getDeclaredConstructors())
                .anyMatch(constructor -> Arrays.asList(constructor.getParameterTypes()).contains(Waiter.class));
    }

    private List<Fork> initForks() {
        List<Fork> forks = new ArrayList<>();
        for (int i = 0; i < numberOfPhilosophers; i++) {
            forks.add(new Fork(i));
        }
        return forks;
    }
}
