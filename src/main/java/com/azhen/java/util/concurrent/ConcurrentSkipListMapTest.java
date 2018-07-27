package com.azhen.java.util.concurrent;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 ConcurrentSkipListMap介绍
 ConcurrentSkipListMap是线程安全的有序的哈希表，适用于高并发的场景。
 ConcurrentSkipListMap和TreeMap，它们虽然都是有序的哈希表。但是，第一，它们的线程安全机制不同，TreeMap是非线程安全的，而ConcurrentSkipListMap是线程安全的。第二，ConcurrentSkipListMap是通过跳表实现的，而TreeMap是通过红黑树实现的。
 关于跳表(Skip List)，它是平衡树的一种替代的数据结构，但是和红黑树不相同的是，跳表对于树的平衡的实现是基于一种随机化的算法的，这样也就是说跳表的插入和删除的工作是比较简单的。
 */
public class ConcurrentSkipListMapTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, Integer> map = new ConcurrentSkipListMap<>();
        for (int i = 0; i < 1000; i++) {
            map.put(i, i);
        }

        for (ConcurrentSkipListMap.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey());
        }
    }
}
