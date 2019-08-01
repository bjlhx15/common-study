package com.lhx.cloud.threadlock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lihongxu
 * @since 2019/3/25 上午9:14
 */
public class IntegerStudy {

    public static void main(String[] args) throws InterruptedException {

        Integer a = null;
        Integer b = null;

        System.out.println(a!=null&&a.equals(b));

    }


}
