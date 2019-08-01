package com.lhx.cloud.threadlock;

import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.*;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/common-study)
 * @since 2019/3/26 下午3:23
 */
public class LockStudyTest {
    @Test
    public void testLock() throws Exception {
        final Lock lock = new ReentrantLock();
        lock.lock();
        Thread.sleep(1000);
        Thread t1 = new Thread(() -> {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " interrupted.");

        });
        t1.start();
        Thread.sleep(1000);
        t1.interrupt();//lock()忽视interrupt(), 拿不到锁就 一直阻塞
        Thread.sleep(10000);
    }

    @Test
    public void testlockInterruptibly() throws Exception {
        final Lock lock = new ReentrantLock();
        lock.lock();
        Thread.sleep(1000);
        Thread t1 = new Thread(() -> {
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted.");
            }
        });
        t1.start();
        Thread.sleep(1000);
        t1.interrupt();
        Thread.sleep(10000);
    }

    @Test
    public void testtryLock() throws Exception {
        final Lock lock = new ReentrantLock();
        lock.lock();
        Thread.sleep(1000);
        Thread t1 = new Thread(() -> {
            boolean b = lock.tryLock();
            System.out.println(Thread.currentThread().getName() + " tryLock."+b);

        });
        t1.start();
        Thread.sleep(1000);
        t1.interrupt();
        Thread.sleep(10000);
    }
}