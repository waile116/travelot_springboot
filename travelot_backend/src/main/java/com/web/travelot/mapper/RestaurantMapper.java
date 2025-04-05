package com.web.travelot.mapper;

import com.web.travelot.po.Restaurant;
import com.web.travelot.po.Food;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RestaurantMapper {
    @Select("select * from restaurant order by rand()")
    public List<Restaurant> listRestaurantRandom();
    @Select("select * from restaurant where state_id=#{stateId} order by id")
    public List<Restaurant> listRestaurantById(Integer stateId);
    @Select("select * from restaurant where id=#{restaurantId}")
    public Restaurant getRestaurantById(Integer restaurantId);
    @Insert("insert into restaurant (state_id, name, description, location, price, open_t, rating, restaurant_img, create_t, update_t) values " +
            "(#{stateId}, #{name}, #{desc}, #{location}, #{price}, #{openTime}, #{rating}, #{restaurantImg}, NOW(), NOW())")
    public int saveRestaurant(Restaurant restaurant);
    @Update("UPDATE restaurant SET state_id=#{stateId}, name=#{name}, description=#{desc}, location=#{location}, " +
            "price=#{price}, open_t=#{openTime}, rating=#{rating}, restaurant_img=#{restaurantImg}, map_img=#{mapImg}, map_link=#{mapLink}, " +
            "update_t=NOW() WHERE id=#{restaurantId}")
    public int updateRestaurant(Restaurant restaurant);

    @Select("select * from food where restaurant_id=#{restaurantId}")
    public List<Food> listFoodById(Integer restaurantId);
}
