package com.web.travelot.po;

public class Food {
    private Integer id;
    private Integer restaurant_id;
    private String name;
    private Double price;
    private String food_img;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFoodImg() {
        return food_img;
    }

    public void setFoodImg(String food_img) {
        this.food_img = food_img;
    }
}
