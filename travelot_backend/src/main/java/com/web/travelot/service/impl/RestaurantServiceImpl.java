package com.web.travelot.service.impl;

import com.web.travelot.mapper.RestaurantMapper;
import com.web.travelot.po.Restaurant;
import com.web.travelot.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    private RestaurantMapper restaurantMapper;

    @Override
    public List<Restaurant> listRestaurantRandom(){
        return restaurantMapper.listRestaurantRandom();
    };
    @Override
    public List<Restaurant> listRestaurantById(Integer stateId){
        return restaurantMapper.listRestaurantById(stateId);
    };

    @Override
    public Restaurant getRestaurantById(Integer restaurantId){
        return restaurantMapper.getRestaurantById(restaurantId);
    };
    @Override
    public int saveRestaurant(Restaurant restaurant){
        // if id exists, update, else save
        if (restaurant.getRestaurantId() != null) {
            return restaurantMapper.updateRestaurant(restaurant);
        } else {
            return restaurantMapper.saveRestaurant(restaurant);
        }
    };
}
