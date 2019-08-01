package com.github.bjlhx15.common.thread.juc.collection;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    // 自定义工作线程
    private static class Worker extends Thread {
        private CountDownLatch countDownLatch;
        private Semaphore semaphore;

        public Worker(CountDownLatch countDownLatch, Semaphore semaphore) {
            this.countDownLatch = countDownLatch;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            super.run();

            boolean hasAcquire = false;
            try {
                countDownLatch.await();
                semaphore.acquire();
                hasAcquire = true;
                System.out.println(Thread.currentThread().getName() + "开始执行");
                // 工作线程开始处理，这里用Thread.sleep()来模拟业务处理
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "执行完毕");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (hasAcquire) {
                    semaphore.release();
                }
            }
        }
    }

    public static void main(String[] args) {
        int threadCount = 10;
        int permitCount = 3;

        CountDownLatch countDownLatch = new CountDownLatch(1);
        Semaphore semaphore = new Semaphore(permitCount);

        for (int i = 0; i < threadCount; i++) {
            Worker worker = new Worker(countDownLatch, semaphore);
            worker.start();
        }

        countDownLatch.countDown();
    }
}
