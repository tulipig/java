package com;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;


public class TestGoogleGuava {
    //https://zhuanlan.zhihu.com/p/20637960
    @Test
    void testSet(){
        Set<String> wordsWithPrimeLength = ImmutableSet.of("one", "two", "three", "six", "seven", "eight");
        Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");

        Sets.SetView<String> intersection = Sets.intersection(primes, wordsWithPrimeLength); // contains "two", "three", "seven"
// I can use intersection as a Set directly, but copying it can be more efficient if I use it a lot.
        System.out.println(intersection.immutableCopy().toString());
    }

    @Test
    void TestNull(){
        Integer a = null;
        Optional<Integer> age = Optional.of(a);
        //System.out.println(age.get());  //throw NullPointerException

    }

}
