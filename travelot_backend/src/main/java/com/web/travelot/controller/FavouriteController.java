package com.web.travelot.controller;

import com.web.travelot.po.Favourite;
import com.web.travelot.po.CommonResult;
import com.web.travelot.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/FavouriteController")
public class FavouriteController {
    @Autowired
    private FavouriteService favouriteService;

    @PostMapping("/saveFavourite/{userId}/{category}/{targetId}")
    public CommonResult<Integer> saveFavourite(@PathVariable("userId") Integer userId,
                                                    @PathVariable("category") Integer category,
                                                    @PathVariable("targetId") Integer targetId) throws Exception {
        Favourite favourite = new Favourite();
        favourite.setUserId(userId);
        favourite.setCategory(category);
        favourite.setTargetId(targetId);
        int result = favouriteService.saveFavourite(favourite);
        return new CommonResult<>(200, "Save favourite success", result);
    }

    @PostMapping("/removeFavourite/{userId}/{category}/{targetId}")
    public CommonResult<Integer> removeFavourite(@PathVariable("userId") Integer userId,
                                                      @PathVariable("category") Integer category,
                                                      @PathVariable("targetId") Integer targetId) throws Exception {
        Favourite favourite = new Favourite();
        favourite.setUserId(userId);
        favourite.setCategory(category);
        favourite.setTargetId(targetId);
        int result = favouriteService.removeFavourite(favourite);
        return new CommonResult<>(200, "Remove favourite success", result);
    }

    @GetMapping("/getFavouriteById/{userId}/{category}/{targetId}")
    public CommonResult<Favourite> getFavouriteById(@PathVariable("userId") Integer userId,
                                                           @PathVariable("category") Integer category,
                                                           @PathVariable("targetId") Integer targetId) throws Exception {
        Favourite favourite = new Favourite();
        favourite.setUserId(userId);
        favourite.setCategory(category);
        favourite.setTargetId(targetId);
        Favourite result = favouriteService.getFavouriteById(favourite);
        return new CommonResult<>(200, "success", result);
    }

    @GetMapping("/listFavouriteByUserId/{userId}")
    public CommonResult<List<Favourite>> listFavouriteByUserId(@PathVariable("userId") Integer userId) throws Exception {
        List<Favourite> result = favouriteService.listFavouriteByUserId(userId);
        return new CommonResult<>(200, "success", result);
    }
}
