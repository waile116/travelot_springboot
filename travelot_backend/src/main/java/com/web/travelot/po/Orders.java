package com.web.travelot.po;

import java.time.LocalDateTime;

public class Orders {
    private Integer id;
    private Integer user_id;
    private Integer total;
    private Integer status;
    private LocalDateTime create_t;
    private LocalDateTime paid_t;

    public Integer getOrderId() {
        return id;
    }

    public void setOrderId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateT() {
        return create_t;
    }

    public void setCreateT(LocalDateTime create_t) {
        this.create_t = create_t;
    }

    public LocalDateTime getPaidT() {
        return paid_t;
    }

    public void setPaidT(LocalDateTime paid_t) {
        this.paid_t = paid_t;
    }
}
