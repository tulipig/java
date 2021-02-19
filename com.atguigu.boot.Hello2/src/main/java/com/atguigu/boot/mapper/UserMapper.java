package com.atguigu.boot.mapper;

import com.atguigu.boot.bean.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {
    User QueryUserById(Long id);
}
