package com.web.travelot.mapper;

import com.web.travelot.po.Orders;
import com.web.travelot.po.OrderDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrdersMapper {
    @Options(useGeneratedKeys = true, keyProperty = "orderId") //retrieve auto-generated primary key
    @Insert("insert into orders (user_id, total, status, create_t) " +
            "values (#{userId}, #{total}, 0, NOW())")
    public void saveOrders(Orders order);

    @Insert("insert into orderdetail (order_id, category, target_id, target_sub_id, quantity, start_t, end_t, create_t, update_t) " +
            "values (#{orderId}, #{category}, #{targetId}, #{targetSubId}, #{quantity}, #{startT}, #{endT}, NOW(), NOW())")
    public void saveOrderDetail(OrderDetail od);

    @Update("update orders set status = 1, paid_t = NOW() where id = #{orderId}")
    public int payOrders(Orders order);
    @Update("update orders set status = -1 where id = #{orderId}")
    public int deleteOrders(Orders order);

    @Select("select * from orders where user_id = #{userId}")
    public List<Orders> listOrdersByUserId(Integer userId);

    @Select("select * from orderdetail where order_id = #{orderId}")
    public OrderDetail getOrderDetailById(Integer orderId);

    @Select("select * from orders where id = #{orderId}")
    public Orders getOrdersById(Integer orderId);
}
