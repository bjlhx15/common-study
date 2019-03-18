package com.lhx.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolTest {
    public static void main(String[] args) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 50; i++) {
            fixedThreadPool.submit(new TestThread((i + 1)));
        }
        fixedThreadPool.shutdown();
    }

}
