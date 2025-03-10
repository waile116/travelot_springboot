package com.web.travelot.controller;

import com.web.travelot.po.CommonResult;
import com.web.travelot.po.Restaurant;
import com.web.travelot.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/RestaurantController")
public class RestaurantController {
    @Autowired
    public RestaurantService restaurantService;

    @RequestMapping("/listRestaurantById/{stateId}")
    public List<Restaurant> listRestaurantById(@PathVariable("stateId") Integer stateId) throws Exception{
        return restaurantService.listRestaurantById(stateId);
    };

    @PostMapping("/saveRestaurant/{stateId}/{name}/{desc}/{location}/{price}/{openTime}/{rating}")
    public CommonResult<Integer> saveRestaurant(@PathVariable("stateId") Integer stateId,
                                                @PathVariable("name") String name,
                                                @PathVariable("desc") String desc,
                                                @PathVariable("location") String location,
                                                @PathVariable("price") String price,
                                                @PathVariable("openTime") String openTime,
                                                @PathVariable("rating") Double rating) throws Exception {
        Restaurant restaurant = new Restaurant();
        restaurant.setStateId(stateId);
        restaurant.setName(name);
        restaurant.setDesc(desc);
        restaurant.setLocation(location);
        restaurant.setPrice(price);
        restaurant.setOpenTime(openTime);
        restaurant.setRating(rating);
        int result = restaurantService.saveRestaurant(restaurant);
        return new CommonResult<>(200, "Insert restaurant success", result);
    }
}
