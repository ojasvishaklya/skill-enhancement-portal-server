package com.telstra.controller;


import com.telstra.dto.CommentRequest;
import com.telstra.dto.CommentResponse;
import com.telstra.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/comment")
    public CommentResponse createComment(@RequestBody CommentRequest commentRequest) {
        return commentService.createComment(commentRequest);
    }

    @PostMapping("/comment/{id}/upvote")
    public String upVote(@PathVariable Long id) {
        return commentService.upVote(id);
    }

    @PostMapping("/comment/{id}/downvote")
    public String downVote(@PathVariable Long id) {
        return commentService.upVote(id);
    }
    @PostMapping("/comment/{id}/select")
    public String selectComment(@PathVariable Long id) {
        return commentService.selectComment(id);
    }

    @PostMapping("/comment/{id}/delete")
    public String deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }

}
