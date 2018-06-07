package com.azhen.java.util.stream;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamTest {
    static class Person {
        private int age;
        private int group;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getGroup() {
            return group;
        }

        public void setGroup(int group) {
            this.group = group;
        }
    }
    private static final int size = 10_0000;
    private static int serialTime = 0;
    private static int parallelTime = 0;
    public static void test1(int add) {
        List<Person> personList = new ArrayList<>(size);
        for (int i = 0; i < size + add; i ++) {
            Person person = new Person();
            person.setAge(i);
            person.setGroup(i % 30);
            personList.add(person);
        }

        long startTime1 = System.currentTimeMillis();
        Map<Integer, List<Person>> personMap = personList.stream().collect(Collectors.groupingBy(Person::getGroup));
        long endTime1 = System.currentTimeMillis();

        long startTime2 = System.currentTimeMillis();
        Map<Integer, List<Person>> personMap2 = personList.parallelStream().collect(Collectors.groupingBy(Person::getGroup));
        long endTime2 = System.currentTimeMillis();
        long useTime1 = endTime1 - startTime1;
        long useTime2 = endTime2 - startTime2;
        serialTime += useTime1;
        parallelTime += useTime2;
        System.out.println("Serial Time: " + useTime1 + " Parallel Time: " + useTime2);
        valid(personMap, size + add);
        valid(personMap2, size + add);
    }
    public static void main(String[] args) {
       for (int i = 0; i < 100; i ++) {
           System.out.println(i);
           test1(i);
       }
        System.out.println("Serial Time: " + serialTime + " Parallel Time: " + parallelTime);
    }

    private static void valid(Map<Integer, List<Person>> personMap, int total) {
        int sum = 0;
        for (Map.Entry<Integer, List<Person>> entry : personMap.entrySet()) {
            sum += entry.getValue().size();
        }
        if (sum != total) {
            throw new RuntimeException("数量不对" + sum);
        }
    }
}
