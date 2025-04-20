package com.web.travelot.po;

import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String alias;
    private int sex;
    private String user_img;
    private String password;
    private String salt;
    private Boolean isAdmin;

    public Integer getUserId() {
        return id;
    }

    public void setUserId(Integer id) {
        this.id = id;
    }
    public String getUserName() {
        return username;
    }
    public void setUserName(String username) {
        this.username = username;
    }
    public String getUserAlias() {
        return alias;
    }
    public void setUserAlias(String alias) {
        this.alias = alias;
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
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public Boolean getIsAdmin() {
       return isAdmin;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
}
