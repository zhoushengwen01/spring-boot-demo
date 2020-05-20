package com.example.multithreading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    private static volatile ThreadPool instance;
    ThreadPoolExecutor pool;

    private ThreadPool() {
        if (pool == null) {
            ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1000);
            pool = new ThreadPoolExecutor(5, 15, 200, TimeUnit.MILLISECONDS, queue);
        }
    }

    public ThreadPoolExecutor getPool() {
        return pool;
    }

    public static ThreadPool getInstance() {
        if (instance == null) {
            synchronized (ThreadPool.class) {
                if (instance == null)
                    return instance = new ThreadPool();
            }
            instance = new ThreadPool();
        }
        return instance;
    }


}
