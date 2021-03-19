package com.wilhelm.konsza;

class Counter {

    private static int count = 0;

    //synchronized is used so only one thread can use the method at a given time
    public synchronized static void simpleCounter() {
        count++;
    }

    public static int getCount() {
        return count;
    }
}

public class MultiThreadingDemo {

    public static void main(String[] args) throws InterruptedException {


        Thread t1 = new Thread(() -> { //Runnable object created using lambda expression as Runnable is a Functional Interface
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 1");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 2");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.setName("First Thread");
        t2.setName("Second Thread");

        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.MAX_PRIORITY);

        t1.start();
        Thread.sleep(10);
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Main Thread expression to test join");

        //Testing synchronized keyword -> makes method threadsafe meaning that only one thread can use the method at a given time


        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                Counter.simpleCounter();
            }
        });

        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                Counter.simpleCounter();
            }
        });

        t3.start();
        t4.start();

        // without join the main thread won't wait for t3 and t4 to complete it's job
        t3.join();
        t4.join();

        System.out.println("Counter: " + Counter.getCount());
    }
}
