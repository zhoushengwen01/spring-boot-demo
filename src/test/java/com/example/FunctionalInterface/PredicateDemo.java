package com.example.FunctionalInterface;

import java.util.function.Predicate;

/**
 * 判断接口
 */
public class PredicateDemo {


    private static void method(Predicate<String> predicate,String s){
        System.out.println(""+predicate.test(s));
    }


}
