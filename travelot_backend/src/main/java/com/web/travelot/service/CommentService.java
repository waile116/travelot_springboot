package com.web.travelot.service;

import com.web.travelot.po.Comment;
import java.util.List;

public interface CommentService {
    public int saveComment(Comment comment);
    public List<Comment> ListCommentByTargetId(Comment comment);
}
