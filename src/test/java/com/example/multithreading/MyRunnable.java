package com.example.multithreading;

public class MyRunnable implements Runnable {

    private int taskNum;

    public MyRunnable(int num) {
        this.taskNum = num;
    }

    public void run() {
        System.out.println("正在执行task " + taskNum);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task " + taskNum + "执行完毕");
    }
}
