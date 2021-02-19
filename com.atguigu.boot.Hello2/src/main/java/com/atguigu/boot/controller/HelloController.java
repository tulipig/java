package com.atguigu.boot.controller;

import com.atguigu.boot.bean.User;
import com.atguigu.boot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.UUID;

@RestController
@Slf4j
public class HelloController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserService userService;

    @RequestMapping("/hello")
    String Handle01(){
        log.info("request comming");
        return "Hello Spring Boot";
    }

    @RequestMapping("/create")
    String HandleCreate(@RequestParam String name,
                        @RequestParam Integer age){
        log.info("create, name=" + name + ", age=" + age);
        Random df = new Random();
        String sql = "INSERT INTO t_user set name='" + name +"', age=" + age + ", id=" + df.nextInt(100000);
        jdbcTemplate.execute(sql);
        return "cerate success!";
    }

    @RequestMapping("/queryage")
    Integer HanldeQueryAge(@RequestParam String name){
        log.info("HanldeQueryAge, name=" + name);
        String sql = "SELECT age from t_user where name='" + name + "'";
        Integer age = jdbcTemplate.queryForObject(sql, Integer.class);
        return age;
    }

    @RequestMapping("/queryUserById")
    User HanldeQueryUserById(@RequestParam Long id){
        log.info("queryUserById, id=" + id);
        return userService.getUserInfoById(id);
    }
}
