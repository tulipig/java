package com.grpc;

import com.grpc.client.HelloClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientMain {
    public static void main(String[] args) {
        SpringApplication.run(ClientMain.class, args);
    }
}
