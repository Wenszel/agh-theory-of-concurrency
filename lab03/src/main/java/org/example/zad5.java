package org.example;

public class zad5 {
    private static class Philosopher extends Thread {
        private final int id;
        private final Fork leftFork;
        private final Fork rightFork;
        private final Waiter waiter;

        public Philosopher(int id, Fork leftFork, Fork rightFork, Waiter waiter) {
            this.id = id;
            this.leftFork = leftFork;
            this.rightFork = rightFork;
            this.waiter = waiter;
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

                    waiter.requestPermissionBlocking();

                    leftFork.pickUp();
                    System.out.println("Filozof " + id + " podniósł lewy widelec.");

                    rightFork.pickUp();
                    System.out.println("Filozof " + id + " podniósł prawy widelec.");

                    eat();

                    leftFork.putDown();
                    System.out.println("Filozof " + id + " odłożył lewy widelec.");
                    rightFork.putDown();
                    System.out.println("Filozof " + id + " odłożył prawy widelec.");

                    waiter.releasePermission();
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
        Waiter waiter = new Waiter(numPhilosophers - 1);

        for (int i = 0; i < numPhilosophers; i++) {
            forks[i] = new Fork(i);
        }

        for (int i = 0; i < numPhilosophers; i++) {
            Fork leftFork = forks[i];
            Fork rightFork = forks[(i + 1) % numPhilosophers];
            philosophers[i] = new Philosopher(i, leftFork, rightFork, waiter);
            philosophers[i].start();
        }
    }
}
