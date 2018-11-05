package com.azhen.java.util.function;

import java.util.function.Function;

/**
 * 级联表达式和柯里化
 * 柯里化：把多个参数的函数转换为只有一个参数的函数
 * 柯里化的目的：函数的标准化和链式调用
 *              例如这样就不需要定义参数个数越来越多的接口
 *              函数的延迟加载
 * 高阶函数：返回值是函数的函数
 */
public class CurryDemo {
    public static void main(String[] args) {
        Function<Integer, Function<Integer, Integer>> fun = x -> y -> x + y;
        System.out.println(fun.apply(2).apply(3));

        int[] nums = {2, 3, 4};
        Function f = fun;
        for (int i : nums) {
            if (f instanceof Function) {
                Object obj = f.apply(i);
                if (obj instanceof Function) {
                    f = (Function) obj;
                } else {
                    System.out.println("调用结束，结果为 " + obj);
                }
            }
        }
    }
}
