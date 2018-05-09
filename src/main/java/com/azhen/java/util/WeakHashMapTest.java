package com.azhen.java.util;

import java.util.WeakHashMap;

/**
 * 强引用：被强引用指向的对象，绝对不会被垃圾收集器回收
 * 软引用：被SoftReference指向的对象可能会被垃圾收集器回收，但是只有在JVM内存不够的情况下才会回收
 * 弱引用：当一个对象仅仅被WeakReference引用时，在下个垃圾收集周期时候该对象就会被回收
 *
 * WeakHashMap的这种特性比较适合实现类似本地、堆内缓存的存储机制——缓存的失效依赖于GC收集器的行为
 */
public class WeakHashMapTest {
    public static void main(String[] args) {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        String img1 = "img1";
        map.put(img1, "azhen.gif");
        map.put("img2", "azhen2.gif");
        System.out.println(map.size());
        // img1 = null;
        System.gc();
        System.out.println(map.size());
    }
}
