package com.github.bjlhx15.common.base.thread.spring.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TaskRunnable implements Runnable {
    // 线程私有属性，创建线程时创建
    private Integer num = 0;

    public TaskRunnable(Integer num) {
        this.num = num;
    }

    @Override
    public void run() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println("thread:" + Thread.currentThread().getName() + ",time:" + sdf1.format(new Date()) + ",num:" + num);
        try {
            //使线程睡眠，模拟线程阻塞情况
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task "+num+"执行完毕");
    }
}

