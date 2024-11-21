package org.example.model.philosopher;

import org.example.model.AbstractPhilosopher;
import org.example.model.Fork;

import java.util.Random;

public class PhilosopherTask4 extends AbstractPhilosopher {
    private final Random random = new Random();

    public PhilosopherTask4(int id, Fork leftFork, Fork rightFork) {
        super(id, leftFork, rightFork);
    }

    @Override
    public void waitToPickUpBothForks() {
        boolean pickLeftFirst = random.nextBoolean();
        if (pickLeftFirst) {
            pickUpLeftFork();
            pickUpRightFork();
        } else {
            pickUpRightFork();
            pickUpLeftFork();
        }
    }
}
