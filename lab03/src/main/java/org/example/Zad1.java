package org.example.lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Zad1 {
    private static class Fork {
        final int id;
        final ReentrantLock reentrantLock = new ReentrantLock();

        public Fork(int id) {
            this.id = id;
        }

        public void lock() {
            reentrantLock.lock();
        }

        public void unlock() {
            if (reentrantLock.isLocked()) {
                reentrantLock.unlock();
            }
        }
    }

    private static class Philosopher implements Runnable {
        private final Fork leftFork;
        private final Fork rightFork;

        public Philosopher(Fork leftFork, Fork rightFork) {
            this.leftFork = leftFork;
            this.rightFork = rightFork;
        }

        @Override
        public void run() {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " is thinking.");
                leftFork.lock();
                System.out.println(Thread.currentThread().getName() + " picked up left fork " + leftFork.id);
                rightFork.lock();
                System.out.println(Thread.currentThread().getName() + " picked up right fork " + rightFork.id);

                // Philosopher is eating
                System.out.println(Thread.currentThread().getName() + " is eating.");
                try {
                    Thread.sleep(1000); // Simulate eating time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                // Put down forks
                leftFork.unlock();
                System.out.println(Thread.currentThread().getName() + " put down left fork " + leftFork.id);
                rightFork.unlock();
                System.out.println(Thread.currentThread().getName() + " put down right fork " + rightFork.id);

                // Philosopher is sleeping
                try {
                    System.out.println(Thread.currentThread().getName() + " is sleeping.");
                    Thread.sleep(1000); // Simulate sleeping time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        int n = 5;
        List<Fork> forks = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        // Initialize forks
        for (int i = 0; i < n; i++) {
            forks.add(new Fork(i));
        }

        // Initialize philosophers and assign forks
        for (int i = 0; i < n; i++) {
            Fork leftFork = forks.get(i);
            Fork rightFork = forks.get((i + 1) % n);
            Philosopher philosopher = new Philosopher(leftFork, rightFork);
            Thread thread = new Thread(philosopher, "Philosopher " + (i + 1));
            threads.add(thread);
        }

        // Start all philosopher threads
        threads.forEach(Thread::start);
    }
}
