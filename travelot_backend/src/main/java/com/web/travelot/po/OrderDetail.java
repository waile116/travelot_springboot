package com.web.travelot.po;

import java.time.LocalDateTime;

public class OrderDetail {
    private Integer id;
    private Integer order_id;
    private Integer category;
    private Integer target_id;
    private Integer target_sub_id;
    private Integer quantity;
    private LocalDateTime start_t;
    private LocalDateTime end_t;
    private LocalDateTime create_t;

    public Integer getOdId() {
        return id;
    }

    public void setOdId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return order_id;
    }

    public void setOrderId(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getTargetId() {
        return target_id;
    }

    public void setTargetId(Integer target_id) {
        this.target_id = target_id;
    }

    public Integer getTargetSubId() {
        return target_sub_id;
    }

    public void setTargetSubId(Integer target_sub_id) {
        this.target_sub_id = target_sub_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getStartT() {
        return start_t;
    }

    public void setStartT(LocalDateTime start_t) {
        this.start_t = start_t;
    }

    public LocalDateTime getEndT() {
        return end_t;
    }

    public void setEndT(LocalDateTime end_t) {
        this.end_t = end_t;
    }

    public LocalDateTime getCreateT() {
        return create_t;
    }

    public void setCreateT(LocalDateTime create_t) {
        this.create_t = create_t;
    }
}
