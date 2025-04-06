package com.web.travelot.service;
import com.web.travelot.po.Hotel;
import com.web.travelot.po.Room;
import com.web.travelot.po.Ticket;

import java.util.List;

public interface HotelService {
    public List<Hotel> listHotelRandom();
    public List<Hotel> listHotelById(Integer stateId);
    public Hotel getHotelById(Integer hotelId);
    public int saveHotel(Hotel hotel);
    public List<Room> listRoom();
    public List<Room> listRoomById(Integer hotelId);
    public int saveRoom(Room room);
}