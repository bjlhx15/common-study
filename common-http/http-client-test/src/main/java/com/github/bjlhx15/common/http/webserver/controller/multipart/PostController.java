package com.github.bjlhx15.common.http.webserver.controller.multipart;

import com.github.bjlhx15.common.http.webserver.controller.entity.Person;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController("multipart")
@RequestMapping(value = "/multipart", method = RequestMethod.POST)
public class PostController {

    @RequestMapping("/noparam")
    @ResponseBody
    public Object noparam() {
        Map<String, String> map = new HashMap<>();
        map.put("code", "2000");
        return map;
    }

    @RequestMapping("/params")
    @ResponseBody
    public Object params(String id, String msg) {
        Map<String, String> map = new HashMap<>();
        map.put("code", "2000");
        map.put("id", id);
        map.put("message", msg);
        return map;
    }

    @RequestMapping("/params1")
    @ResponseBody
    public Object params1(@RequestParam("id")String id,@RequestParam("msg") String message) {
        Map<String, String> map = new HashMap<>();
        map.put("code", "2000");
        map.put("id", id);
        map.put("message", message);
        return map;
    }

    @RequestMapping("/paramsobject")
    @ResponseBody
    public Object paramsobject(Person person) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "2000");
        map.put("person", person);
        return map;
    }

    /**
     * 客户端 访问失败
     * curl -X POST \
     *   http://localhost:8080/form/paramsobject1 \
     *   -H 'content-type: application/x-www-form-urlencoded' \
     *   -d 'name=%E5%BC%A0%E4%B8%89&age=14'
     * @param person
     * @return
     */
    @RequestMapping("/paramsobject1")
    @ResponseBody
    public Object paramsobject1(@RequestBody Person person) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "2000");
        map.put("person", person);
        return map;
    }
}
