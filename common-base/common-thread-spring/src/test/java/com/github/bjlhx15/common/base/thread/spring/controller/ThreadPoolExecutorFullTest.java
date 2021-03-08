package com.github.bjlhx15.common.base.thread.spring.controller;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolExecutorFullTest {

    //submit 主线程关闭，多线程任务同时关闭 没有得到响应
    @Test
    public void newFixedThreadPoolTestSubmit() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 50; i++) {
            fixedThreadPool.submit(()->{
                try {

                    Thread.sleep(2000);
                    System.out.println(new Date()+":"+Thread.currentThread().getName()
                            +"；守护线程："+Thread.currentThread().isDaemon());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        fixedThreadPool.shutdown();
    }

    //execute 主线程关闭，多线程任务同时关闭 没有得到响应
    @Test
    public void newFixedThreadPoolTestExecute() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 50; i++) {
            fixedThreadPool.execute(()->{
                try {
                    Thread.sleep(2000);
                    System.out.println(new Date()+":"+Thread.currentThread().getName()
                            +"；守护线程："+Thread.currentThread().isDaemon());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        fixedThreadPool.shutdown();
    }
    //submit 主线程 显示调用get 阻塞 增加了回调处理
    @Test
    public void newFixedThreadPoolTestSubmitGet() throws Exception {
        List<Future<String>> futureList = new ArrayList<>();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 50; i++) {
            Future<String> submit = fixedThreadPool.submit(() -> {
                try {
                    System.out.println("======-----------------------------"+ LocalDateTime.now()
                            +":"+Thread.currentThread().getName()
                            +"；守护线程："+Thread.currentThread().isDaemon());
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "ok:"+LocalDateTime.now();
            });
            futureList.add(submit);
        }
        fixedThreadPool.shutdown();

        for (Future<String> future : futureList) {
            System.out.println(future.get());
        }


    }


}