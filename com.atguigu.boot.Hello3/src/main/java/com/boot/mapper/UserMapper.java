package com.boot.mapper;

import com.boot.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public User getUserAgeById(Long id);

    @Insert("insert into  t_user(`id`, `name`,`age`) values(#{id},#{name},#{age})")
    //@Options(useGeneratedKeys = true,keyProperty = "id")
    public void createUser(User user);


}
