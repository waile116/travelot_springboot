package com.web.travelot.po;

public class Restaurant {
    private Integer id;
    private Integer state_id;
    private String name;
    private String description;
    private String location;
    private String price;
    private String open_t;
    private Double rating;
    private String restaurant_img;

    public Integer getRestaurantId() {
        return id;
    }

    public void setRestaurantId(Integer id) {
        this.id = id;
    }

    public Integer getStateId() {
        return state_id;
    }

    public void setStateId(Integer state_id) {
        this.state_id = state_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOpenTime() {
        return open_t;
    }

    public void setOpenTime(String open_t) {
        this.open_t = open_t;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getRestaurantImg() {
        return restaurant_img;
    }

    public void setRestaurantImg(String restaurant_img) {
        this.restaurant_img = restaurant_img;
    }
}
