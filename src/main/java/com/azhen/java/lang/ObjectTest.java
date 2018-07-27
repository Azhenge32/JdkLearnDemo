package com.azhen.java.lang;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class ObjectTest {
    public static void main(String[] args) {
        Integer count = 2222;
        ReferenceQueue refQueue = new ReferenceQueue();
        PhantomReference<Integer> p = new PhantomReference<>(count, refQueue);
        count = null;
        System.gc();
        try {
            Reference<Integer> ref = refQueue.remove();
            if (ref != null) {
                // do something
                System.out.println(ref.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
