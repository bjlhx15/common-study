package com.github.bjlhx15.common.thread.juc.collection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//生产者-使用者场景的一个用例
public class BlockingQueueDemo1 {
    public static void main(String[] args) {
        BlockingQueue q = new ArrayBlockingQueue(2);
        Producer p = new Producer(q);
        Consumer c1 = new Consumer(q);
        Consumer c2 = new Consumer(q);
        new Thread(p).start();
        new Thread(c1).start();
        new Thread(c2).start();
    }
}

class Producer implements Runnable {
    private final BlockingQueue queue;

    Producer(BlockingQueue q) {
        queue = q;
    }

    public void run() {
        try {
            while (true) {
                queue.put(produce());
            }
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    Object produce() {
        System.out.println("---produce---");
        return "produce";
    }
}

class Consumer implements Runnable {
    private final BlockingQueue queue;

    Consumer(BlockingQueue q) {
        queue = q;
    }

    public void run() {
        try {
            while (true) {
                consume(queue.take());
            }
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    void consume(Object x) {
        System.out.println("---consume---");}
}

