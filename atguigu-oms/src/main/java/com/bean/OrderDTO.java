package com.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    Integer uid;
    int state;
    Integer payment;
    long timeStamp;
    String dealCode;
}
