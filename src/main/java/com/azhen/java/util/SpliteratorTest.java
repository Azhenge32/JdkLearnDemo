package com.azhen.java.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Spliterator默认是一个一个分开的
 */
public class SpliteratorTest {
    private static class BatchSpliterator<T> implements Spliterator {
        int start, listSize, batchSize;
        List<T> list;

        public BatchSpliterator(List<T> list, int batchSize) {
            this.list = list;
            this.start = 0;
            this.listSize = list.size();
            this.batchSize = batchSize;
        }

        @Override
        public boolean tryAdvance(Consumer action) {
            if (start >= listSize) {
                return false;
            }
            int end = start + batchSize;
            if (end >= listSize) {
                end = listSize;
            }
            action.accept(list.subList(start, end));
            start = end;
            return true;
        }

        @Override
        public Spliterator trySplit() {
           if (listSize - start <= 50) {
               return null;
           } else {
               int tmpStart = start;
               int end = start + batchSize;
               if (end >= listSize) {
                   end = listSize;
               }
               start = end;
               return new BatchSpliterator(list.subList(tmpStart, end), batchSize);
           }
        }

        @Override
        public long estimateSize() {
            return listSize - start;
        }

        @Override
        public int characteristics() {
            return ORDERED | SIZED | IMMUTABLE | SUBSIZED;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i ++) {
            test6(i);
        }
    }
    public static void test6(final int SIZE) {
        int size = SIZE;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i ++) {
            list.add(String.valueOf(i));
        }
        Spliterator<List<String>> spliterator = new BatchSpliterator(list, 50);
        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Consumer<List<String>> action = (strs) -> {
            for (String str : strs) {
                set.add(str);
            }
        };
        StreamSupport.stream(spliterator, true).forEach(action);
        if (set.size() != size) {
            throw new RuntimeException("数量不对");
        }
    }

    // 查看背后的集合支持的特性
    public static void test3(String[] args) {
        // Collection<String> collection = new ArrayList<>();
        // Collection<String> collection = new HashSet<>();
        Collection<String> collection = new ConcurrentLinkedDeque<>();
        Spliterator<String> s = collection.spliterator();
        if(s.hasCharacteristics(Spliterator.ORDERED)){
            System.out.println("ORDERED");
        }
        if(s.hasCharacteristics(Spliterator.DISTINCT)){
            System.out.println("DISTINCT");
        }
        if(s.hasCharacteristics(Spliterator.SORTED)){
            System.out.println("SORTED");
        }
        if(s.hasCharacteristics(Spliterator.SIZED)){
            System.out.println("SIZED");
        }

        if(s.hasCharacteristics(Spliterator.CONCURRENT)){
            System.out.println("CONCURRENT");
        }
        if(s.hasCharacteristics(Spliterator.IMMUTABLE)){
            System.out.println("IMMUTABLE");
        }
        if(s.hasCharacteristics(Spliterator.NONNULL)){
            System.out.println("NONNULL");
        }
        if(s.hasCharacteristics(Spliterator.SUBSIZED)){
            System.out.println("SUBSIZED");
        }
    }

    public static void test4() {
        List<String> list = Arrays.asList("Apple", "Banana", "Orange");
        Spliterator<String> s = list.spliterator();
        s.tryAdvance(System.out::println);
        s.tryAdvance(System.out::println);
        System.out.println(s.estimateSize());
        System.out.println(s.getExactSizeIfKnown());
    }

    public static void test2() {
        List<String> list = Arrays.asList("Apple", "Banana", "Orange");

        Spliterator<String> s = list.spliterator();
        Spliterator<String> s1 = s.trySplit();
        Spliterator<String> s2 = s1.trySplit();
        s.forEachRemaining(System.out::println);
        System.out.println("-- traversing the other half of the spliterator --- ");
        s1.forEachRemaining(System.out::println);
        s2.forEachRemaining(System.out::println);
    }

    public static void test1(String[] args) {
        List<String> list = Arrays.asList("Apple", "Banana", "Orange");
        Spliterator<String> s = list.spliterator();
        s.tryAdvance(System.out::println);
        System.out.println(" --- bulk traversal");
        s.forEachRemaining(System.out::println);

        System.out.println(" --- attempting tryAdvance again");
        boolean b = s.tryAdvance(System.out::println);
        System.out.println("Element exists: "+b);
    }
}
