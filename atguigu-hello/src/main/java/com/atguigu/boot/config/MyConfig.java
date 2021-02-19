package com.atguigu.boot.config;

import com.atguigu.hello.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class MyConfig {

    //如果自定义了bean, 会使用自定义bean,
    //因为Hello-starter里面使用了@ConditionalOnMissingBean(HelloService.class)
    @Bean
    public HelloService helloService(){
        System.out.println("use self-define bean");
        return new HelloService();
    }
}
