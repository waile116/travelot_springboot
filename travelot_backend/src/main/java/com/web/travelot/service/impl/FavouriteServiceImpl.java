package com.web.travelot.service.impl;

import com.web.travelot.mapper.FavouriteMapper;
import com.web.travelot.po.Favourite;
import com.web.travelot.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteServiceImpl implements FavouriteService {
    @Autowired
    private FavouriteMapper favouriteMapper;
    @Override
    public int saveFavourite(Favourite favourite){
        return favouriteMapper.saveFavourite(favourite);
    }
    @Override
    public int removeFavourite(Favourite favourite){
        return favouriteMapper.removeFavourite(favourite);
    }
    @Override
    public Favourite getFavouriteById(Favourite favourite){
        return favouriteMapper.getFavouriteById(favourite);
    }
    @Override
    public List<Favourite> listFavouriteByUserId(Integer userId){
        return favouriteMapper.listFavouriteByUserId(userId);
    }
}
