package com.azhen.java.multithreadingLearn.diffpartofarray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 */
public class Diffpartofarray {
    static class OpcTask implements Runnable {
        private List<Integer> list;
        private int beginIndex;
        private int endIndex;

        public OpcTask(List<Integer> list, int beginIndex, int endIndex) {
            this.list = list;
            this.beginIndex = beginIndex;
            this.endIndex = endIndex;
        }

        @Override
        public void run() {
            for (int i = beginIndex; i < endIndex; i ++) {
                list.set(i, i);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        int size = 123;
        for (int i = 0; i < size; i ++) {
            list.add(-1);
        }

        int part = 33;
        ExecutorService executor = Executors.newFixedThreadPool(size / part + 1);
        int count = 0;
        for (int i = 0; i < size; i += part) {
            int beginIndex = i;
            int endIndex = i + part;
            if (endIndex >= size) {
                endIndex = size;
            }
            OpcTask task = new OpcTask(list, beginIndex, endIndex);
            executor.execute(task);
            count ++;
        }

        shutdownAndAwaitTermination(120, executor);
        for (int i = 0; i < size; i ++) {
            System.out.println(list.get(i));
        }
    }

    public static void shutdownAndAwaitTermination(int awaitTime, ExecutorService pool) {
        pool.shutdown();//关闭线程池
        //判断是否所有的线程已经运行完
        while (!pool.isTerminated()) {
            ;
        }
      /*  pool.shutdown();    //禁用提交的新任务
        try {
            if (!pool.awaitTermination(awaitTime, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();                     //（重新）取消当前线程是否中断
            Thread.currentThread().interrupt();     //保持中断状态
        }*/
    }

}
