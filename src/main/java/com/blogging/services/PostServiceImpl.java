package com.blogging.services;

import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.models.Category;
import com.blogging.models.Post;
import com.blogging.models.User;
import com.blogging.payloads.PostDto;
import com.blogging.payloads.PostResponse;
import com.blogging.repositories.CategoryRepo;
import com.blogging.repositories.PostRepo;
import com.blogging.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    PostRepo postRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto , Integer categoryId , Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User" , "userId" , userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category" , "categoryId" , categoryId));

        Post post = this.modelMapper.map(postDto , Post.class);
        post.setImageName("default.png");
        post.setAddDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post savedPost = this.postRepo.save(post);
        return this.modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer id) {
        Post post = this.postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post" , "postId" , id));

        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setAddDate(postDto.getAddDate());
        post.setImageName(postDto.getImageName());

        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost , PostDto.class);
    }

    @Override
    public void deletePost(Integer id) {
        Post post = this.postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post" , "postId" , id));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageSize , Integer pageNumber, String sortBy, String sortDir) {
        Pageable pageable;
        if(sortDir.equalsIgnoreCase("desc"))
        pageable = PageRequest.of(pageNumber , pageSize , Sort.by(sortBy).descending());
        else pageable = PageRequest.of(pageNumber , pageSize , Sort.by(sortBy).ascending());


        Page<Post> allPages = this.postRepo.findAll(pageable);
        List<Post> posts = allPages.getContent();
        List<PostDto> postDto = posts.stream().map(post -> this.modelMapper.map(post , PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDto);
        postResponse.setPageNumber(allPages.getNumber());
        postResponse.setPageSize(allPages.getSize());
        postResponse.setTotalContents(allPages.getTotalElements());
        postResponse.setTotalContentsCurrentPage(allPages.getNumberOfElements());
        postResponse.setTotalPages(allPages.getTotalPages());
        postResponse.setLastPage(allPages.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer id) {
        Post post = this.postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post" , "postId" , id));
        return this.modelMapper.map(post , PostDto.class);
    }

    @Override
    public List<PostDto> getAllPostByUser(Integer id) {
        User user = this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User" , "userId" , id));
        List<Post> posts =   this.postRepo.findAllByUser(user);
        return posts.stream().map(post -> this.modelMapper.map(post , PostDto.class)).collect(Collectors.toList());

    }

    @Override
    public List<PostDto> getAllPostByCategory(Integer id) {
        Category category = this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category" , "categoryId" , id) );
         List<Post> posts =   this.postRepo.findAllByCategory(category);
         return posts.stream().map(post -> this.modelMapper.map(post , PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepo.findByTitle("%"+keyword+"%");
        return posts.stream().map(post -> this.modelMapper.map(post ,PostDto.class)).collect(Collectors.toList());
    }
}
