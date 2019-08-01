package com.github.bjlhx15.common.http.webserver.controller.multipart;

import com.github.bjlhx15.common.http.webserver.controller.Result;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class PostControllerTest {
    RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());

    @Test
    public void noparam() {
        //设置请求头
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity entity = new HttpEntity(null, headers1);

//HttpServerErrorException$InternalServerError: 500 null
        Result result2 = restTemplate.postForObject("http://localhost:8080/multipart/noparam", entity, Result.class);
        System.out.println(result2);
    }

    @Test
    public void params() {
        //设置请求头
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("id", "aaaa");
        valueMap.add("msg", "dddd");
        HttpEntity entity = new HttpEntity(valueMap, headers1);

        Result result2 = restTemplate.postForObject("http://localhost:8080/multipart/params", entity, Result.class);
        System.out.println(result2);
    }

    @Test
    public void params1() {
        //设置请求头
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("id", "aaaa");
        valueMap.add("msg", "dddd");
        HttpEntity entity = new HttpEntity(valueMap, headers1);

        Result result2 = restTemplate.postForObject("http://localhost:8080/multipart/params1", entity, Result.class);
        System.out.println(result2);
    }

    @Test
    public void paramsobject() {
        //设置请求头
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("name", "aaaa");
        valueMap.add("age", "111");
        HttpEntity entity = new HttpEntity(valueMap, headers1);

        //接收多个 url和传输的
        Result result21 = restTemplate.postForObject("http://localhost:8080/multipart/paramsobject?name=111&age=112", entity, Result.class);
        System.out.println(result21);

        Result result2 = restTemplate.postForObject("http://localhost:8080/multipart/paramsobject", entity, Result.class);
        System.out.println(result2);
    }

    @Test
    public void paramsobject1() {
        //设置请求头
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("name", "aaaa");
        valueMap.add("age", "111");
        HttpEntity entity = new HttpEntity(valueMap, headers1);

        //接收 RequestBody 其实是application/json此时会： $UnsupportedMediaType: 415 null
        Result result21 = restTemplate.postForObject("http://localhost:8080/multipart/paramsobject1?name=111&age=112", entity, Result.class);
        System.out.println(result21);
    }
}