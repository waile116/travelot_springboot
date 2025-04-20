package com.web.travelot.service;

import com.web.travelot.po.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

public interface UserService {
    public int saveUser(User user);
    public int updateUserImgById(User user);
    public int updateUserPassword(User user);
    public User getUserByIdPass(User user);
    public User getUserById(Integer userId);
}
