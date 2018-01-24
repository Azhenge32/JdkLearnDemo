package com.azhen.java.lang;

/**
 * @author Azhen
 * @date 2018/01/24
 */
public class BooleanTest {
    public static void main(String[] args) {
        Boolean b1 = new Boolean("true");
        System.out.println(b1.hashCode());  // 1231
        Boolean b2 = new Boolean("truE");
        System.out.println(b1.hashCode());  // 1231
        System.out.println(b1.equals(b2));  // true
        System.out.println(b1 == b2);   // false
    }
}
