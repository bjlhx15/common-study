package com.lhx.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolTest {
    public static void main(String[] args) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 50; i++) {
            cachedThreadPool.submit(new TestThread((i + 1)));
        }
        cachedThreadPool.shutdown();
    }
}
