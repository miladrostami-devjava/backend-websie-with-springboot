package com.example.blog_app.service;

import com.example.blog_app.entities.Post;
import com.example.blog_app.payloads.PostDto;
import com.example.blog_app.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
    PostDto updatePost(PostDto postDto,Integer postId);
    void deletePost(Integer postId);
//    List<PostDto>  getAllPost(Integer pageNumber,Integer pageSize);

    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,
                            String sortDir);
    PostDto getPostById(Integer postId);
    List<PostDto> getPostByCategory(Integer categoryId);
    List<PostDto> getPostByUser(Integer userId);
    List<PostDto> searchPosts(String keyword);

}
