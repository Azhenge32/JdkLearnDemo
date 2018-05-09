package com.azhen.java.util;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Azhen
 * @date 2018/01/24
 */
public class ObjectsTest {
    public static void test1(String[] args) {
        int[] arr1 = new int[] {1,2,3};
        int[] arr2 = new int[] {1,2,3};
        System.out.println(Objects.equals(arr1, arr2)); // false
    }
    public static void main(String[] args) {
        int[] arr1 = new int[] {1,2,3};
        int[] arr2 = new int[] {1,2,3};
        int[] arr3 = new int[] {1,2,4};
        System.out.println(Arrays.equals(arr1, arr2));  // true
        System.out.println(Arrays.equals(arr2, arr3));  // false
    }
}
