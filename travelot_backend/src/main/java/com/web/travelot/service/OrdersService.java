package com.web.travelot.service;

import com.web.travelot.po.OrderDetail;
import com.web.travelot.po.Orders;

import java.util.List;

public interface OrdersService {
    public int saveOrders(Orders orders, OrderDetail od);
    public int updateOrdersById(Orders orders);
    public List<Orders> listOrdersByUserId(Integer userId);
    public OrderDetail getOrderDetailById(Integer orderId);
    public Orders getOrdersById(Integer orderId);

}
