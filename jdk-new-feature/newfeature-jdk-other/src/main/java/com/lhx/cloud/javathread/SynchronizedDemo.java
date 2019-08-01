package com.lhx.cloud.javathread;

public class SynchronizedDemo {
    public void method() {
        synchronized (this) {
            System.out.println("Method 1 start");
        }
    }

    public static void main(String[] args) {
        SynchronizedDemo demo=new SynchronizedDemo();
        System.out.println(demo.hashCode());
        demo.method();
        System.out.println("end");
        int i = System.identityHashCode(demo);
        System.out.println(demo.hashCode());
    }
}
