package com.boot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

//@SpringBootTest  //如果用到spring的@Autowired等功能，需要增加此注解
public class TestMain {
//    @Autowired
//    StringRedisTemplate redisTemplate;
//
//    @Autowired
//    RedisConnectionFactory redisConnectionFactory;
//
//    @Test
//    void testRedis(){
//        String bid = "mid_90000231__";
//        String key = "Hello";
//        ValueOperations<String, String> operations = redisTemplate.opsForValue();
//
//        operations.set(bid+key,"world!");
//
//        String value = operations.get(bid+key);
//        System.out.println(value);
//        System.out.println(redisConnectionFactory.getClass());
//
//    }

    @Test
    void helloTest(){
        System.out.println("Hello Test");
    }
}
