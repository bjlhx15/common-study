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
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//帮我们创建容器
@RunWith(SpringJUnit4ClassRunner.class)
//指定创建容器时使用哪个配置文件
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringThreadPoolExecutorTest {

    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    @Qualifier("executor")
    private ThreadPoolTaskExecutor executor;

    @Test
    public void newFixedThreadPoolTestSubmit() throws Exception {
        List<Future<?>> list=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Future<?> submit = taskExecutor.submit(new DataTask("ssss" + i));
            list.add(submit);
        }
        for (Future<?> future : list) {
            future.get();
        }
    }


    @Test
    public void testTaskExecutor() throws Exception {
        List<Future<?>> list=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Future<?> submit = executor.submit(new DataTask("ssss" + i));
            list.add(submit);
        }
        for (Future<?> future : list) {
            future.get();
        }
    }
    class DataTask implements Runnable {
        private String a;

        public DataTask(String a) {
            this.a = a;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("-------"+ LocalDateTime.now()+a);
        }
    }

    @Test
    public void testNum() throws Exception {
        int i = taskExecutor.getMaxPoolSize() ;
        
        System.out.println(i);
    }
}