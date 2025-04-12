package com.web.travelot.service;

import com.web.travelot.po.Favourite;

import java.util.List;

public interface FavouriteService {
    public int saveFavourite(Favourite favourite);
    public int removeFavourite(Favourite favourite);
    public Favourite getFavouriteById(Favourite favourite);
    public List<Favourite> listFavouriteByUserId(Integer userId);
}
