package com.github.bjlhx15.common.http.webserver.controller.multipart;

import com.github.bjlhx15.common.http.webserver.controller.Result;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

public class PostFileControllerTest {
    RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());

    @Test
    public void upload() {

        //设置请求头
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
        FileSystemResource fileSystemResource = new FileSystemResource(new File("/Users/lihongxu6/IdeaProjects/common-study/common-http/http-client-test/target/test-classes/test.txt"));
        valueMap.add("file", fileSystemResource);
        HttpEntity entity = new HttpEntity(valueMap, headers1);

        Result result2 = restTemplate.postForObject("http://localhost:8080/multipart/upload", entity, Result.class);
        System.out.println(result2);
    }

    @Test
    public void uploadFileParam() {
        RestTemplate restTemplate2 = new RestTemplate(new OkHttp3ClientHttpRequestFactory());
        //设置请求头
        HttpHeaders headers1 = new HttpHeaders();
        headers1.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
        FileSystemResource fileSystemResource = new FileSystemResource(new File("/Users/lihongxu6/IdeaProjects/common-study/common-http/http-client-test/target/test-classes/test.txt"));
        valueMap.add("file", fileSystemResource);
        valueMap.add("msg", "dddddd");
        HttpEntity entity = new HttpEntity(valueMap, headers1);

        Result result2 = restTemplate2.postForObject("http://localhost:8080/multipart/uploadFileParam", entity, Result.class);
        System.out.println(result2);
    }
}