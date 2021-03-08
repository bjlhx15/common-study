package com.github.bjlhx15.common.base.thread.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/pool")
public class ThreadPoolExecutorController {
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    //容器主线程 持续运行，程序会立即返回，然后后台默默执行开启的线程
    @RequestMapping(value = "/poolSubmit", method = RequestMethod.GET)
    @ResponseBody
    public Object poolSubmit() {
        for (int i = 0; i < 10; i++) {
            fixedThreadPool.execute(() -> {
                try {
                    Thread.sleep(2000);
                    System.out.println(new Date());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        fixedThreadPool.shutdown();
        return "ok";
    }

    //容器主线程 持续运行，程序阻塞
    @RequestMapping(value = "/poolSubmitGet", method = RequestMethod.GET)
    @ResponseBody
    public Object poolSubmitGet() {
        List<Future<String>> futureList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Future<String> submit = fixedThreadPool.submit(() -> {
                try {
                    System.out.println("======-----------------------------" + new Date());
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "ok:" + new Date();
            });
            futureList.add(submit);
        }

        for (Future<String> future : futureList) {
            try {
                System.out.println(future.get() + "ok");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "ok";
    }

    public static void main(String[] args) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 20; i++) {
            fixedThreadPool.submit(() -> {
                try {

                    Thread.sleep(2000);
                    System.out.println(new Date() + ":" + Thread.currentThread().getName()
                            + "；守护线程：" + Thread.currentThread().isDaemon());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        fixedThreadPool.shutdown();
    }


}
