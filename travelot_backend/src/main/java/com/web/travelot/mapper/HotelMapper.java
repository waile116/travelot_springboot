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
            "price=#{price}, rating=#{rating}, hotel_img=#{hotelImg}, update_t=NOW() " +
            "WHERE id=#{hotelId}")
    public int updateHotel(Hotel hotel);

    @Select("select * from room where hotel_id=#{hotelId} order by price")
    public List<Room> listRoomById(Integer hotelId);
}