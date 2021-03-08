package com.github.bjlhx15.common.thread.juc.collection.jdk8stream;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static org.junit.Assert.*;

public class TStreamTest {
    @Test
    public void testParallelStream() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        numbers.parallelStream()
                .forEach(p -> System.out.println(p));
    }


    @Test
    public void testParallelStream2() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        numbers.parallelStream()
                .forEachOrdered(p -> System.out.println(p));
    }

    @Test
    public void testSystemCPU() {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    @Test
    public void testParallelCPU() {
        System.out.println(ForkJoinPool.getCommonPoolParallelism());
    }

    @Test
    public void testSetParallelCPU() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "20");
        System.out.println(ForkJoinPool.getCommonPoolParallelism());
    }

    @Test
    public void testSetParallelMutli() throws ExecutionException, InterruptedException {
        int[] threadCountArr = {2, 4, 6};
        List<Integer> para = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            para.add(i);

        }
        for (int threadCount : threadCountArr) {
            new ForkJoinPool(threadCount).submit(() -> {
                para.parallelStream().forEach(i -> {
                    System.out.println(Thread.currentThread().getName() + ":" + i);
                });
            }).get();
        }
    }

    @Test
    public void testSetParallelMutli2() throws ExecutionException, InterruptedException {
        List<Integer> para = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            para.add(i);

        }
        para.parallelStream().forEach(i -> {
            try {
//                Thread.sleep(2000);
                Thread.sleep(new Random().nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(LocalDateTime.now() + "||" + Thread.currentThread().getName() + ":" + i);
        });
    }

    @Test
    public void testSetParallelMutli3() throws ExecutionException, InterruptedException {
        List<Integer> para = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            para.add(i);

        }
        para.parallelStream().forEach(i -> {
            System.out.println("__1111__");
//            try {
////                Thread.sleep(2000);
////                Thread.sleep(new Random().nextInt(2000));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            if (i == 7)
                return;
            System.out.println(LocalDateTime.now() + "||" + Thread.currentThread().getName() + ":" + i);
        });
    }

    //    有问题
    @Test
    public void testExec() throws ExecutionException, InterruptedException {
        List<Integer> a = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            a.add(i);
        }
        List<Integer> b = Lists.newArrayList();
        a.parallelStream().forEach(p -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (p % 5 == 0) {
                b.add(p);
            }
        });
        System.out.println("===========" + b.size());
        b.forEach(p -> System.out.print(p + " "));
    }

    //改成串行
    @Test
    public void testExec2() throws ExecutionException, InterruptedException {
        List<Integer> a = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            a.add(i);
        }
        List<Integer> b = Lists.newArrayList();
        a.stream().forEach(p -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (p % 5 == 0) {
                b.add(p);
            }
        });
        System.out.println("===========" + b.size());
        b.forEach(p -> System.out.print(p + " "));
    }

    @Test
    public void testExec3() throws ExecutionException, InterruptedException {
        List<Integer> a = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            a.add(i);
        }
        List<Integer> b = Lists.newCopyOnWriteArrayList();
        a.parallelStream().forEach(p -> {
            try {
//                Thread.sleep(100);
                Thread.sleep(new Random().nextInt(5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (p % 5 == 0) {
                b.add(p);
            }
        });
        System.out.println("===========" + b.size());
        b.forEach(p -> System.out.print(p + " "));
    }

    @Test
    public void testExec4() throws ExecutionException, InterruptedException {
        List<Integer> a = Lists.newArrayList();
        for (int i = 0; i < 200; i++) {
            a.add(i);
        }
        List<Integer> b = Lists.newArrayList();

        System.out.println("===========" + b.size());
        b.forEach(p -> System.out.print(p + " "));
    }


}