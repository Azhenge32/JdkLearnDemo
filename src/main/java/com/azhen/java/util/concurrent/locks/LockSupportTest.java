package com.azhen.java.util.concurrent.locks;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {
    public static void main(String[] args) throws InterruptedException {
        String a = new String("游戏");

        Thread thread = new Thread(() -> {
            System.out.println("周末了我要打游戏");
            LockSupport.park();
            System.out.println("配女朋友逛逛街");
        });
        thread.start();
        Thread.sleep(300_000);
        System.out.println("女朋友准备喊男朋友逛街");
        LockSupport.unpark(thread);
    }
}
