package com.web.travelot.service;

import com.web.travelot.po.Restaurant;

import java.util.List;

public interface RestaurantService {
    public List<Restaurant> listRestaurantById(Integer stateId);
    public int saveRestaurant(Restaurant restaurant);
}
