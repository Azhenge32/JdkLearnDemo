package com.azhen.java.util.function;

import java.util.function.Consumer;
import java.util.function.Function;

public class Dog {
    public static void main(String[] args) {
        Consumer<String> consumer = System.out::println;
        // static method
        Consumer<String> consumer2 = Dog::bark;
        consumer2.accept("mouse");

        // non-static method
        Dog dog = new Dog();
        Function<String, String> function = dog::eat;
    }

    private static void bark(String msg) {
        System.out.println("旺旺" + msg);
    }

    private String eat(String msg) {
        return msg;
    }
}
