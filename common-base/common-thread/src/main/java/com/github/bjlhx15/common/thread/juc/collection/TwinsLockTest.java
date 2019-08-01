package com.github.bjlhx15.common.thread.juc.collection;

public class TwinsLockTest {
    private final static TwinsLock twinsLock = new TwinsLock();

    private static final class Worker extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                twinsLock.lock();
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    twinsLock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 启动10个线程
        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker();
            worker.setDaemon(true);
            worker.start();
        }

        Thread.sleep(10000);
    }
}