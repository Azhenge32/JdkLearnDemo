package com.azhen.java.util.concurrent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListTest {
    /**
     * 在遍历的时候可以修改列表
     * @param args
     */
    public static void modifiElemWhileTravel(String[] args) {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        //List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        for (Integer i : list) {
            if (i.equals(3)) {
                list.remove(i);
            }
        }
        desc(list);
    }

    private static void desc(List<Integer> list) {
        for (Integer i : list) {
            System.out.println(i);
        }
    }
}
