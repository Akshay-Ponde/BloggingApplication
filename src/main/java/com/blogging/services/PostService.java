package com.blogging.services;

import com.blogging.payloads.PostDto;
import com.blogging.payloads.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto , Integer categoryId , Integer UserId);
    PostDto updatePost(PostDto postDto, Integer id);
    void deletePost(Integer id);
    PostResponse getAllPost(Integer pageSize , Integer pageNumber, String sortBy, String sortDir);
    PostDto getPostById(Integer id);
    List<PostDto> getAllPostByUser(Integer id);
    List<PostDto> getAllPostByCategory(Integer id);
    List<PostDto> searchPosts(String keyword);


}
