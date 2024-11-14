package org.example.model.philosopher;

import org.example.model.AbstractPhilosopher;
import org.example.model.Fork;

public class PhilosopherTask3 extends AbstractPhilosopher {
        public PhilosopherTask3(int id, Fork leftFork, Fork rightFork) {
            super(id, leftFork, rightFork);
        }

        @Override
        public void waitToPickUpBothForks() {
            if (id % 2 == 0) {
                pickUpRightFork();
                pickUpLeftFork();
            }
            else {
                pickUpLeftFork();
                pickUpRightFork();
            }
        }
}
