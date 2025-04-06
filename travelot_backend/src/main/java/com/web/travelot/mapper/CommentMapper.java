package com.web.travelot.mapper;

import com.web.travelot.po.Comment;
import com.web.travelot.po.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment (user_id, category, target_id, rating, text, comment_img, create_t, update_t) " +
            "values (#{userId}, #{category}, #{target_id}, #{rating}, #{text}, #{commentImg}, NOW(),NOW())")
    public int saveComment(Comment comment);
    @Select("select * from comment where category = #{category} and target_id = #{targetId}")
    public List<Comment> listCommentByTargetId(Comment comment);

    @Select("select * from comment where user_id = #{userId}")
    public List<Comment> listCommentByUserId(Integer userId);
}
