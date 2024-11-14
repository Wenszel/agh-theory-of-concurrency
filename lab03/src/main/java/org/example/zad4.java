package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;


public class zad4 {

    private static class Philosopher extends Thread {
        private final int id;
        private final Fork leftFork;
        private final Fork rightFork;
        private final Random random = new Random();

        public Philosopher(int id, Fork leftFork, Fork rightFork) {
            this.id = id;
            this.leftFork = leftFork;
            this.rightFork = rightFork;
        }

        private void think() throws InterruptedException {
            System.out.println("Filozof " + id + " myśli.");
            Thread.sleep((int) (Math.random() * 1000));
        }

        private void eat() throws InterruptedException {
            System.out.println("Filozof " + id + " je.");
            Thread.sleep((int) (Math.random() * 1000));
        }

        @Override
        public void run() {
            try {
                while (true) {
                    think();

                    // Rzut monetą: true dla podniesienia lewego najpierw, false dla prawego
                    boolean pickLeftFirst = random.nextBoolean();

                    if (pickLeftFirst) {
                        // Najpierw podnosimy lewy widelec
                        leftFork.pickUp();
                        System.out.println("Filozof " + id + " podniósł lewy widelec.");
                        rightFork.pickUp();
                        System.out.println("Filozof " + id + " podniósł prawy widelec.");
                    } else {
                        // Najpierw podnosimy prawy widelec
                        rightFork.pickUp();
                        System.out.println("Filozof " + id + " podniósł prawy widelec.");
                        leftFork.pickUp();
                        System.out.println("Filozof " + id + " podniósł lewy widelec.");
                    }

                    eat();

                    // Odkładanie widelców
                    leftFork.putDown();
                    System.out.println("Filozof " + id + " odłożył lewy widelec.");
                    rightFork.putDown();
                    System.out.println("Filozof " + id + " odłożył prawy widelec.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public static void main(String[] args) {
        int numPhilosophers = 5;
        Philosopher[] philosophers = new Philosopher[numPhilosophers];
        Fork[] forks = new Fork[numPhilosophers];

        // Tworzenie widelców
        for (int i = 0; i < numPhilosophers; i++) {
            forks[i] = new Fork(i);
        }

        // Tworzenie filozofów
        for (int i = 0; i < numPhilosophers; i++) {
            Fork leftFork = forks[i];
            Fork rightFork = forks[(i + 1) % numPhilosophers];
            philosophers[i] = new Philosopher(i, leftFork, rightFork);
            philosophers[i].start();
        }
    }
}
