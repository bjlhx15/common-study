package com.github.bjlhx15.common.base.thread.spring.controller;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class ThreadPoolExecutorCycTest {


    @Test
    public void poolNoCountDownLatch() throws Exception {
        int parrelnum = 100;
        List<Future<String>> futureList = new ArrayList<>();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(parrelnum);
        for (int i = 0; i < parrelnum; i++) {
            Future<String> submit = fixedThreadPool.submit(() -> {
                try {
                    System.out.println("======-----------------------------" + LocalDateTime.now());
//                    System.out.println("======-----------------------------"+ LocalDateTime.now()
//                            +":"+Thread.currentThread().getName()
//                            +"；守护线程："+Thread.currentThread().isDaemon());
                    Thread.sleep(new Random().nextInt(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "ok:" + LocalDateTime.now();
            });
            futureList.add(submit);
        }
        fixedThreadPool.shutdown();

        try {
            System.out.println(LocalDateTime.now() + ":" + "1秒后统一开始");
            Thread.sleep(1000);
            for (Future<String> future : futureList) {
                System.out.println(future.get());
            }
            System.out.println(LocalDateTime.now() + ":" + "全部结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void poolCyclicBarrier() throws Exception {
        int parrelnum = 5;
        //所有线程阻塞，然后统一开始
        CyclicBarrier cyclicBarrier = new CyclicBarrier(parrelnum);
        List<Future<String>> futureList = new ArrayList<>();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(parrelnum);
        for (int i = 0; i < 15; i++) {
            Future<String> submit = fixedThreadPool.submit(() -> {
                try {
                    Thread.sleep(new Random().nextInt(2000));
                    //begin 阻塞所有线程
                    cyclicBarrier.await();
                    System.out.println("======-----------------------------并发：" + LocalDateTime.now());
                    Thread.sleep(new Random().nextInt(2000));
                    System.out.println("======-----------------------------" + LocalDateTime.now()
                            + ":" + Thread.currentThread().getName()
                            + "；守护线程：" + Thread.currentThread().isDaemon());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "ok:" + LocalDateTime.now();
            });
            futureList.add(submit);
        }

        for (Future<String> future : futureList) {
            future.get();
//                System.out.println(future.get());
        }
    }

}