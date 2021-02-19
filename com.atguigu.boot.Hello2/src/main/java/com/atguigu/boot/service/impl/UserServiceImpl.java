package com.atguigu.boot.service.impl;

import com.atguigu.boot.mapper.UserMapper;
import com.atguigu.boot.bean.User;
import com.atguigu.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    public User getUserInfoById(Long id){
        return userMapper.QueryUserById(id);
    }
}
