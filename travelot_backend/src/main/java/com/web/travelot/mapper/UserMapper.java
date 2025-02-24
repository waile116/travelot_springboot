package com.web.travelot.mapper;

import com.web.travelot.po.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Insert("insert into user values(#{userId},#{username},CONCAT('user_',#{username}),#{userSex},null,#{password},NOW(),NOW())")
    public int saveUser(User user);
    @Update("update user set user_img = #{userImg}, update_t = NOW() where id = #{userId}")
    public int updateUserImgById(User user);
    @Select("select * from user where id=#{userId} and password = #{password}")
    public User getUserByIdPass(User user);
    @Select("select count(*) from user where id=#{userId}")
    public int getUserCountById(Integer userId);
}
