package com.blogging.services;

import com.blogging.models.Comment;
import com.blogging.payloads.CommentDto;

import java.util.List;

public interface CommentService {

    public CommentDto createComment(CommentDto commentDto,Integer postId,Integer userId);

    public CommentDto updateComment(CommentDto commentDto , Integer id);

    public CommentDto getCommentById(Integer id);

    public void deleteComment(Integer id);

    public List<CommentDto> getAllComments();

    public List<CommentDto> getAllCommentsByPost(Integer postId);


}
