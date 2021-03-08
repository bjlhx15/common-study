package com.github.bjlhx15.common.base.thread.spring.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

//帮我们创建容器
@RunWith(SpringJUnit4ClassRunner.class)
//指定创建容器时使用哪个配置文件
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringThreadPoolExecutorFullTest {

    @Autowired
    @Qualifier("taskExecutor2")
    private ThreadPoolTaskExecutor taskExecutor;


    @Test
    public void newFixedThreadPoolTestSubmit() throws Exception {
        List<Future<?>> list=new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            final int tmp=i;
            taskExecutor.execute(()->{
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"___"+LocalDateTime.now()+"::"+tmp);
            });
        }
        Thread.sleep(30000);
    }


}