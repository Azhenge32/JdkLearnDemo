package com.azhen.java.util.concurrent;

class SharingLong {
    volatile long v;
    long p1, p2, p3, p4, p5, p6;
}

class LightThread extends Thread {
    SharingLong[] shares;
    int index;

    LightThread(SharingLong[] shares, int index) {
        this.shares = shares;
        this.index = index;
    }

    public void run() {
        for (int i = 0; i < 100_000_000; i ++) {
            shares[index].v ++;
        }
    }
}

public class VolatileTest {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i ++) {
            benchMark();
        }
    }

    public static void benchMark() throws InterruptedException {
        int size = Runtime.getRuntime().availableProcessors();
        SharingLong[] shares = new SharingLong[size];
        Thread[] threads = new Thread[size];
        for (int i = 0; i < size; i ++) {
            shares[i] = new SharingLong();
            threads[i] = new LightThread(shares, i);
        }

        for (Thread t : threads) {
            t.start();
        }
        long start = System.currentTimeMillis();
        for (Thread t : threads) {
            t.join();
        }
        long end = System.currentTimeMillis();
        System.out.printf("total costs %dms\n", end - start);
    }
}
