package com.boot.controller;

import com.boot.bean.User;
import com.boot.service.UserOperatorService;
import com.boot.service.UserOperatorService2;
import com.boot.service.impl.UserOperatorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@Slf4j
@RestController
public class UserOperator {

    @Autowired
    UserOperatorService userOperatorService;

    @Autowired
    UserOperatorService2 userOperatorService2;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @RequestMapping("/getUserAgeById")
    User getUserAgeById(@RequestParam Long id){
        return userOperatorService.getUserAgeById(id);
    }

    @RequestMapping("/createUser")
    void createUser(@RequestParam String name,
                    @RequestParam Integer age){

        log.info("name:" + name + ", age:" + age);
        Random df = new Random();
        User user = new User((long) df.nextInt(100000), name, age);
        log.info(user.toString());

        //userOperatorService.createUser(user);
        userOperatorService2.saveOrUpdate(user);

    }

    @RequestMapping("testRedis")
    String testRedis(@RequestParam String name,
               @RequestParam String age){
        String bid = "mid_90000231__";
        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        operations.set(bid+name,age);

        String value = operations.get(bid+name);
        System.out.println(value);
        System.out.println(redisConnectionFactory.getClass());

        return value;

    }

}
