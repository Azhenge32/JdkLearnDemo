package com.azhen.core.generic;

import java.util.ArrayList;
import java.util.List;

class A {

}
class B extends A {

}
class C extends B {

}
public class Test {
    public static void main(String[] args) {
        List<A> aList = new ArrayList<>();
    }
}
