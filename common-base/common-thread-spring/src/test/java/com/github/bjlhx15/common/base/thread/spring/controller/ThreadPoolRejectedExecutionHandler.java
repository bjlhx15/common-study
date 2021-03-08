package com.github.bjlhx15.common.base.thread.spring.controller;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolRejectedExecutionHandler {
    private static final int THREADS_SIZE = 1;
    private static final int CAPACITY = 1;

    @Test
    public void testAbortPolicy() throws Exception {
        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(THREADS_SIZE, THREADS_SIZE, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(CAPACITY));
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 10; i++) {
            final int a=i;
            pool.execute(() -> {
                try {
                    System.out.println(new Date() + ":" + Thread.currentThread().getName()+"-"+a
                            + "；守护线程：" + Thread.currentThread().isDaemon());
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(3000);
        // 关闭线程池
        pool.shutdown();
    }

    @Test
    public void testDiscardPolicy() throws Exception {
        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(THREADS_SIZE, THREADS_SIZE, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(CAPACITY));
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        for (int i = 0; i < 10; i++) {
            final int a=i;
            pool.execute(() -> {
                try {
                    System.out.println(new Date() + ":" + Thread.currentThread().getName()+"-"+a
                            + "；守护线程：" + Thread.currentThread().isDaemon());
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(3000);
        // 关闭线程池
        pool.shutdown();
    }
    @Test
    public void testDiscardOldestPolicy() throws Exception {
        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(THREADS_SIZE, THREADS_SIZE, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(CAPACITY));
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        for (int i = 0; i < 10; i++) {
            final int a=i;
            pool.execute(() -> {
                try {
                    System.out.println(new Date() + ":" + Thread.currentThread().getName()+"-"+a
                            + "；守护线程：" + Thread.currentThread().isDaemon());
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(3000);
        // 关闭线程池
        pool.shutdown();
    }

    @Test
    public void testCallerRunsPolicy() throws Exception {
        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(THREADS_SIZE, THREADS_SIZE, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(CAPACITY));
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 10; i++) {
            final int a=i;
            pool.execute(() -> {
                try {
                    System.out.println(new Date() + ":" + Thread.currentThread().getName()+"-"+a
                            + "；守护线程：" + Thread.currentThread().isDaemon());
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(3000);
        // 关闭线程池
        pool.shutdown();
    }
}
