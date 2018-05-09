package com.azhen.java.util.concurrent.atomic;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicMarkableReferenceTest {
    static AtomicMarkableReference<Integer> atomicMarkableReference = new AtomicMarkableReference(0, false);

    public static void main(String[] args) {
    }
}
