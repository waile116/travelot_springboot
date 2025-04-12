package com.web.travelot.controller;

import com.web.travelot.po.Comment;
import com.web.travelot.po.CommonResult;
import com.web.travelot.po.User;
import com.web.travelot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/CommentController")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/saveComment/{userId}/{category}/{targetId}/{rating}/{text}")
    public CommonResult<Integer> saveComment(@PathVariable("userId") Integer userId,
                                                          @PathVariable("category") Integer category,
                                                          @PathVariable("targetId") Integer targetId,
                                                          @PathVariable("rating") Double rating,
                                                          @PathVariable("text") String text,
                                                          @RequestBody Map<String, String> requestBody) throws Exception {
        String commentImg = requestBody.get("userImg");
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setCategory(category);
        comment.setTargetId(targetId);
        comment.setRating(rating);
        comment.setText(text);
        comment.setCommentImg(commentImg);
        int result = commentService.saveComment(comment);
        return new CommonResult<>(200, "Save comment success", result);
    }

    @GetMapping("/listCommentByTargetId/{category}/{targetId}")
    public CommonResult<List<Comment>> listCommentByTargetId(@PathVariable("category") Integer category,
                                              @PathVariable("targetId") Integer targetId) throws Exception {
        Comment comment = new Comment();
        comment.setCategory(category);
        comment.setTargetId(targetId);
        List<Comment> result = commentService.listCommentByTargetId(comment);
        return new CommonResult<>(200, "success", result);
    }

    @GetMapping("/listCommentByUserId/{userId}")
    public CommonResult<List<Comment>> listCommentByUserId(@PathVariable("userId") Integer userId) throws Exception {
        List<Comment> result = commentService.listCommentByUserId(userId);
        return new CommonResult<>(200, "success", result);
    }
}
