package com.atguigu.hello.service;

import com.atguigu.hello.bean.HelloProperties;
import org.springframework.beans.factory.annotation.Autowired;

public class HelloService {
    @Autowired
    HelloProperties helloProperties;

    public String sayHello(String userName){
        return helloProperties.getPrefix() + ": " + userName + ">>>" +
                helloProperties.getSuffix();
    }
}
