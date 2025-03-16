package com.web.travelot.service;

import com.web.travelot.po.Restaurant;

import java.util.List;

public interface RestaurantService {
    public List<Restaurant> listRestaurantRandom();
    public List<Restaurant> listRestaurantById(Integer stateId);
    public Restaurant getRestaurantById(Integer restaurantId);
    public int saveRestaurant(Restaurant restaurant);
}
