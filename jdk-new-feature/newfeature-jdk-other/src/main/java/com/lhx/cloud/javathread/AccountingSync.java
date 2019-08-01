package com.lhx.cloud.javathread;

import java.util.Random;
import java.util.Scanner;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/common-study)
 * @since 2019/3/20 下午3:39
 */
public class AccountingSync implements Runnable {
    static AccountingSync instance = new AccountingSync();
    int i = 0;
    static int j = 0;

    @Override
    public void run() {
        for (int j = 0; j < 4; j++) {
            //this,当前实例对象锁
            synchronized (this) {
                System.out.println("run 获取锁线程ID：" + Thread.currentThread().getId());
                if (j == 2) {
                    System.out.println("----断点----");
                }
                i++;
                increase();//synchronized的可重入性
            }
        }
    }

    public synchronized void increase() {
        System.out.println("increase 获取锁线程ID：" + Thread.currentThread().getId());
        try {
            int i = new Random().nextInt(500);
            System.out.println("increase 休眠时间：" + i);
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        j++;
    }


    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(instance.i);
    }
}
