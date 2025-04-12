package com.web.travelot.mapper;

import com.web.travelot.po.Favourite;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FavouriteMapper {
    @Insert("insert into favourite (user_id, category, target_id, create_t) " +
            "values (#{userId}, #{category}, #{target_id}, NOW())")
    public int saveFavourite(Favourite favourite);
    @Delete("delete from favourite where user_id = #{userId} and category = #{category} and target_id = #{targetId}")
    public int removeFavourite(Favourite favourite);
    @Select("select * from favourite where user_id = #{userId} and category = #{category} and target_id = #{targetId}")
    public Favourite getFavouriteById(Favourite favourite);

    @Select("select * from favourite where user_id = #{userId}")
    public List<Favourite> listFavouriteByUserId(Integer userId);
}
