package com.github.bjlhx15.common.thread.juc.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairAndUnfairTest {
    private static CountDownLatch start;


    public static void main(String[] args) {
        Lock fairLock = new MyReentrantLock(true);
        Lock unfairLock = new MyReentrantLock(false);

//        testLock(fairLock);
		testLock(unfairLock);
    }

    private static void testLock(Lock lock) {
        start = new CountDownLatch(1);
        for (int i = 0; i < 5; i++) {
            Thread thread = new Worker(lock);
            thread.setName("" + i);
            thread.start();
        }
        start.countDown();
    }

    private static class MyReentrantLock extends ReentrantLock {
        public MyReentrantLock(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads() {
            List<Thread> arrayList = new ArrayList<Thread>(super.getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }
    }

    private static class Worker extends Thread {
        private Lock lock;

        public Worker(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 连续两次打印当前的Thread和等待队列中的Thread
            for (int i = 0; i < 2; i++) {
                lock.lock();
                try {
                    System.out.println("Lock by [" + getName() + "], Waiting by " + ((MyReentrantLock) lock).getQueuedThreads());
                } finally {
                    lock.unlock();
                }
            }
        }

        public String toString() {
            return getName();
        }
    }
}
