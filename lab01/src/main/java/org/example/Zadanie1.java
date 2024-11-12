package org.example;

public class Zadanie1 {
    private static boolean elo = false;
    public static void main(String[] args) {
        int n = 3;
        for (int i = 0; i < n; i++) {
            int seconds = (int) Math.ceil(Math.random() * 10);
            Thread thread = new Thread(() -> {
                for (int j = seconds; j > 0; j--) {
                    try {
                        if (elo) break;
                        System.out.println(Thread.currentThread().getName() + ": " + (j) + " seconds left");

                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
                elo = true;
                System.out.println("Pif paf!");
            });
            thread.start();
        }
    }
}