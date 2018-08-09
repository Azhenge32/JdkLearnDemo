package com.azhen.java.util.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.function.Consumer;

/**
 * 有就拿货走人，没有就占个位置等着，等到或超时。
 */
public class SynchronousQueueTest {
    /**
     main over.
     begin to produce.id=1
     success to produce.id=1
     begin to produce.id=2
     */
    // 阻塞队列
     private static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(1);

    /**
     main over.
     begin to produce.id=1
     begin to produce.id=2
     */
    // 同步队列
    // private static SynchronousQueue<String> queue = new SynchronousQueue<String>();

    /*public static void main(String[] args) throws Exception
    {
        new Productor(1).start();
        new Productor(2).start();
        System.out.println("main over.");
    }*/

    public static void main(String[] args) throws Exception {
        new Consumer().start();
        Thread.sleep(200);

        new Productor(1).start();
        new Productor(2).start();
        System.out.println("main over.");
    }

    static class Productor extends Thread
    {
        private int id;

        public Productor(int id)
        {
            this.id = id;
        }

        @Override
        public void run()
        {
            try
            {
                String result = "id=" + this.id;
                System.out.println("begin to produce."+result);
                queue.put(result);
                System.out.println("success to produce."+result);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    static class Consumer extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                System.out.println("consume begin.");
                String v = queue.take();
                System.out.println("consume success." + v);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
