package com.bean;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@ToString
public class Computer {
    @Value("${computer.brand}")
//    @Value("TULIP")
    String brand;

    @Value("${computer.price}")
//    @Value("50")
    Integer price;

    String note;
}
