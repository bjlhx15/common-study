package com.lhx.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutorTest {
    public static void main(String[] args) {
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 50; i++) {
            singleThreadPool.submit(new TestThread((i + 1)));
        }
        singleThreadPool.shutdown();
    }
}
