package com.web.travelot.po;

public class Ticket {
    private Integer id;
    private Integer attraction_id;
    private String name;
    private String description;
    private Integer price;
    private Integer amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAttractionId() {
        return attraction_id;
    }

    public void setAttractionId(Integer attraction_id) {
        this.attraction_id = attraction_id;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
