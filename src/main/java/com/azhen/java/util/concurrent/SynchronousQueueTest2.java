package com.azhen.java.util.concurrent;

import java.util.concurrent.*;

/**
 * 适用场景
 * 任务不保留
 * 1、只有一个消费者，任务串行处理，处理过程中不保留新添加的任务
 */
public class SynchronousQueueTest2 {
    private static ExecutorService executor = new ThreadPoolExecutor(1, 1,
            1000, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(true),
            new ThreadPoolExecutor.DiscardPolicy());

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            kickOffEntry(i);

            Thread.sleep(200);
        }

        executor.shutdown();
    }

    private static void kickOffEntry(final int index) {
        executor.
                submit(
                        new Callable<Void>() {
                            public Void call() throws InterruptedException {
                                System.out.println("start " + index);
                                Thread.sleep(1000); // pretend to do work
                                System.out.println("stop " + index);
                                return null;
                            }
                        }
                );
    }
}
