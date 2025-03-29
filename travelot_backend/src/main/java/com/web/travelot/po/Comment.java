package com.web.travelot.po;

import java.time.LocalDateTime;

public class Comment {
    private Integer id;
    private Integer user_id;
    private Integer category;
    private Integer target_id;
    private Double rating;
    private String text;
    private String comment_img;
    private LocalDateTime create_t;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCommentImg() {
        return comment_img;
    }

    public void setCommentImg(String comment_img) {
        this.comment_img = comment_img;
    }

    public LocalDateTime getCreateT() {
        return create_t;
    }

    public void setCreateT(LocalDateTime create_t) {
        this.create_t = create_t;
    }
}
