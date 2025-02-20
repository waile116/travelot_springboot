package com.web.travelot.mapper;

import com.web.travelot.po.Attraction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AttractionMapper {
    @Select("select * from attraction order by rand()")
    public List<Attraction> listAttractionRandom();
    @Select("select * from attraction where state_id=#{stateId} order by id")
    public List<Attraction> listAttractionById(Integer stateId);
}