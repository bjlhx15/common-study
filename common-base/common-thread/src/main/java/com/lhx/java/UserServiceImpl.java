package com.lhx.java;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public String get() {
        return "我是测试";
    }
}
