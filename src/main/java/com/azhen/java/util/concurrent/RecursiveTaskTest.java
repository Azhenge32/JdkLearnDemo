package com.azhen.java.util.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class RecursiveTaskTest extends RecursiveTask<Long> {

    static final int THRESHOLD = 100;
    long[] array;
    int start;
    int end;

    RecursiveTaskTest(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            // 如果任务足够小,直接计算:
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println(String.format("compute %d~%d = %d", start, end, sum));
            return sum;
        }
        // 任务太大,一分为二:
        int middle = (end + start) / 2;
        System.out.println(String.format("split %d~%d ==> %d~%d, %d~%d", start, end, start, middle, middle, end));
        RecursiveTaskTest subtask1 = new RecursiveTaskTest(this.array, start, middle);
        RecursiveTaskTest subtask2 = new RecursiveTaskTest(this.array, middle, end);
        invokeAll(subtask1, subtask2);
        Long subresult1 = subtask1.join();
        Long subresult2 = subtask2.join();
        Long result = subresult1 + subresult2;
        System.out.println("result = " + subresult1 + " + " + subresult2 + " ==> " + result);
        return result;
    }

    private static void fillRandom(long[] array) {
        if (array == null || array.length == 0) {
         return ;
        }

        for (int i = 0; i < array.length; i ++) {
            array[i] = (long) Math.random();
        }
    }

    public static void main(String[] args) {
        // 创建随机数组成的数组:
        long[] array = new long[400];
        fillRandom(array);
        // fork/join task:
        ForkJoinPool fjp = new ForkJoinPool(4); // 最大并发数4
        ForkJoinTask<Long> task = new RecursiveTaskTest(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        Long result = fjp.invoke(task);
        long endTime = System.currentTimeMillis();
        System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");
    }
}
