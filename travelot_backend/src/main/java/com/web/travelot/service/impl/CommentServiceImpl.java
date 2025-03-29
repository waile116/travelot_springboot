package com.web.travelot.service.impl;

import com.web.travelot.mapper.CommentMapper;
import com.web.travelot.po.Comment;
import com.web.travelot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public int saveComment(Comment comment){
        return commentMapper.saveComment(comment);
    }
    @Override
    public List<Comment> ListCommentByTargetId(Comment comment){
        return commentMapper.listCommentByTargetId(comment);
    }
}
