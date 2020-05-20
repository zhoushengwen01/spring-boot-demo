package com.example.multithreading;

import java.util.concurrent.ThreadPoolExecutor;


public class Main {
    public static void main(String[] args) {
        ThreadPoolExecutor pool = ThreadPool.getInstance().getPool();


        for (int i = 0; i < 20; i++) {
            MyRunnable myRunnable = new MyRunnable(i);
            pool.execute(myRunnable);
        }

        pool.shutdown();

        while (true) {
            System.out.println("线程池中的线程数目：" + pool.getPoolSize() + ",队列中等待执行的任务数量：" + pool.getQueue().size() + ",已执行完的任务数目：" + pool.getCompletedTaskCount());
            if (pool.isTerminated()) {
                System.out.println("==============所有线程全部运行完毕！=======================");
                break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
