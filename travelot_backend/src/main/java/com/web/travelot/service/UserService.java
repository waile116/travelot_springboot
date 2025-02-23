package com.web.travelot.service;

import com.web.travelot.po.User;

public interface UserService {
    public int saveUser(User user);
    public User getUserByIdPass(User user);
    public int getUserCountById(Integer userId);
}
