package org.example;

public class zad6 {
    private static class Philosopher extends AbstractPhilosopher{
        private final Waiter waiter;

        public Philosopher(int id, Fork leftFork, Fork rightFork, Waiter waiter) {
            super(id, leftFork, rightFork);
            this.waiter = waiter;
        }


        @Override
        public void run() {
            try {
                while (true) {
                    think();

                    boolean inDiningRoom = waiter.tryAcquirePermission();

                    if (inDiningRoom) {
                        System.out.println("Filozof " + id + " wchodzi do jadalni.");
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
                        System.out.println("Filozof " + id + " opuszcza jadalnię.");
                    } else {
                        System.out.println("Filozof " + id + " czeka na korytarzu.");
                        rightFork.pickUp();
                        System.out.println("Filozof " + id + " (na korytarzu) podniósł prawy widelec.");
                        leftFork.pickUp();
                        System.out.println("Filozof " + id + " (na korytarzu) podniósł lewy widelec.");

                        eat();

                        rightFork.putDown();
                        System.out.println("Filozof " + id + " (na korytarzu) odłożył prawy widelec.");
                        leftFork.putDown();
                        System.out.println("Filozof " + id + " (na korytarzu) odłożył lewy widelec.");
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
        Waiter waiter = new Waiter(numPhilosophers - 1);

        // Tworzenie widelców
        for (int i = 0; i < numPhilosophers; i++) {
            forks[i] = new Fork(i);
        }

        // Tworzenie filozofów
        for (int i = 0; i < numPhilosophers; i++) {
            Fork leftFork = forks[i];
            Fork rightFork = forks[(i + 1) % numPhilosophers];
            philosophers[i] = new Philosopher(i, leftFork, rightFork, waiter);
            philosophers[i].start();
        }
    }
}
