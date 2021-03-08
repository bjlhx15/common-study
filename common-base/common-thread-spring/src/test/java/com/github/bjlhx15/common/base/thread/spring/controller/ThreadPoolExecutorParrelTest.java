package com.github.bjlhx15.common.base.thread.spring.controller;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class ThreadPoolExecutorParrelTest {

    @Test
    public void testParrel() {
        //所有线程阻塞，然后统一开始
        CountDownLatch begin = new CountDownLatch(1);
        //主线程阻塞，直到所有分线程执行完毕
        CountDownLatch end = new CountDownLatch(5);
        for(int i = 0; i < 5; i++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        begin.await();
                        System.out.println(LocalDateTime.now()+":"+Thread.currentThread().getName() + " 开始执行");
                        Thread.sleep(new Random().nextInt(2000));
                        System.out.println(LocalDateTime.now()+":"+Thread.currentThread().getName() + " 结束");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally {
                        end.countDown();
                    }

                }
            });
            thread.start();
        }

        try {
            System.out.println(LocalDateTime.now()+":"+"1秒后统一开始");
            Thread.sleep(1000);
            begin.countDown();

            end.await();
            System.out.println(LocalDateTime.now()+":"+"全部结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void poolNoCountDownLatch() throws Exception {
        int parrelnum=100;
        List<Future<String>> futureList = new ArrayList<>();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(parrelnum);
        for (int i = 0; i < parrelnum; i++) {
            Future<String> submit = fixedThreadPool.submit(() -> {
                try {
                    System.out.println("======-----------------------------"+ LocalDateTime.now());
//                    System.out.println("======-----------------------------"+ LocalDateTime.now()
//                            +":"+Thread.currentThread().getName()
//                            +"；守护线程："+Thread.currentThread().isDaemon());
                    Thread.sleep(new Random().nextInt(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "ok:"+LocalDateTime.now();
            });
            futureList.add(submit);
        }
        fixedThreadPool.shutdown();

        try {
            System.out.println(LocalDateTime.now()+":"+"1秒后统一开始");
            Thread.sleep(1000);
            for (Future<String> future : futureList) {
                System.out.println(future.get());
            }
            System.out.println(LocalDateTime.now()+":"+"全部结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void poolCountDownLatch() throws Exception {
        int parrelnum=100;
        //所有线程阻塞，然后统一开始
        CountDownLatch begin = new CountDownLatch(1);
        //主线程阻塞，直到所有分线程执行完毕
        CountDownLatch end = new CountDownLatch(parrelnum);
        List<Future<String>> futureList = new ArrayList<>();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(parrelnum);
        for (int i = 0; i < parrelnum; i++) {
            Future<String> submit = fixedThreadPool.submit(() -> {
                try {
                    //begin 阻塞所有线程
                    begin.await();

                    System.out.println("======-----------------------------"+ LocalDateTime.now());
//                    System.out.println("======-----------------------------"+ LocalDateTime.now()
//                            +":"+Thread.currentThread().getName()
//                            +"；守护线程："+Thread.currentThread().isDaemon());
                    Thread.sleep(new Random().nextInt(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    //end 执行完代码减一
                    end.countDown();
                }
                return "ok:"+LocalDateTime.now();
            });
            futureList.add(submit);
        }
        fixedThreadPool.shutdown();

        try {
            System.out.println(LocalDateTime.now()+":"+"1秒后统一开始");
            Thread.sleep(1000);
            //begin 减一 = 0 唤醒 阻塞的所有线程
            begin.countDown();
            for (Future<String> future : futureList) {
                System.out.println(future.get());
            }
            //end 等待所有代码执行完毕
            end.await();
            System.out.println(LocalDateTime.now()+":"+"全部结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}