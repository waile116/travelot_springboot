package com.web.travelot.service.impl;

import com.web.travelot.mapper.UserMapper;
import com.web.travelot.po.User;
import com.web.travelot.util.PasswordUtil;
import com.web.travelot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    @CachePut(value="user", key="#user.userId")
    public int saveUser(User user) {
        // generate salt and encrypt password
        String salt = PasswordUtil.generateSalt();
        String encryptedPassword = PasswordUtil.encrypt(user.getPassword(), salt);
        user.setPassword(encryptedPassword);
        user.setSalt(salt);

        // if user exists, update, else save
        if(userMapper.getUserById(user.getUserId()) != null){
            return userMapper.updateUser(user);
        }else {
            return userMapper.saveUser(user);
        }
    }

    @Override
    public int updateUserImgById(User user){
        return userMapper.updateUserImgById(user);
    };
    @Override
    public int updateUserPassword(User user){
        // generate salt and encrypt password
        String salt = PasswordUtil.generateSalt();
        String encryptedPassword = PasswordUtil.encrypt(user.getPassword(), salt);
        user.setPassword(encryptedPassword);
        user.setSalt(salt);

        return userMapper.updateUserPassword(user);
    };
    @Override
    @Cacheable(value="user", key="#user.userId")
    public User getUserByIdPass(User user) {
        User dbUser = userMapper.getUserByIdPass(user);
        if(dbUser != null) {
            // verify db's password and request's password
            boolean isValid = PasswordUtil.verify(
                    user.getPassword(),
                    dbUser.getPassword(),
                    dbUser.getSalt()
            );
            return isValid ? dbUser : null;
        }
        return null;
    }

    @Override
    @Cacheable(value="user", key="#userId")
    public User getUserById(Integer userId) {
        return userMapper.getUserById(userId);
    }
}