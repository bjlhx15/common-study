package com.lhx.cloud.futruetask;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TestException {
    /**
     * 任务没有异常 正常执行，然后结束
     */
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            // 模拟执行耗时任务
            System.out.println("task doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 告诉completableFuture任务已经完成
            completableFuture.complete("ok");
        }).start();
        // 获取任务结果，如果没有完成会一直阻塞等待
        String result = completableFuture.get();
        System.out.println("计算结果:" + result);
    }

    /**
     * 线程有异常  正常执行，然后无法结束，主线程会一直等待
     */
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            // 模拟执行耗时任务
            System.out.println("task doing...");
            try {
                Thread.sleep(3000);
                int i=1/0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 告诉completableFuture任务已经完成
            completableFuture.complete("ok");
        }).start();
        // 获取任务结果，如果没有完成会一直阻塞等待
        String result = completableFuture.get();
        System.out.println("计算结果:" + result);
    }


    /**
     * 线程有异常  正常执行，然后通过completableFuture.completeExceptionally(e);告诉completableFuture任务发生异常了
     * 主线程接收到 程序继续处理，至结束
     */
    @Test
    public void test3() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            // 模拟执行耗时任务
            System.out.println("task doing...");
            try {
                Thread.sleep(3000);
                int i = 1/0;
            } catch (Exception e) {
                // 告诉completableFuture任务发生异常了
                completableFuture.completeExceptionally(e);
            }
            // 告诉completableFuture任务已经完成
            completableFuture.complete("ok");
        }).start();
        // 获取任务结果，如果没有完成会一直阻塞等待
        String result = completableFuture.get();
        System.out.println("计算结果:" + result);
    }
}
