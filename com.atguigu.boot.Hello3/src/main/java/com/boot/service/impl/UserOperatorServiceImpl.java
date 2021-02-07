package com.boot.service.impl;

import com.boot.bean.User;
import com.boot.mapper.UserMapper;
import com.boot.service.UserOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserOperatorServiceImpl implements UserOperatorService {

    @Autowired
    UserMapper userMapper;

    public User getUserAgeById(Long id){
        return userMapper.getUserAgeById(id);
    }

    public void createUser(User user){
        userMapper.createUser(user);
    }
}