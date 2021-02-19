package com.grpc.config;

import com.grpc.client.HelloClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class MyConfig {
    @Value("${grpc.host}")
    String host;
    @Value("${grpc.port}")
    Integer port;

    @Bean
    public HelloClient helloClient(){
        return new HelloClient(host, port);
    }
}
