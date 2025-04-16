package com.web.travelot.mapper;

import com.web.travelot.po.Hotel;
import com.web.travelot.po.Room;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface HotelMapper {
    @Select("select * from hotel order by rand()")
    public List<Hotel> listHotelRandom();
    @Select("select * from hotel where state_id=#{stateId} order by id")
    public List<Hotel> listHotelById(Integer stateId);
    @Select("select * from hotel where id=#{hotelId}")
    public Hotel getHotelById(Integer hotelId);
    @Insert("insert into hotel (state_id, name, description, location, price, rating, hotel_img, create_t, update_t) values " +
            "(#{stateId}, #{name}, #{desc}, #{location}, #{price}, #{rating}, #{hotelImg}, NOW(), NOW())")
    public int saveHotel(Hotel hotel);
    @Update("UPDATE hotel SET state_id=#{stateId}, name=#{name}, description=#{desc}, location=#{location}, " +
            "price=#{price}, rating=#{rating}, hotel_img=#{hotelImg}, map_img=#{mapImg}, map_link=#{mapLink}, update_t=NOW() " +
            "WHERE id=#{hotelId}")
    public int updateHotel(Hotel hotel);

    @Select("select * from room")
    public List<Room> listRoom();
    @Select("select * from room where hotel_id=#{hotelId} order by price")
    public List<Room> listRoomById(Integer hotelId);
    @Select("select * from room where id=#{roomId} ")
    public Room getRoomById(Integer roomId);
    @Insert("insert into room (hotel_id, name, type, capacity, price, room_img, create_t, update_t) values " +
            "(#{hotelId}, #{name}, #{type}, #{capacity}, #{price}, #{roomImg}, NOW(), NOW())")
    public int saveRoom(Room room);
    @Update("UPDATE room SET hotel_id=#{hotelId}, name=#{name}, type=#{type}, capacity=#{capacity}, " +
            "price=#{price}, room_img=#{roomImg}, update_t=NOW() WHERE id=#{roomId}")
    public int updateRoom(Room room);
}