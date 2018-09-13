package com.lhx.java.controller;

import com.lhx.java.SpringContextUtil;
import com.lhx.java.TestThread;
import com.lhx.java.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("testThread")
    public String testThread() {
        System.out.println(userService.get());
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            fixedThreadPool.submit(new TestThread((i + 1)));
        }
        fixedThreadPool.shutdown();
        return "ok";
    }
}
