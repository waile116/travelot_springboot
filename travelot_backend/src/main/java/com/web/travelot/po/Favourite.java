package com.web.travelot.po;

import java.time.LocalDateTime;

public class Favourite {
    private Integer id;
    private Integer user_id;
    private Integer category;
    private Integer target_id;
    private LocalDateTime create_t;

    public Integer getFavouriteId() {
        return id;
    }

    public void setFavouriteId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
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

    public LocalDateTime getCreateT() {
        return create_t;
    }

    public void setCreateT(LocalDateTime create_t) {
        this.create_t = create_t;
    }
}
