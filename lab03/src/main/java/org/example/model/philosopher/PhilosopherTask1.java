package org.example.model.philosopher;

import org.example.model.AbstractPhilosopher;
import org.example.model.Fork;

public class PhilosopherTask1 extends AbstractPhilosopher {
    public PhilosopherTask1(int id, Fork leftFork, Fork rightFork) {
        super(id, leftFork, rightFork);
    }

    @Override
    public void waitToPickUpBothForks() throws InterruptedException {
        pickUpLeftFork();
        pickUpRightFork();
    }
}
