package com.github.bjlhx15.common.thread.juc.collection;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    // 自定义工作线程
    private static class Worker extends Thread {
        private CountDownLatch countDownLatch;

        public Worker(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            super.run();

            try {
                countDownLatch.await();
                System.out.println(Thread.currentThread().getName() + "开始执行");
                // 工作线程开始处理，这里用Thread.sleep()来模拟业务处理
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "执行完毕");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        for (int i = 0; i < 3; i++) {
            System.out.println("创建工作线程" + i);
            Worker worker = new Worker(countDownLatch);
            worker.start();
        }

        // 工作线程需要等待主线程准备操作完毕才可以执行，这里用Thread.sleep()来模拟准备操作
        Thread.sleep(1000);
        System.out.println("主线程准备完毕");

        countDownLatch.countDown();
    }
}
