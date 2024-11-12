package org.example;


public class Zadanie2 {
    public static void main(String[] args) {
        int[] vals = {100, 1000, 10000, 100000, 1000000};

        for (int val : vals) {
            final Incrementator incrementator = new Incrementator();
            Incrementator.setValue(0);
            final Thread thread1 = new Thread(() -> {
                for (int i = 0; i < val; i++) {
                    Incrementator.incrementSync();
                }
            });
            final Thread thread2 = new Thread(() -> {
                synchronized (incrementator) {
                for (int i = 0; i < val; i++) {
                    incrementator.increment();
                }}
            });
            thread1.start();
            thread2.start();
            try {
                thread1.join();
                thread2.join();
                System.out.println("dla " + val + " " + incrementator.getValue());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static class Incrementator{
        private static int value = 0;

        public static void setValue(int value) {
            Incrementator.value = value;
        }

        public void increment(){
            value++;
        }
        public static synchronized void incrementSync(){
            value++;
        }
        public int getValue(){
            return value;
        }
    }
}
