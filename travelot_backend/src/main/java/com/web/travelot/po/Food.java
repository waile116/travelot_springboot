package com.web.travelot.po;

public class Food {
    private Integer id;
    private Integer restaurant_id;
    private String name;
    private Integer price;
    private String food_img;

    public Integer getFoodId() {
        return id;
    }

    public void setFoodId(Integer id) {
        this.id = id;
    }

    public Integer getRestaurantId() {
        return restaurant_id;
    }

    public void setRestaurantId(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getFoodImg() {
        return food_img;
    }

    public void setFoodImg(String food_img) {
        this.food_img = food_img;
    }
}
