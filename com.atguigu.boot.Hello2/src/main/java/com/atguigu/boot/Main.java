package com.atguigu.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

import javax.sql.DataSource;

@Slf4j
@SpringBootApplication
public class Main {

    @Autowired
    DataSource dataSource;

    public static void main(String[] args) {
        log.info("SpringVersion:" +SpringVersion.getVersion() + ", SpringBootVersion:" + SpringBootVersion.getVersion());
        SpringApplication.run(Main.class, args);
    }
}
