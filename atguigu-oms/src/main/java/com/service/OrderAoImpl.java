package com.service;

import com.bean.OrderBo;
import com.bean.OrderDTO;

import java.util.Calendar;
import java.util.UUID;

public class OrderAoImpl implements OrderAo{
    public int createOrder(OrderBo orderBo){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setPayment(orderBo.getPayment());
        orderDTO.setState(1); //下单
        orderDTO.setUid(orderBo.getUid());
        orderDTO.setDealCode(UUID.randomUUID().toString());
        orderDTO.setTimeStamp(Calendar.getInstance().getTimeInMillis());

        return 0;
    }

    public int updateOrder(int state){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setState(state); //下单

        return  0;
    }

    public OrderBo queryOrder(String dealCode){
        return new OrderBo(19016476,1,10000);
    }
}
