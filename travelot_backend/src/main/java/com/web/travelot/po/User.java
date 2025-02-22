package com.web.travelot.po;

public class User {
    private String id;
    private String password;
    private String username;
    private int sex;
    private String user_img;

    public String getUserId() {
        return id;
    }

    public void setUserId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserSex() {
        return sex;
    }

    public void setUserSex(int sex) {
        this.sex = sex;
    }

    public String getUserImg() {
        return user_img;
    }

    public void setUserImg(String user_img) {
        this.user_img = user_img;
    }
}
