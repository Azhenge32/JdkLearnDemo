package com.azhen.java.lang;

public class StringBuilderTest {
    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        builder.insert(1, "B"); // 报错
        System.out.println(builder.toString());
        builder.insert(0, "A");
        System.out.println(builder.toString());
    }
}
