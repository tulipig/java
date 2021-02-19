package com.config;

import com.bean.Car;
import com.bean.Pet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
    @Value("xxoo")
    String petName;
    @Value("19")
    Integer petAge;

    @Bean
    public Pet pet(){
//        return new Pet("lucy",2);
        return new Pet(petName, petAge);
    }
}
