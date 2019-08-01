package com.github.bjlhx15.common.http.webserver.controller.form;

import com.github.bjlhx15.common.http.webserver.controller.Person;
import com.github.bjlhx15.common.http.webserver.controller.Result;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


public class PostControllerTest {
    RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());

    @Test
    public void noparam() {
        Map s = restTemplate.postForObject("http://localhost:8080/form/noparam", null, Map.class);
        System.out.println(s);
        Result result = restTemplate.postForObject("http://localhost:8080/form/noparam", null, Result.class);
        System.out.println(result);
    }

    @Test
    public void params() {
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap();
        valueMap.add("id", "123");
        valueMap.add("msg", "message");

        //默认是根据传输内容：application/x-www-form-urlencoded
        Map s = restTemplate.postForObject("http://localhost:8080/form/params", valueMap, Map.class);
        System.out.println(s);

        //默认是根据传输内容：application/x-www-form-urlencoded
        Result result = restTemplate.postForObject("http://localhost:8080/form/params", valueMap, Result.class);
        System.out.println(result);
    }

    @Test
    public void paramsobject() {
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap();
        valueMap.add("name", "nnaa");
        valueMap.add("age", "12");

        //1、默认是根据传输内容：application/x-www-form-urlencoded
        Map s = restTemplate.postForObject("http://localhost:8080/form/paramsobject", valueMap, Map.class);
        System.out.println(s);

        //2、默认是根据传输内容：application/x-www-form-urlencoded
        Result result = restTemplate.postForObject("http://localhost:8080/form/paramsobject", valueMap, Result.class);
        System.out.println(result);


        Person person = new Person();
        person.setName("test");
        person.setAge(23);
        //3、默认是根据传输内容：这里是类 则 Content-Type:application/json
        //访问可以，但是接收不到参数 主要是因为对象默认转化为  rest application/json的
        Result result2 = restTemplate.postForObject("http://localhost:8080/form/paramsobject", person, Result.class);
        System.out.println(result2);


        //4、默认是根据传输内容这里是类 还想以表单方式传输
        //设置请求头尾 表单，强行传输会报错
        //No HttpMessageConverter for [com.github.bjlhx15.common.http.webserver.controller.Person] and content type [application/x-www-form-urlencoded]
        /*
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity entity = new HttpEntity(person, headers);
        Result result3 = restTemplate.postForObject("http://localhost:8080/form/paramsobject", entity, Result.class);
        System.out.println(result3);
         */
    }

    @Test
    public void paramsobject1() {

        Person person = new Person();
        person.setName("test");
        person.setAge(23);
        //1、服务端使用 RequestBody接收，则意思是接收：Content-Type:application/json
        //ok
        Result result2 = restTemplate.postForObject("http://localhost:8080/form/paramsobject1", person, Result.class);
        System.out.println(result2);


        //2、服务端使用 RequestBody接收，则意思是接收：Content-Type:application/json
        //如果客户端将类强行转成：Content-Type：application/x-www-form-urlencoded
        //报错： No HttpMessageConverter for [com.github.bjlhx15.common.http.webserver.controller.Person]
        // and content type [application/x-www-form-urlencoded]
        /*
        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-Type","application/x-www-form-urlencoded");
        HttpEntity entity=new HttpEntity(person,headers);
        Result result23 = restTemplate.postForObject("http://localhost:8080/form/paramsobject1", entity, Result.class);
        System.out.println(result23);
        */


        //3、服务端使用 RequestBody接收，则意思是接收：Content-Type:application/json
        //客户端 使用 Content-Type：application/x-www-form-urlencoded 传输
        //报错： UnsupportedMediaType: 415 null
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap();
        valueMap.add("name", "nnaa");
        valueMap.add("age", "12");

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity entity = new HttpEntity(valueMap, headers2);
        Result result23 = restTemplate.postForObject("http://localhost:8080/form/paramsobject1", entity, Result.class);
        System.out.println(result23);
    }
}