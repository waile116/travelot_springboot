package com.web.travelot.controller;

import com.web.travelot.po.CommonResult;
import com.web.travelot.po.User;
import com.web.travelot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/UserController")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/saveUser/{userId}/{password}/{username}/{userSex}")
    public CommonResult<Integer> saveUser(@PathVariable("userId") Integer userId,
                                          @PathVariable("password") String password,
                                          @PathVariable("username") String username,
                                          @PathVariable("userSex") Integer userSex) throws Exception {
        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);
        user.setPassword(password);
        user.setUserSex(userSex);
        int result = userService.saveUser(user);
        return new CommonResult<>(200, "Register success", result);
    }

    @PostMapping("/updateUserImgById/{userId}")
    public CommonResult<Integer> updateUserImgById(@PathVariable("userId") Integer userId,
                                          @RequestBody Map<String, String> requestBody) throws Exception {

        String userImg = requestBody.get("userImg");
        User user = new User();
        user.setUserId(userId);
        user.setUserImg(userImg);
        int result = userService.updateUserImgById(user);
        return new CommonResult<>(200, "UserImg update success", result);
    }

    @GetMapping("/getUserByIdPass/{userId}/{password}")
    public CommonResult<User> getUserByIdPass(@PathVariable("userId") Integer userId,
                                              @PathVariable("password") String password) throws Exception {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        User result = userService.getUserByIdPass(user);
        return new CommonResult<>(200, "success", result);
    }

    @GetMapping("/getUserById/{userId}")
    public CommonResult<User> getUserById(@PathVariable("userId") Integer userId) throws Exception {
        User result = userService.getUserById(userId);
        return new CommonResult<>(200, "success", result);
    }
}
