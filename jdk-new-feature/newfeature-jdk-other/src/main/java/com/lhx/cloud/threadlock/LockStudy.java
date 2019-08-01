package com.lhx.cloud.threadlock;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.*;

/**
 * @author lihongxu
 * @since 2019/3/25 上午9:14
 */
public class LockStudy {

    public static void main(String[] args) throws InterruptedException {
        Lock lock=new ReentrantLock();
        lock.lock();
        try {
//            synchronized ()
            //do something
        } catch (Exception e) {
        }finally {

            lock.unlock();
        }
//        ArrayBlockingQueue arrayBlockingQueue=new ArrayBlockingQueue();
        Semaphore semaphore=new Semaphore(2,true);
        semaphore.acquire();
        semaphore.release();

//        LockSupport
    }


}
