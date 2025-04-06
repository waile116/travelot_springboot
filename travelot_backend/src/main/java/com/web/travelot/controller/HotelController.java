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

    @GetMapping("/listHotelRandom")
    public CommonResult<List<Hotel>> listHotelRandom() throws Exception{
        List<Hotel> result = hotelService.listHotelRandom();
        return new CommonResult<>(200, "success", result);
    };

    @GetMapping("/listHotelById/{stateId}")
    public CommonResult<List<Hotel>> listHotelById(@PathVariable("stateId") Integer stateId) throws Exception{
        List<Hotel> result = hotelService.listHotelById(stateId);
        return new CommonResult<>(200, "success", result);
    };

    @GetMapping("/getHotelById/{hotelId}")
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
        String mapImg = requestBody.get("mapImg");
        String mapLink = requestBody.get("mapLink");
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
        hotel.setMapLink(mapLink);
        hotel.setHotelImg(img);
        hotel.setMapImg(mapImg);
        int result = hotelService.saveHotel(hotel);
        return new CommonResult<>(200, "Insert hotel success", result);
    }

    @GetMapping("/listRoom")
    public CommonResult<List<Room>> listRoom() throws Exception{
        List<Room> result = hotelService.listRoom();
        return new CommonResult<>(200, "success", result);
    };
    @GetMapping("/listRoomById/{hotelId}")
    public CommonResult<List<Room>> listRoomById(@PathVariable("hotelId") Integer hotelId) throws Exception{
        List<Room> result = hotelService.listRoomById(hotelId);
        return new CommonResult<>(200, "success", result);
    };

    @PostMapping("/saveRoom/{id}/{hotelId}/{name}/{type}/{capacity}/{price}")
    public CommonResult<Integer> saveRoom(@PathVariable("id") Integer roomId,
                                                @PathVariable("hotelId") Integer hotelId,
                                                @PathVariable("name") String name,
                                                @PathVariable("type") String type,
                                                @PathVariable("capacity") Integer capacity,
                                                @PathVariable("price") Integer price,
                                                @RequestBody Map<String, String> requestBody) throws Exception {
        String img = requestBody.get("img");
        Room room = new Room();
        if(roomId > -1) {
            room.setRoomId(roomId);
        }
        room.setHotelId(hotelId);
        room.setName(name);
        room.setType(type);
        room.setCapacity(capacity);
        room.setPrice(price);
        room.setRoomImg(img);
        int result = hotelService.saveRoom(room);
        return new CommonResult<>(200, "Insert room success", result);
    }
}
