package com.github.bjlhx15.common.base.thread.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/")
public class IndexController {
    @RequestMapping(value = "test",method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        return "ok";
    }

    @RequestMapping(value = "test2",method = RequestMethod.GET)
    @ResponseBody
    public Object test2() {
        Map<String, String> map = new HashMap<>();
        map.put("code", "2000");
        return map;
    }


    public static void main(String[] args) {
        System.out.println(getTest());
        //程序立即打印 Ok
        //同时 都是用户线程 会等方法中的全部线程执行完毕后退出
    }
    public static String getTest(){
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            fixedThreadPool.submit(()->{
                try {

                    Thread.sleep(2000);
                    System.out.println(new Date()+":"+Thread.currentThread().getName()
                            +"；守护线程："+Thread.currentThread().isDaemon());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        fixedThreadPool.shutdown();
        return "ok";
    }
}
