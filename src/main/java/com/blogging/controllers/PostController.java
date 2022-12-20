package com.blogging.controllers;


import com.blogging.payloads.ImageResponse;
import com.blogging.payloads.PostDto;
import com.blogging.payloads.PostResponse;
import com.blogging.payloads.ResponseApi;
import com.blogging.services.FileService;
import com.blogging.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    FileService fileService;

    @Value("${project.image}")
    String filePath;

    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadFile(
            @PathVariable Integer postId,
            @RequestParam("image") MultipartFile image
    ) throws IOException {

        PostDto postDto = this.postService.getPostById(postId);

        String fileName = null;
        fileName = this.fileService.uploadImage(filePath,image);
        postDto.setImageName(fileName);
        PostDto updatedPost = this.postService.updatePost(postDto ,postId);
        return new ResponseEntity<>(updatedPost , HttpStatus.OK);
    }

    @GetMapping(value = "/posts/image/{imageName}" , produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName , HttpServletResponse httpServletResponse) throws IOException {
        InputStream is = this.fileService.getResource(filePath , imageName);
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(is , httpServletResponse.getOutputStream());
    }

    @PostMapping("/category/{categoryId}/user/{userId}/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto , @PathVariable Integer categoryId , @PathVariable Integer userId)
    {
       PostDto savedPost = this.postService.createPost(postDto,categoryId,userId);
       return new ResponseEntity<>(savedPost,HttpStatus.CREATED);

    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getAllPostByUser(@PathVariable Integer userId)
    {
        List<PostDto> posts = this.postService.getAllPostByUser(userId);
        return new ResponseEntity<>(posts , HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getAllPostByCategory(@PathVariable Integer categoryId)
    {
        List<PostDto> posts = this.postService.getAllPostByCategory(categoryId);
        return new ResponseEntity<>(posts , HttpStatus.OK);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer id)
    {
        PostDto postDto = this.postService.getPostById(id);
        return new ResponseEntity<>(postDto , HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPostByCategory(
            @RequestParam(value = "pageSize" , defaultValue = "4" , required = false) Integer pageSize ,
            @RequestParam(value = "pageNumber" , defaultValue = "0" , required = false) Integer pageNumber,
            @RequestParam(value = "sortBy" , defaultValue = "addDate" , required = false) String sortBy,
            @RequestParam(value = "sortDir" , defaultValue = "asc" , required = false) String sortDir)
    {
        PostResponse postResponse = this.postService.getAllPost(pageSize ,pageNumber,sortBy,sortDir);
         return new ResponseEntity<>(postResponse , HttpStatus.OK);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<ResponseApi> deletePost(@PathVariable Integer id)
    {
        this.postService.deletePost(id);
        return new ResponseEntity<>(new ResponseApi("Post deleted successfully !!!" , true),HttpStatus.OK);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto , @PathVariable Integer id)
    {
        PostDto updatedPost = this.postService.updatePost(postDto,id);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword)
    {
        List<PostDto> postDto = this.postService.searchPosts(keyword);
        return new ResponseEntity<>(postDto , HttpStatus.OK);
    }
}
