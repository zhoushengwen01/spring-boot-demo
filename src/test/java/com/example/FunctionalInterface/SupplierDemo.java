package com.example.FunctionalInterface;

import java.util.function.Supplier;

public class SupplierDemo {

    /**
     * Supplier接口
     * 限定Lambda表达式需要“对外提供”一个符合泛型类型的对象数据
     */


    private static int getMax(Supplier<Integer> sup) {
        return sup.get();
    }


    public static void main(String[] args) {
        int[] arr = {12, 34, 31, 312, 12, 455, 1234, 123};

        int max = getMax(() -> {
            int i = arr[0];
            for (int j = 1; j < arr.length; j++) {
                if (i < arr[j]) {
                    i = arr[j];
                }
            }
            return i;
        });

        System.out.println(max);
    }


}
