package com.lhx.cloud.futruetask;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TestCompletableFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            //长时间的计算任务
            try {
                System.out.println("计算型任务开始");
                Thread.sleep(2000);
                return "计算型任务结束";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "·00";
        });
        System.out.println(future.get());
    }
}
