package com.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.bean.User;
import com.boot.mapper.UserMapper2;
import com.boot.service.UserOperatorService2;
import org.springframework.stereotype.Service;

@Service
public class UserOperatorService2Impl extends ServiceImpl<UserMapper2, User>
        implements UserOperatorService2 {
}
