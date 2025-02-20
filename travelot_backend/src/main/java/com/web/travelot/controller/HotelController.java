package com.web.travelot.controller;

import com.web.travelot.po.Hotel;
import com.web.travelot.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/HotelController")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @RequestMapping("/listHotelRandom")
    public List<Hotel> listHotelRandom() throws Exception{
        return hotelService.listHotelRandom();
    };

    @RequestMapping("/listHotelById/{stateId}")
    public List<Hotel> listHotelById(@PathVariable("stateId") Integer stateId) throws Exception{
        return hotelService.listHotelById(stateId);
    };
}
