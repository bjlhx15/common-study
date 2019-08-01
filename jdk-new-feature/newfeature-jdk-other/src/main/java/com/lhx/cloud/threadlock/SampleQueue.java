package com.lhx.cloud.threadlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/common-study)
 * @since 2019/3/26 下午4:23
 */
public class SampleQueue<T> {
    private Object[] elements;
    private Lock lock=new ReentrantLock();
    //队列是否为空
    private Condition notEmpty = lock.newCondition();
    //队列是否已满
    private Condition notFull = lock.newCondition();
    private int length=0,addIndex=0,removeIndex=0;

    public SampleQueue(int size) {
        this.elements = new Object[size];
    }

    public int getLength() {
        return length;
    }

    public void add(T object) throws InterruptedException {
        lock.lock();
        try {
            while (length==elements.length){
                System.out.println("队列已满，等待~~~");
                notFull.await();
            }
            elements[addIndex]=object;
            if(addIndex++ ==elements.length){
                addIndex = 0;
            }
            length++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
    public T remove() throws InterruptedException {
        lock.lock();
        try {
            while (length==0){
                System.out.println("队列为空，等待~~~");
                notEmpty.await();
            }
            Object element = elements[removeIndex];
            if(removeIndex++ ==elements.length){
                removeIndex = 0;
            }
            length--;
            notFull.signal();
            return (T) element;
        } finally {
            lock.unlock();
        }
    }
}
