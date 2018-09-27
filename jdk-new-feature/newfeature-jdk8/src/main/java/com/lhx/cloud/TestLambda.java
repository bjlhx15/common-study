package com.lhx.cloud;

public class TestLambda {
    public static void main(String[] args) {
        // Java 8 传统方式：
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Java 8 传统方式");
            }
        }).start();

        // Java 8 lambda方式：
        new Thread(() -> System.out.println("Java 8 lambda方式!") ).start();

        // Java 8 lambda方式：
        Runnable runnable = () -> System.out.println("Java 8 lambda方式!多线程");
        new Thread(runnable).start();
    }
}
