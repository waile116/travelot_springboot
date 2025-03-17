package com.web.travelot.controller;

import com.web.travelot.po.CommonResult;
import com.web.travelot.po.Hotel;
import com.web.travelot.po.Room;
import com.web.travelot.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/HotelController")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @RequestMapping("/listHotelRandom")
    public CommonResult<List<Hotel>> listHotelRandom() throws Exception{
        List<Hotel> result = hotelService.listHotelRandom();
        return new CommonResult<>(200, "success", result);
    };

    @RequestMapping("/listHotelById/{stateId}")
    public CommonResult<List<Hotel>> listHotelById(@PathVariable("stateId") Integer stateId) throws Exception{
        List<Hotel> result = hotelService.listHotelById(stateId);
        return new CommonResult<>(200, "success", result);
    };

    @RequestMapping("/getHotelById/{hotelId}")
    public CommonResult<Hotel> getHotelById(@PathVariable("hotelId") Integer hotelId) throws Exception{
        Hotel result = hotelService.getHotelById(hotelId);
        return new CommonResult<>(200, "success", result);
    };

    @PostMapping("/saveHotel/{id}/{stateId}/{name}/{desc}/{location}/{price}/{rating}")
    public CommonResult<Integer> saveHotel(@PathVariable("id") Integer hotelId,
                                           @PathVariable("stateId") Integer stateId,
                                           @PathVariable("name") String name,
                                           @PathVariable("desc") String desc,
                                           @PathVariable("location") String location,
                                           @PathVariable("price") String price,
                                           @PathVariable("rating") Double rating,
                                           @RequestBody Map<String, String> requestBody) throws Exception {
        String img = requestBody.get("img");
        Hotel hotel = new Hotel();
        if(hotelId > -1) {
            hotel.setHotelId(hotelId);
        }
        hotel.setStateId(stateId);
        hotel.setName(name);
        hotel.setDesc(desc);
        hotel.setLocation(location);
        hotel.setPrice(price);
        hotel.setRating(rating);
        hotel.setHotelImg(img);
        int result = hotelService.saveHotel(hotel);
        return new CommonResult<>(200, "Insert hotel success", result);
    }

    @RequestMapping("/listRoomById/{hotelId}")
    public CommonResult<List<Room>> listRoomById(@PathVariable("hotelId") Integer hotelId) throws Exception{
        List<Room> result = hotelService.listRoomById(hotelId);
        return new CommonResult<>(200, "success", result);
    };
}
