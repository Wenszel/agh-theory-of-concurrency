package org.example.model;

import org.example.timer.TimeMeasurement;

public abstract class AbstractPhilosopher extends Thread {
    protected Fork leftFork;
    protected Fork rightFork;
    protected int id;

    protected AbstractPhilosopher(int id, Fork leftFork, Fork rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    public void think() throws InterruptedException {
//        System.out.println("Philosopher" + id + " thinks.");
    }

    public void eat() throws InterruptedException {
//        System.out.println("Philosopher" + id + " eats.");
    }

    public void pickUpLeftFork() {
        leftFork.pickUp();
//        System.out.println("Philosopher" + id + " picked up left fork.");
    }

    public void pickUpRightFork() {
        rightFork.pickUp();
//        System.out.println("Philosopher" + id + " picked up right fork.");
    }

    public void putDownLeftFork() {
        leftFork.putDown();
//        System.out.println("Philosopher" + id + " put down left fork.");
    }

    public void putDownRightFork() {
        rightFork.putDown();
//        System.out.println("Philosopher" + id + " put down right fork.");
    }

    public boolean tryPickUpLeftFork() {
        if (leftFork.tryLock()) {
//            System.out.println("Philosopher" + id + " picked up left fork.");
            return true;
        }
        return false;
    }
    public boolean tryPickUpRightFork() {
        if (rightFork.tryLock()) {
//            System.out.println("Philosopher" + id + " picked up right fork.");
            return true;
        }
        return false;
    }
    public abstract void waitToPickUpBothForks() throws InterruptedException;

    public void putDownForks() {
        putDownLeftFork();
        putDownRightFork();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                think();
                long startTime = System.nanoTime();

                waitToPickUpBothForks();

                long estimatedTime = System.nanoTime() - startTime;
                TimeMeasurement.addTime(this.id, estimatedTime);

                eat();
                putDownForks();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

