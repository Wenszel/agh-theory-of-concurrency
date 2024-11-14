package org.example;

import java.util.concurrent.locks.ReentrantLock;

public class Fork {
    final int id;
    final ReentrantLock reentrantLock = new ReentrantLock();

    public Fork(int id) {
        this.id = id;
    }

    public void pickUp() {
        reentrantLock.lock();
    }

    public void putDown() {
        reentrantLock.unlock();
    }

    public boolean isLocked() {
        return reentrantLock.isLocked();
    }
}