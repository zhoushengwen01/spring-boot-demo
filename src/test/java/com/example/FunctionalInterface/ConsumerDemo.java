package com.example.FunctionalInterface;

import java.util.function.Consumer;

/**
 * Consumer接口 消费一个数据，数据类型由泛型决定
 */

public class ConsumerDemo {

    private static void consumerString(Consumer<String> consumer1, Consumer<String> consumer2) {
        consumer1.andThen(consumer2).accept("hellow");
        //andThen方法：先自身执行accept方法，再调用消费返回的Consumer的accept方法
    }


    public static void main(String[] args) {
//        consumerString(
//                s -> System.out.println(s.toLowerCase()),
//                s -> System.out.println(s.toUpperCase())
//        );
        String[] array = {"迪丽热巴,女", "古力娜扎,女", "马尔扎哈,男"};
        printInfo(
                s -> System.out.println("姓名：" +s.split(",")[0]),
                s -> System.out.println("性别：" + s.split(",")[1]),
                array
        );
    }


    private static void printInfo(Consumer<String> one, Consumer<String> two, String[] array) {
        for (String info : array) {
            one.andThen(two).accept(info); // 姓名：迪丽热巴。性别：女。
        }
    }

}
