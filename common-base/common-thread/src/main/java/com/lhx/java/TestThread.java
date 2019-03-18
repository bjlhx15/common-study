package com.lhx.java;

import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestThread implements Runnable {
    /**
     * 线程私有属性，创建线程时创建
     */
    private Integer num = 0;


    public TestThread(Integer num) {
        this.num = num;
    }

    @Override
    public void run() {

        UserService userService = SpringContextUtil.getBeanByClass(UserService.class);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //System.out.println("thread:" + Thread.currentThread().getName() + ",time:" + sdf1.format(new Date()) + ",num:" + num);
        try {
            System.err.println(userService.get());
            //使线程睡眠，模拟线程阻塞情况
            TimeUnit.SECONDS.sleep(3);
            System.out.println("thread:" + Thread.currentThread().getName() + ",time:" + sdf1.format(new Date()) + ",num:" + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
