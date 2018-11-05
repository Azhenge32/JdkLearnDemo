package com.azhen.java.util.function;

interface Interface1 {
    default String sayHi() {
        return "1  Hi";
    }
}

interface Interface2 {
    default String sayBye() {
        return "bye";
    }
    String sayHi();
}

@FunctionalInterface
public interface Interface3 extends Interface1, Interface2 {
    String name();
    @Override
    default String sayBye() {
        return null;
    }

    @Override
    default String sayHi() {
        return Interface1.super.sayHi();
    }

    static void main(String[] args) {
        System.out.println("hello");
    }
}
