package com.github.bjlhx15.common.http.webserver.controller.json;

import com.github.bjlhx15.common.http.webserver.controller.Person;
import com.github.bjlhx15.common.http.webserver.controller.Result;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class PostControllerTest {
    RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());

    @Test
    public void noparam() {
        //设置请求头，为json
        HttpHeaders headers1 = new HttpHeaders();
        headers1.add("Content-Type", "application/json");
        HttpEntity entity = new HttpEntity(null, headers1);

        Result result2 = restTemplate.postForObject("http://localhost:8080/json/noparam", entity, Result.class);
        System.out.println(result2);
    }

    @Test
    public void params() {
        Person person = new Person();
        person.setName("test");
        person.setAge(23);
        //设置请求头，为json
        HttpHeaders headers1 = new HttpHeaders();
        headers1.add("Content-Type", "application/json");
        HttpEntity entity = new HttpEntity(person, headers1);

        Result result2 = restTemplate.postForObject("http://localhost:8080/json/params", entity, Result.class);
        System.out.println(result2);

        Result result3 = restTemplate.postForObject("http://localhost:8080/json/params?id=22", entity, Result.class);
        System.out.println(result3);
    }

    @Test
    public void params1() {

        Person person = new Person();
        person.setName("test");
        person.setAge(23);
        //设置请求头，为json
        HttpHeaders headers1 = new HttpHeaders();
        headers1.add("Content-Type", "application/json");
        HttpEntity entity = new HttpEntity(person, headers1);

//        Result result2 = restTemplate.postForObject("http://localhost:8080/json/params1", entity, Result.class);
//        System.out.println(result2);

        Result result3 = restTemplate.postForObject("http://localhost:8080/json/params1?id=22&msg=999", entity, Result.class);
        System.out.println(result3);
    }

    @Test
    public void paramsobject() {
        Person person = new Person();
        person.setName("test");
        person.setAge(23);
        //设置请求头，为json
        HttpHeaders headers1 = new HttpHeaders();
        headers1.add("Content-Type", "application/json");
        HttpEntity entity = new HttpEntity(person, headers1);
        //服务端 没有使用 RequestBody ，无法接收到参数
        Result result2 = restTemplate.postForObject("http://localhost:8080/json/paramsobject", entity, Result.class);
        System.out.println(result2);


        Result result3 = restTemplate.postForObject("http://localhost:8080/json/paramsobject?name=aaa", entity, Result.class);
        System.out.println(result3);

    }

    @Test
    public void paramsobject1() {
        Person person = new Person();
        person.setName("test");
        person.setAge(23);
        //设置请求头，为json
        HttpHeaders headers1 = new HttpHeaders();
        headers1.add("Content-Type", "application/json");
        HttpEntity entity = new HttpEntity(person, headers1);
        Result result2 = restTemplate.postForObject("http://localhost:8080/json/paramsobject1", entity, Result.class);
        System.out.println(result2);
    }
}