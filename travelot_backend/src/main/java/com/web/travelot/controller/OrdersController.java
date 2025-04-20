package com.web.travelot.controller;

import com.web.travelot.po.CommonResult;
import com.web.travelot.po.OrderDetail;
import com.web.travelot.po.Orders;
import com.web.travelot.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/OrdersController")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @PostMapping("/saveOrders/{userId}/{category}/{targetId}/{targetSubId}/{price}/{quantity}/{startT}/{endT}")
    public CommonResult<Integer> saveOrders(@PathVariable("userId") Integer userId,
                                            @PathVariable("category") Integer category,
                                            @PathVariable("targetId") Integer targetId,
                                            @PathVariable("targetSubId") Integer targetSubId,
                                            @PathVariable("price") Integer price,
                                            @PathVariable("quantity") Integer quantity,
                                            @PathVariable("startT") String startT,
                                            @PathVariable("endT") String endT) throws Exception {

        LocalDate start = LocalDate.parse(startT); // convert to Date
        LocalDateTime startTime = start.atStartOfDay(); // convert to DateTime

        Orders orders = new Orders();
        OrderDetail od = new OrderDetail();
        orders.setUserId(userId);
        orders.setTotal(quantity * price);
        od.setCategory(category);
        od.setTargetId(targetId);
        od.setTargetSubId(targetSubId);
        od.setQuantity(quantity);
        od.setStartT(startTime);

        if(!endT.equals("null")) {
            LocalDate end = LocalDate.parse(endT);
            LocalDateTime endTime = end.atStartOfDay();
            od.setEndT(endTime);
        }
        int orderId = ordersService.saveOrders(orders, od);
        return new CommonResult<>(200, "Save order success", orderId);
    }

    @PostMapping("/updateOrdersById/{orderId}/{status}")
    public CommonResult<Integer> updateOrdersById(@PathVariable("orderId") Integer orderId,
                                                  @PathVariable("status") Integer status) throws Exception {

        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setStatus(status);

        OrderDetail od = ordersService.getOrderDetailById(orderId);
        int result = ordersService.updateOrdersById(orders, od);
        return new CommonResult<>(200, "Update order success", result);
    }

    @GetMapping("/listOrdersByUserId/{userId}")
    public CommonResult<List<Orders>> listOrdersByUserId(@PathVariable("userId") Integer userId) throws Exception {
        List<Orders> result = ordersService.listOrdersByUserId(userId);
        return new CommonResult<>(200, "success", result);
    }

    @GetMapping("/getOrderDetailById/{orderId}")
    public CommonResult<OrderDetail> getOrderDetailById(@PathVariable("orderId") Integer orderId) throws Exception {
        OrderDetail result = ordersService.getOrderDetailById(orderId);
        return new CommonResult<>(200, "success", result);
    }

    @GetMapping("/getOrdersById/{orderId}")
    public CommonResult<Orders> getOrdersById(@PathVariable("orderId") Integer orderId) throws Exception {
        Orders result = ordersService.getOrdersById(orderId);
        return new CommonResult<>(200, "success", result);
    }
}
