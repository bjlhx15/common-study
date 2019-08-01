package com.github.bjlhx15.common.http.webserver.controller.form;

import org.junit.Test;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;


public class PostControllerTest {

    @Test
    public void noparam() {
        RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());
        // new RestTemplate(); 等价于 new RestTemplate(new SimpleClientHttpRequestFactory());
        String s = restTemplate.postForObject("http://localhost:8080/form/noparam", null, String.class);
        System.out.println(s);
    }
    @Test
    public void noparam2() {
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        String s = restTemplate.postForObject("http://localhost:8080/form/noparam", null, String.class);
        System.out.println(s);
    }

    @Test
    public void noparam3() {
        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory());
        String s = restTemplate.postForObject("http://localhost:8080/form/noparam", null, String.class);
        System.out.println(s);
    }
    @Test
    public void params() {
    }

    @Test
    public void paramsobject() {
    }

    @Test
    public void paramsobject1() {
    }
}