package com.web.travelot.mapper;

import com.web.travelot.po.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Insert("insert into user values(#{userId},#{userName},#{userAlias},#{userSex},null,#{password},#{salt},NOW(),NOW(), 0)")
    public void saveUser(User user);
    @Update("update user set username = #{userName}, alias = #{userAlias}, sex = #{userSex}, update_t = NOW() where id = #{userId}")
    public void updateUser(User user);
    @Update("update user set user_img = #{userImg}, update_t = NOW() where id = #{userId}")
    public int updateUserImgById(User user);
    @Update("update user set password = #{password}, salt = #{salt}, update_t = NOW() where id = #{userId}")
    public int updateUserPassword(User user);

    // password matching is done in service
    @Select("select id, username, alias, sex, user_img, password, salt, isAdmin from user where id = #{userId}")
    public User getUserByIdPass(User user);

    // 在 UserMapper.xml
    public User getUserById(Integer userId);
}
