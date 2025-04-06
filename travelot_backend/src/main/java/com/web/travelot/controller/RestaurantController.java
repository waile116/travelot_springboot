package com.web.travelot.controller;

import com.web.travelot.po.CommonResult;
import com.web.travelot.po.Food;
import com.web.travelot.po.Restaurant;
import com.web.travelot.po.Ticket;
import com.web.travelot.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/RestaurantController")
public class RestaurantController {
    @Autowired
    public RestaurantService restaurantService;

    @GetMapping("/listRestaurantRandom")
    public CommonResult<List<Restaurant>> listRestaurantRandom() throws Exception{
        List<Restaurant> result = restaurantService.listRestaurantRandom();
        return new CommonResult<>(200, "success", result);
    };
    @GetMapping("/listRestaurantById/{stateId}")
    public CommonResult<List<Restaurant>> listRestaurantById(@PathVariable("stateId") Integer stateId) throws Exception{
        List<Restaurant> result = restaurantService.listRestaurantById(stateId);
        return new CommonResult<>(200, "success", result);
    };

    @GetMapping("/getRestaurantById/{restaurantId}")
    public CommonResult<Restaurant> getRestaurantById(@PathVariable("restaurantId") Integer restaurantId) throws Exception{
        Restaurant result = restaurantService.getRestaurantById(restaurantId);
        return new CommonResult<>(200, "success", result);
    };

    @PostMapping("/saveRestaurant/{id}/{stateId}/{name}/{desc}/{location}/{price}/{openTime}/{rating}")
    public CommonResult<Integer> saveRestaurant(@PathVariable("id") Integer restaurantId,
                                                @PathVariable("stateId") Integer stateId,
                                                @PathVariable("name") String name,
                                                @PathVariable("desc") String desc,
                                                @PathVariable("location") String location,
                                                @PathVariable("price") String price,
                                                @PathVariable("openTime") String openTime,
                                                @PathVariable("rating") Double rating,
                                                @RequestBody Map<String, String> requestBody) throws Exception {
        String img = requestBody.get("img");
        String mapImg = requestBody.get("mapImg");
        String mapLink = requestBody.get("mapLink");
        Restaurant restaurant = new Restaurant();
        if(restaurantId > -1) {
            restaurant.setRestaurantId(restaurantId);
        }
        restaurant.setStateId(stateId);
        restaurant.setName(name);
        restaurant.setDesc(desc);
        restaurant.setLocation(location);
        restaurant.setPrice(price);
        restaurant.setOpenTime(openTime);
        restaurant.setRating(rating);
        restaurant.setMapLink(mapLink);
        restaurant.setRestaurantImg(img);
        restaurant.setMapImg(mapImg);
        int result = restaurantService.saveRestaurant(restaurant);
        return new CommonResult<>(200, "Insert restaurant success", result);
    }

    @GetMapping("/listFoodById/{restaurantId}")
    public CommonResult<List<Food>> listFoodById(@PathVariable("restaurantId") Integer restaurantId) throws Exception{
        List<Food> result = restaurantService.listFoodById(restaurantId);
        return new CommonResult<>(200, "success", result);
    };

    @PostMapping("/saveFood/{id}/{restaurantId}/{name}/{price}")
    public CommonResult<Integer> saveFood(@PathVariable("id") Integer foodId,
                                          @PathVariable("restaurantId") Integer restaurantId,
                                          @PathVariable("name") String name,
                                          @PathVariable("price") Integer price,
                                          @RequestBody Map<String, String> requestBody) throws Exception {
        String img = requestBody.get("img");
        Food food = new Food();
        if(foodId > -1) {
            food.setFoodId(foodId);
        }
        food.setRestaurantId(restaurantId);
        food.setName(name);
        food.setPrice(price);
        food.setFoodImg(img);
        int result = restaurantService.saveFood(food);
        return new CommonResult<>(200, "Insert food success", result);
    }
}
