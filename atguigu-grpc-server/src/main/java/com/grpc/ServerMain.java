package com.grpc;

import com.grpc.server.HelloServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ServerMain {
    public static void main(String[] args) {
        SpringApplication.run(ServerMain.class, args);
        HelloServer helloServer = new HelloServer();
        try {
            helloServer.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
