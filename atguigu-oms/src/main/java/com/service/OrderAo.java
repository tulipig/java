package com.service;

import com.bean.OrderBo;

public interface OrderAo {
    int createOrder(OrderBo orderBo);
    int updateOrder(int state);
    OrderBo queryOrder(String dealCode);
}
