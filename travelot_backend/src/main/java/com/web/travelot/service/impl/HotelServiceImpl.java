package com.web.travelot.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.web.travelot.po.Hotel;
import com.web.travelot.service.HotelService;
import com.web.travelot.mapper.HotelMapper;

@Service
public class HotelServiceImpl implements HotelService{
    @Autowired
    private HotelMapper hotelMapper;

    @Override
    public List<Hotel> listHotelRandom(){
        return hotelMapper.listHotelRandom();
    };
    @Override
    public List<Hotel> listHotelById(Integer stateId){
        return hotelMapper.listHotelById(stateId);
    };
    @Override
    public Hotel getHotelById(Integer hotelId){
        return hotelMapper.getHotelById(hotelId);
    };
    @Override
    public int saveHotel(Hotel hotel){
        // if id exists, update, else save
        if (hotel.getHotelId() != null) {
            return hotelMapper.updateHotel(hotel);
        } else {
            return hotelMapper.saveHotel(hotel);
        }
    };

}
