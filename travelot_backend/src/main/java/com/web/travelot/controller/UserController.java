package com.web.travelot.controller;

import com.web.travelot.po.CommonResult;
import com.web.travelot.po.User;
import com.web.travelot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/UserController")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/saveUser/{userId}/{password}/{userName}/{userAlias}/{userSex}")
    public CommonResult<User> saveUser(@PathVariable("userId") Integer userId,
                                          @PathVariable("password") String password,
                                          @PathVariable("userName") String userName,
                                          @PathVariable("userAlias") String userAlias,
                                          @PathVariable("userSex") Integer userSex) throws Exception {
        User user = new User();
        user.setUserId(userId);
        if(!"-1".equals(password)) {
            user.setPassword(password);
        }
        user.setUserName(userName);
        user.setUserAlias(userAlias);
        user.setUserSex(userSex);
        User result = userService.saveUser(user);
        return new CommonResult<>(200, "Save user success", result);
    }

    @PostMapping("/updateUserImgById/{userId}")
    public CommonResult<Integer> updateUserImgById(@PathVariable("userId") Integer userId,
                                          @RequestBody Map<String, String> requestBody) throws Exception {

        String userImg = requestBody.get("userImg");
        User user = new User();
        user.setUserId(userId);
        user.setUserImg(userImg);
        int result = userService.updateUserImgById(user);
        return new CommonResult<>(200, "Update userImg success", result);
    }

    @PostMapping("/updateUserPassword/{userId}/{password}")
    public CommonResult<Integer> updateUserPassword(@PathVariable("userId") Integer userId,
                                                    @PathVariable("password") String password) throws Exception {

        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        int result = userService.updateUserPassword(user);
        return new CommonResult<>(200, "Update password success", result);
    }

    @GetMapping("/getUserByIdPass/{userId}/{password}")
    public CommonResult<User> getUserByIdPass(@PathVariable("userId") Integer userId,
                                              @PathVariable("password") String password) throws Exception {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        User result = userService.getUserByIdPass(user);
        return new CommonResult<>(200, "Login success", result);
    }

    @GetMapping("/getUserById/{userId}")
    public CommonResult<User> getUserById(@PathVariable("userId") Integer userId) throws Exception {
        User result = userService.getUserById(userId);
        return new CommonResult<>(200, "success", result);
    }
}
