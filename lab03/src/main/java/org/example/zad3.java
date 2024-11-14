package org.example;

public class zad3 {
    private static class Philosopher extends Thread {
        private final int id;
        private final Fork leftFork;
        private final Fork rightFork;

        public Philosopher(int id, Fork leftFork, Fork rightFork) {
            this.id = id;
            this.leftFork = leftFork;
            this.rightFork = rightFork;
        }

        private void think() throws InterruptedException {
            System.out.println("Filozof " + id + " myśli.");
            Thread.sleep((int)(Math.random() * 1000));
        }

        private void eat() throws InterruptedException {
            System.out.println("Filozof " + id + " je.");
            Thread.sleep((int)(Math.random() * 1000));
        }

        @Override
        public void run() {
            try {
                while (true) {
                    think();

                    // Filozof o parzystym numerze podnosi najpierw prawy widelec
                    if (id % 2 == 0) {
                        rightFork.pickUp();
                        System.out.println("Filozof " + id + " podniósł prawy widelec.");
                        leftFork.pickUp();
                        System.out.println("Filozof " + id + " podniósł lewy widelec.");
                    }
                    // Filozof o nieparzystym numerze podnosi najpierw lewy widelec
                    else {
                        leftFork.pickUp();
                        System.out.println("Filozof " + id + " podniósł lewy widelec.");
                        rightFork.pickUp();
                        System.out.println("Filozof " + id + " podniósł prawy widelec.");
                    }

                    eat();

                    // Odkładanie widelców w odwrotnej kolejności
                    if (id % 2 == 0) {
                        leftFork.putDown();
                        System.out.println("Filozof " + id + " odłożył lewy widelec.");
                        rightFork.putDown();
                        System.out.println("Filozof " + id + " odłożył prawy widelec.");
                    } else {
                        rightFork.putDown();
                        System.out.println("Filozof " + id + " odłożył prawy widelec.");
                        leftFork.putDown();
                        System.out.println("Filozof " + id + " odłożył lewy widelec.");
                    }
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
