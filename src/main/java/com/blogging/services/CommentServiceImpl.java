package com.blogging.services;

import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.models.Comment;
import com.blogging.models.Post;
import com.blogging.models.User;
import com.blogging.payloads.CommentDto;
import com.blogging.repositories.CommentRepo;
import com.blogging.repositories.PostRepo;
import com.blogging.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PostRepo postRepo;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public CommentDto createComment(CommentDto commentDto,Integer postId,Integer userId) {
         User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User" ,"userId" , userId));
         Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post" ,"postId" , postId));

        Comment comment = this.modelMapper.map(commentDto , Comment.class);
        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment , CommentDto.class);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer id) {

        Comment comment = this.commentRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Comment" ,"commentId" , id));

        comment.setContent(commentDto.getContent());
        Comment updatedComment = this.commentRepo.save(comment);

        return this.modelMapper.map(updatedComment , CommentDto.class);
    }

    @Override
    public CommentDto getCommentById(Integer id) {
        Comment comment = this.commentRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Comment" ,"commentId" , id));
        return this.modelMapper.map(comment , CommentDto.class);
    }

    @Override
    public void deleteComment(Integer id) {
        Comment comment = this.commentRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Comment" ,"commentId" , id));
        this.commentRepo.delete(comment);
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = this.commentRepo.findAll();
        return comments.stream().map(comment -> this.modelMapper.map(comment , CommentDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getAllCommentsByPost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post" ,"postId" , postId));
        List<Comment> comments = this.commentRepo.findAllByPost(post);
        return comments.stream().map(comment -> this.modelMapper.map(comment , CommentDto.class)).collect(Collectors.toList());
    }
}
