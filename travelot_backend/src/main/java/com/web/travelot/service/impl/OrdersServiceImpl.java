package com.web.travelot.service.impl;

import com.web.travelot.mapper.AttractionMapper;
import com.web.travelot.mapper.OrdersMapper;
import com.web.travelot.po.OrderDetail;
import com.web.travelot.po.Orders;
import com.web.travelot.po.Ticket;
import com.web.travelot.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private AttractionMapper attractionMapper;

    @Override
    public int saveOrders(Orders orders, OrderDetail od){
        // save order and get orderId
        ordersMapper.saveOrders(orders);
        int orderId = orders.getOrderId();

        // (attraction) add ticket to ordered ticket
        if(od.getCategory() == 1){
            Ticket ticket = attractionMapper.getTicketById(od.getTargetSubId());
            ticket.setQuantity(ticket.getQuantity() + od.getQuantity());
            attractionMapper.updateTicket(ticket);
        }

        // save order detail
        od.setOrderId(orderId);
        ordersMapper.saveOrderDetail(od);
        return orderId;
    };

    @Override
    public int updateOrdersById(Orders orders){
        if(orders.getStatus() > 0) {
            return ordersMapper.payOrders(orders);
        }
        else {
            return ordersMapper.deleteOrders(orders);
        }
    }
    @Override
    public List<Orders> listOrdersByUserId(Integer userId){
        return ordersMapper.listOrdersByUserId(userId);
    }
    @Override
    public OrderDetail getOrderDetailById(Integer orderId){
        return ordersMapper.getOrderDetailById(orderId);
    }
    @Override
    public Orders getOrdersById(Integer orderId){
        return ordersMapper.getOrdersById(orderId);
    }
}
