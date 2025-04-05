package com.web.travelot.po;

public class Hotel {
    private Integer id;
    private Integer state_id;
    private String name;
    private String description;
    private String location;
    private String price;
    private String open_t;
    private Double rating;
    private String hotel_img;
    private String map_img;
    private String map_link;

    public Integer getHotelId() {
        return id;
    }

    public void setHotelId(Integer id) {
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

    public String getHotelImg() {
        return hotel_img;
    }

    public void setHotelImg(String hotel_img) {
        this.hotel_img = hotel_img;
    }

    public String getMapImg() {
        return map_img;
    }

    public void setMapImg(String map_img) {
        this.map_img = map_img;
    }

    public String getMapLink() {
        return map_link;
    }

    public void setMapLink(String map_link) {
        this.map_link = map_link;
    }
}
