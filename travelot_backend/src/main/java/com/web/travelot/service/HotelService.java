package com.web.travelot.service;
import com.web.travelot.po.Hotel;
import java.util.List;

public interface HotelService {
    public List<Hotel> listHotelRandom();
    public List<Hotel> listHotelById(Integer stateId);
}