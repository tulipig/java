package com;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/*
策略定义
 */
interface Strategy {
    void algorithmInterface();
}

class ConcreteStrategyA implements Strategy {
    @Override
    public void  algorithmInterface() {
        //具体的算法...
        System.out.println("call ConcreteStrategyA::algorithmInterface...");
    }
}

class ConcreteStrategyB implements Strategy {
    @Override
    public void  algorithmInterface() {
        //具体的算法...
        System.out.println("call ConcreteStrategyB::algorithmInterface...");
    }
}

/*
策略的创建
 */
class StrategyFactory {
    private static final Map<String, Strategy> strategies = new HashMap<>();

    static {
        strategies.put("A", new ConcreteStrategyA());
        strategies.put("B", new ConcreteStrategyB());
    }

    public static Strategy getStrategy(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("type should not be empty.");
        }
        return strategies.get(type);
    }
}

/*
策略的使用
 */
public class TestStrategy {
    @Test
    void testStrategy(){
        StrategyFactory.getStrategy("B").algorithmInterface();
    }
}


//example 如何利用策略模式避免分支判断？
/*

public class OrderService {
  public double discount(Order order) {
    double discount = 0.0;
    OrderType type = order.getType();
    if (type.equals(OrderType.NORMAL)) { // 普通订单
      //...省略折扣计算算法代码
    } else if (type.equals(OrderType.GROUPON)) { // 团购订单
      //...省略折扣计算算法代码
    } else if (type.equals(OrderType.PROMOTION)) { // 促销订单
      //...省略折扣计算算法代码
    }
    return discount;
  }
}
 */

enum OrderType{
    NORMAL,GROUPON,PROMOTION
}

@Data
class Order{
    public Order(OrderType type){
        this.type = type;
    }

    OrderType type;
    double discount;
}

// 策略的定义
interface DiscountStrategy {
    double calDiscount(Order order);
}

class NormalDiscountStrategy implements DiscountStrategy{

    @Override
    public double calDiscount(Order order) {
        System.out.println("call NormalDiscountStrategy::calDiscount...");
        return 10;
    }
}

class GrouponDiscountStrategy implements DiscountStrategy{

    @Override
    public double calDiscount(Order order) {
        System.out.println("call GrouponDiscountStrategy::calDiscount...");
        return 20;
    }
}

class PromotionDiscountStrategy implements DiscountStrategy{

    @Override
    public double calDiscount(Order order) {
        System.out.println("call PromotionDiscountStrategy::calDiscount...");
        return 30;
    }
}

// 策略的创建
class DiscountStrategyFactory {
    private static final Map<OrderType, DiscountStrategy> strategies = new HashMap<>();

    static {
        strategies.put(OrderType.NORMAL, new NormalDiscountStrategy());
        strategies.put(OrderType.GROUPON, new GrouponDiscountStrategy());
        strategies.put(OrderType.PROMOTION, new PromotionDiscountStrategy());
    }

    public static DiscountStrategy getDiscountStrategy(OrderType type) {
        return strategies.get(type);
    }
}

// 策略的使用
class OrderService {
    public double discount(Order order) {
        OrderType type = order.getType();
        DiscountStrategy discountStrategy = DiscountStrategyFactory.getDiscountStrategy(type);
        return discountStrategy.calDiscount(order);
    }
}

class TestOrderService{
   @Test
    void testOrderService(){
        OrderService orderService = new OrderService();
        double discount = orderService.discount(new Order(OrderType.PROMOTION));
        System.out.println("discount: " + discount);
    }
}
