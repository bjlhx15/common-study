package com.github.bjlhx15.common.http.webserver.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//帮我们创建容器
@RunWith(SpringJUnit4ClassRunner.class)
//指定创建容器时使用哪个配置文件
@ContextConfiguration("classpath:applicationContext.xml")
public class RestTemplatePoolTest {
    /**
     * SimpleClientHttpRequestFactoryNoPool
     * 没有连接池 并发 受服务端容器限制【如tomcat默认200】
     */
    @Test
    public void SimpleClientHttpRequestFactoryNoPool() throws Exception {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(20000);
        requestFactory.setConnectTimeout(20000);

        // 添加转换器
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new FormHttpMessageConverter());
//        messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        RestTemplate restTemplate = new RestTemplate(messageConverters);
        restTemplate.setRequestFactory(requestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());


        List<Future<String>> list=new ArrayList<Future<String>>();
        long start = System.currentTimeMillis();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(200);
        for (int i = 0; i < 200; i++) {
            Future<String> submit = fixedThreadPool.submit(() -> {
                String s = restTemplate.postForObject("http://localhost:8080/form/noparam", null, String.class);
                return s;
            });
            list.add(submit);
        }
        fixedThreadPool.shutdown();
        System.out.println("装载耗时：" + (System.currentTimeMillis() - start) + "ms");
        System.out.println("个数：" + list.size());
        for (Future<String> future : list) {
            System.out.println(future.get());
        }
        System.out.println("总计执行完毕耗时：" + (System.currentTimeMillis() - start) + "ms");
    }
    @Autowired
    private RestTemplate restTemplateHttpClient;

    /**
     * httpClient 有 连接池 并行 按配置
     * @throws Exception
     */
    @Test
    public void HttpComponentsClientHttpRequestFactoryPool() throws Exception {
        List<Future<String>> list=new ArrayList<Future<String>>();
        long start = System.currentTimeMillis();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(500);
        for (int i = 0; i < 500; i++) {
            Future<String> submit = fixedThreadPool.submit(() -> {
                String s = restTemplateHttpClient.postForObject("http://localhost:8080/form/noparam", null, String.class);
                return s;
            });
            list.add(submit);
        }
        fixedThreadPool.shutdown();
        System.out.println("装载耗时：" + (System.currentTimeMillis() - start) + "ms");
        System.out.println("个数：" + list.size());
        for (Future<String> future : list) {
            System.out.println(future.get());
        }
        System.out.println("总计执行完毕耗时：" + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * httpClient 不主动设置 连接池 并行 5个任务
     * @throws Exception
     */
    @Test
    public void HttpComponentsClientHttpRequestFactoryNoPool() throws Exception {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        // 开启 5个
        RestTemplate restTemplateNoPool= new RestTemplate(factory);
        List<Future<String>> list=new ArrayList<Future<String>>();
        long start = System.currentTimeMillis();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            Future<String> submit = fixedThreadPool.submit(() -> {
                String s = restTemplateNoPool.postForObject("http://localhost:8080/form/noparam", null, String.class);
                return s;
            });
            list.add(submit);
        }
        fixedThreadPool.shutdown();
        System.out.println("装载耗时：" + (System.currentTimeMillis() - start) + "ms");
        System.out.println("个数：" + list.size());
        for (Future<String> future : list) {
            System.out.println(future.get());
        }
        System.out.println("总计执行完毕耗时：" + (System.currentTimeMillis() - start) + "ms");
    }
    /**
     *没有连接池 并发 受服务端容器限制【如tomcat默认200】
     */
    @Test
    public void OkHttp3ClientHttpRequestFactoryPool() throws Exception {
        OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();

        RestTemplate restTemplateOkHttp = new RestTemplate(factory);
        List<Future<String>> list=new ArrayList<Future<String>>();
        long start = System.currentTimeMillis();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(300);
        for (int i = 0; i < 300; i++) {
            Future<String> submit = fixedThreadPool.submit(() -> {
                String s = restTemplateOkHttp.postForObject("http://localhost:8080/form/noparam", null, String.class);
                return s;
            });
            list.add(submit);
        }
        fixedThreadPool.shutdown();
        System.out.println("装载耗时：" + (System.currentTimeMillis() - start) + "ms");
        System.out.println("个数：" + list.size());
        for (Future<String> future : list) {
            System.out.println(future.get());
        }
        System.out.println("总计执行完毕耗时：" + (System.currentTimeMillis() - start) + "ms");
    }
}