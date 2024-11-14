package org.example.model.philosopher;

import org.example.model.AbstractPhilosopher;
import org.example.model.Fork;

public class PhilosopherTask2 extends AbstractPhilosopher {
    public PhilosopherTask2(int id, Fork leftFork, Fork rightFork) {
        super(id, leftFork, rightFork);
    }

    public void waitToPickUpBothForks() {
        while (true) {
            if (tryPickUpLeftFork()) {
                if (tryPickUpRightFork()) {
                    break;
                } else {
                    leftFork.putDown();
                }
            }
        }
    }
}
