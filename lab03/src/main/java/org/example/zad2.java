package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class zad2 {
    private static class Philosopher extends AbstractPhilosopher {
        private final Lock lock = new ReentrantLock();
        public Philosopher(int id, Fork leftFork, Fork rightFork) {
            super(id, leftFork, rightFork);
        }

        private void waitToPickUpBothForks() throws InterruptedException {
            synchronized (lock) {
               while(rightFork.isLocked() || leftFork.isLocked()) {
                   try {
                       lock.wait();
                   } catch (InterruptedException e) {
                       Thread.currentThread().interrupt();
                   }
               }
                pickUpLeftFork();
                pickUpRightFork();
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    think();
                    waitToPickUpBothForks();
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
        int n = 2;
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
