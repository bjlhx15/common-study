package com.lhx.cloud.futruetask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TestFuture {
    public static void main(String[] args) throws Exception {
        Callable<Integer> call = () -> {
            System.out.println("计算线程正在计算结果...");
            Thread.sleep(3000);
            return 1;
        };
        FutureTask<Integer> task = new FutureTask<>(call);
        Thread thread = new Thread(task);
        thread.start();

        System.out.println("main线程干点别的...");

        Integer result = task.get();
        System.out.println("从计算线程拿到的结果为：" + result);
    }
}
