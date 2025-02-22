package com.web.travelot.controller;

import com.web.travelot.po.CommonResult;
import com.web.travelot.po.User;
import com.web.travelot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/UserController")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/saveUser/{userId}/{password}/{username}/{userSex}")
    public CommonResult<Integer> saveUser(@PathVariable("userId") String userId,
                                          @PathVariable("password") String password,
                                          @PathVariable("username") String username,
                                          @PathVariable("userSex") Integer userSex) throws Exception {
        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);
        user.setPassword(password);
        user.setUserSex(userSex);
        int result = userService.saveUser(user);
        return new CommonResult<>(200, "success", result);
    }

    @GetMapping("/getUserByIdPass/{userId}/{password}")
    public CommonResult<User> getUserByIdPass(@PathVariable("userId") String userId,
                                              @PathVariable("password") String password) throws Exception {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        User result = userService.getUserByIdPass(user);
        return new CommonResult<>(200, "success", result);
    }

    @GetMapping("/getUserCountById/{userId}")
    public CommonResult<Integer> getUserCountById(@PathVariable("userId") String userId) throws Exception {
        int result = userService.getUserCountById(userId);
        return new CommonResult<>(200, "success", result);
    }
}
