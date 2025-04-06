package com.web.travelot.service;

import com.web.travelot.po.Comment;
import java.util.List;

public interface CommentService {
    public int saveComment(Comment comment);
    public List<Comment> listCommentByTargetId(Comment comment);
    public List<Comment> listCommentByUserId(Integer userId);

}
