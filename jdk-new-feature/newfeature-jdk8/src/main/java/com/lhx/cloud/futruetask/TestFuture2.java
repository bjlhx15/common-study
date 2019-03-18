package com.lhx.cloud.futruetask;

import java.util.concurrent.*;

public class TestFuture2 {
    public static void main(String[] args) {
        Callable<String> callable1=()->{
            Thread.sleep(2000);
            return Thread.currentThread().getName();
        };
        Callable<String> callable2=()->{
            Thread.sleep(3000);
            return Thread.currentThread().getName();
        };
        FutureTask<String> futureTask1 = new FutureTask<>(callable1);// 将Callable写的任务封装到一个由执行者调度的FutureTask对象
        FutureTask<String> futureTask2 = new FutureTask<>(callable2);

        ExecutorService executor = Executors.newFixedThreadPool(2);        // 创建线程池并返回ExecutorService实例
        executor.execute(futureTask1);  // 执行任务
        executor.execute(futureTask2);
        //同时开启了两个任务
        long startTime = System.currentTimeMillis();
        while (true) {
            try {
                if(futureTask1.isDone() && futureTask2.isDone()){//  两个任务都完成
                    System.out.println("Done");
                    executor.shutdown();                          // 关闭线程池和服务
                    return;
                }

                if(!futureTask1.isDone()){ // 任务1没有完成，会等待，直到任务完成
                    System.out.println("FutureTask1 output="+futureTask1.get());
                }

                System.out.println("Waiting for FutureTask2 to complete");
                String s = futureTask2.get(200L, TimeUnit.MILLISECONDS);
                if(s !=null){
                    System.out.println("FutureTask2 output="+s);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }catch(TimeoutException e){
                //do nothing
            }
            System.out.println((System.currentTimeMillis()-startTime));
        }
    }
}
