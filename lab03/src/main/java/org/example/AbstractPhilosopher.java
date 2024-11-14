package org.example;

public abstract class AbstractPhilosopher extends Thread {
    protected final Fork leftFork;
    protected final Fork rightFork;
    protected final int id;

    protected AbstractPhilosopher(int id, Fork leftFork, Fork rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    protected void think() throws InterruptedException {
        System.out.println("Philosopher" + id + " thinks.");
        Thread.sleep(1000);
    }

    protected void eat() throws InterruptedException {
        System.out.println("Philosopher" + id + " eats.");
        Thread.sleep(1000);
    }

    protected void sleep() throws InterruptedException {
        System.out.println("Philosopher" + id + " sleeps.");
        Thread.sleep(1000);
    }

    protected void pickUpLeftFork() throws InterruptedException {
        leftFork.pickUp();
        System.out.println("Philosopher" + id + " picked up left fork.");
    }

    protected void pickUpRightFork() throws InterruptedException {
        rightFork.pickUp();
        System.out.println("Philosopher" + id + " picked up right fork.");
    }

    protected void putDownLeftFork() {
        leftFork.putDown();
        System.out.println("Philosopher" + id + " put down left fork.");
    }

    protected void putDownRightFork() {
        rightFork.putDown();
        System.out.println("Philosopher" + id + " put down right fork.");
    }
}

