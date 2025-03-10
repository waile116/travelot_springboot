package com.web.travelot.mapper;

import com.web.travelot.po.Restaurant;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RestaurantMapper {
    @Select("select * from restaurant where state_id=#{stateId} order by id")
    public List<Restaurant> listRestaurantById(Integer stateId);

    @Insert("insert into restaurant (state_id, name, description, location, price, open_t, rating, create_t, update_t) values " +
            "(#{stateId}, #{name}, #{desc}, #{location}, #{price}, #{openTime}, #{rating}, NOW(), NOW())")
    public int saveRestaurant(Restaurant restaurant);
}
