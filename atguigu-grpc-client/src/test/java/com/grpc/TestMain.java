package com.grpc;

import com.grpc.client.HelloClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class TestMain {
    @Autowired
    HelloClient helloClient;

    @Test
    void testHello(){
        helloClient.greet("adelaide52");
    }
}
