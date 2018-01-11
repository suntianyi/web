package com.sun.demo.pack1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class App1 {
    public static void main(String[] args) {
        TimeLock timeLock = new TimeLock();
        Thread t1 = new Thread(timeLock);
        Thread t2 = new Thread(timeLock);
        t1.start();
        t2.start();
    }
}

class TimeLock implements Runnable {
    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                System.out.println("get lock succeed");
                TimeUnit.SECONDS.sleep(6);
            } else {
                System.out.println("get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}