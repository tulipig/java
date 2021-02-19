package com.boot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplicatin {
    public static void main(String[] args) {
        System.out.println("hello world...");
        SpringApplication.run(MainApplicatin.class, args);
    }

}
