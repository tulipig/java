package com;

import com.bean.Car;
import com.bean.Computer;
import com.bean.Pet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@SpringBootTest
public class TestMain {
    @Autowired
    Pet pet;

    @Autowired
    Car car;

    @Autowired
    Computer computer;

    @Test
    void testBean(){
        log.info(pet.toString());
        log.info(car.toString());
        log.info(computer.toString());
    }
}
