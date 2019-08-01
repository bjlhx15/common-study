package com.github.bjlhx15.common.http.webserver.controller;

import org.junit.Test;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


public class RestTemplateTest {

    @Test
    public void SimpleClientHttpRequestFactory() {
        RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());
        // new RestTemplate(); 等价于 new RestTemplate(new SimpleClientHttpRequestFactory());
        String s = restTemplate.postForObject("http://localhost:8080/form/noparam", null, String.class);
        System.out.println(s);
    }
    @Test
    public void HttpComponentsClientHttpRequestFactory() {
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        String s = restTemplate.postForObject("http://localhost:8080/form/noparam", null, String.class);
        System.out.println(s);
    }

    @Test
    public void OkHttp3ClientHttpRequestFactory() {
        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory());
        String s = restTemplate.postForObject("http://localhost:8080/form/noparam", null, String.class);
        System.out.println(s);
    }
}