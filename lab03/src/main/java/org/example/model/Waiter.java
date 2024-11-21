package org.example.model;

import java.util.concurrent.Semaphore;

public class Waiter {
    private final Semaphore semaphore;

    public Waiter(int maxConcurrentPhilosophers) {
        this.semaphore = new Semaphore(maxConcurrentPhilosophers);
    }

    public boolean tryAcquirePermission() {
        return semaphore.tryAcquire();
    }

    public void requestPermissionBlocking() throws InterruptedException {
        semaphore.acquire();
    }

    public void releasePermission() {
        semaphore.release();
    }
}
