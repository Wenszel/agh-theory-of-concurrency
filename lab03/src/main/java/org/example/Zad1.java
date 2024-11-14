package org.example;

import java.util.ArrayList;
import java.util.List;

public class Zad1 {

    private static class Philosopher extends AbstractPhilosopher{
        public Philosopher(int id, Fork leftFork, Fork rightFork) {
            super(id, leftFork, rightFork);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    think();
                    pickUpLeftFork();
                    pickUpRightFork();
                    eat();
                    putDownLeftFork();
                    putDownRightFork();
                    sleep();
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

        for (int i = 0; i < n; i++) {
            forks.add(new Fork(i));
        }

        for (int i = 0; i < n; i++) {
            Fork leftFork = forks.get(i);
            Fork rightFork = forks.get((i + 1) % n);
            Philosopher philosopher = new Philosopher(i, leftFork, rightFork);
            Thread thread = new Thread(philosopher, "Philosopher " + (i + 1));
            threads.add(thread);
        }

        threads.forEach(Thread::start);
    }
}
