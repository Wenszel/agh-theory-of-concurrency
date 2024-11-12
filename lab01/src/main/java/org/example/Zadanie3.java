package org.example;


public class Zadanie3{
    private static double sharedDouble = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                sharedDouble = Double.MAX_VALUE;
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                sharedDouble = 0.0;
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("Final value of sharedDouble: " + sharedDouble);
    }
}
