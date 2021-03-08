package com.github.bjlhx15.common.base.thread.spring.controller;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.*;

public class TestCyclicBarrier {
    private static final int THREADS_SIZE = 1;
    private static final int CAPACITY = 1;

    CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
    @Test
    public void testAbortPolicy() throws Exception {
        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
//        ExecutorService pool = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10,
                0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20));

        for (int i = 0; i < 30; i++) {
            final int a=i;
            pool.execute(() -> {
                try {
                    cyclicBarrier.await();
                    System.out.println("---"+a+"--"+new Date() + ":" + Thread.currentThread().getName()
                            + "；守护线程：" + Thread.currentThread().isDaemon());
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(8000);
        // 关闭线程池
        pool.shutdown();
    }

    @Test
    public void testAbortPolicyTimeOut() throws Exception {
        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
//        ExecutorService pool = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(15, 20,
                0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20));

        for (int i = 0; i < 20; i++) {
            final int a=i;
            pool.execute(() -> {
                try {
//                    cyclicBarrier.await(1000,TimeUnit.MILLISECONDS);
                    cyclicBarrier.await();
                    System.out.println("---"+a+"--"+new Date() + ":" + Thread.currentThread().getName()
                            + "；守护线程：" + Thread.currentThread().isDaemon());
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(8000);
        // 关闭线程池
        pool.shutdown();
    }


    @Test
    public void testReturn() throws Exception {
        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
//        ExecutorService pool = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 20,
                0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20));

        for (int i = 0; i < 10; i++) {
            final int a=i;
            pool.execute(() -> {
                try {
                    if(a==4){
                        throw new RuntimeException("ooo");
                    }else{
                        System.out.println("---"+a+"--"+new Date() + ":" + Thread.currentThread().getName()
                                + "；守护线程：" + Thread.currentThread().isDaemon());
                        Thread.sleep(2000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(4000);
        // 关闭线程池
        pool.shutdown();
    }


    @Test
    public void testReturn1() throws Exception {
        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
//        ExecutorService pool = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 20,
                0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20));

        for (int i = 0; i < 10; i++) {
            final int a=i;
            pool.execute(() -> {
                try {
                    if(a==4){
                        return;
                    }else{
                        System.out.println("---"+a+"--"+new Date() + ":" + Thread.currentThread().getName()
                                + "；守护线程：" + Thread.currentThread().isDaemon());
                        Thread.sleep(2000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(4000);
        // 关闭线程池
        pool.shutdown();
    }
}

