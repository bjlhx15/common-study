package com.github.bjlhx15.common.thread.juc.collection;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {
    public static void main(String[] args) {
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(20));
    }
}
