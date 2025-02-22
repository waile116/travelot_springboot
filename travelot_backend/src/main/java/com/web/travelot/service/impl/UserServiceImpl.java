package com.web.travelot.service.impl;

import com.web.travelot.mapper.UserMapper;
import com.web.travelot.po.User;
import com.web.travelot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int saveUser(User user) {
        return userMapper.saveUser(user);
    }

    @Override
    public User getUserByIdPass(User user) {
        return userMapper.getUserByIdPass(user);
    }

    @Override
    public int getUserCountById(String userId) {
        return userMapper.getUserCountById(userId);
    }
}