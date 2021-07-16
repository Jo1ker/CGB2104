package com.jt.service;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Override
    public String getMsg() {
        return "你好";
    }
}
