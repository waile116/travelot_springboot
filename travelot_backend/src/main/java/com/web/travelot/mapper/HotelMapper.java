package com.web.travelot.mapper;

import com.web.travelot.po.Hotel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HotelMapper {
    @Select("select * from hotel order by rand()")
    public List<Hotel> listHotelRandom();
    @Select("select * from hotel where state_id=#{stateId} order by id")
    public List<Hotel> listHotelById(Integer stateId);
}