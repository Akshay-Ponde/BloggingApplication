package com.blogging.controllers;

import com.blogging.payloads.CommentDto;
import com.blogging.payloads.ResponseApi;
import com.blogging.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/posts/{postId}/user/{userId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto  , @PathVariable Integer postId , @PathVariable Integer userId)
    {
        CommentDto savedComment = this.commentService.createComment(commentDto,postId,userId);
        return new ResponseEntity<>(savedComment , HttpStatus.CREATED);
    }
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ResponseApi> createComment(@PathVariable Integer commentId)
    {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ResponseApi("Comment deleted Successfully !!!" , true) , HttpStatus.OK);
    }
}
