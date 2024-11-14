package org.example.model.philosopher;

import org.example.model.AbstractPhilosopher;
import org.example.model.Fork;
import org.example.model.Waiter;

public class PhilosopherTask6 extends AbstractPhilosopher {
    private final Waiter waiter;

    public PhilosopherTask6(int id, Fork leftFork, Fork rightFork, Waiter waiter) {
        super(id, leftFork, rightFork);
        this.waiter = waiter;
    }

    @Override
    public void waitToPickUpBothForks() throws InterruptedException {
        boolean inDiningRoom = waiter.tryAcquirePermission();

        if (inDiningRoom) {
            pickUpLeftFork();
            pickUpRightFork();
        } else {
            pickUpRightFork();
            pickUpLeftFork();
        }
    }

    @Override
    public void putDownForks() {
        putDownLeftFork();
        putDownRightFork();
        waiter.releasePermission();
    }
}
