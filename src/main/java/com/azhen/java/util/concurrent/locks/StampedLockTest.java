package com.azhen.java.util.concurrent.locks;

import java.util.concurrent.locks.StampedLock;

public class StampedLockTest {
    private final StampedLock s1 = new StampedLock();

    void mutate() {
        long stamp = s1.writeLock();
        try {
            write();
        } finally {
            s1.unlockWrite(stamp);
        }
    }

    String access() {
        long stamp = s1.tryOptimisticRead();
        String data = read();   // 先试着  读
        if (!s1.validate(stamp)) {  // 判断有没有进入写模式
            stamp = s1.readLock();  // 如果进入了写模式就加读锁后再读
            try {
                data = read();
            } finally {
                s1.unlockRead(stamp);
            }
        }

        return data;
    }

    private String read() {
        return null;
    }

    private void write() {

    }
}
