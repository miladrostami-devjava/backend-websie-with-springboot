package com.example.blog_app.service;

import com.example.blog_app.entities.Category;
import com.example.blog_app.entities.Post;
import com.example.blog_app.entities.User;
import com.example.blog_app.exception.ResourceNotFoundException;
import com.example.blog_app.payloads.PostDto;
import com.example.blog_app.payloads.PostResponse;
import com.example.blog_app.repository.CategoryRepo;
import com.example.blog_app.repository.PostRepo;
import com.example.blog_app.repository.UserRepo;
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
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        Post posts = this.modelMapper.map(postDto, Post.class);
        posts.setImageName("default.png");
        posts.setAddedDate(new Date());
        posts.setUser(user);
        posts.setCategory(category);
        Post newPosts = this.postRepo.save(posts);
        return this.modelMapper.map(newPosts, PostDto.class);
    }


    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post","Post Id",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatePost = this.postRepo.save(post);
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
Post post = this.postRepo.findById(postId)
        .orElseThrow(()-> new ResourceNotFoundException("Post","Post Id",postId));
this.postRepo.delete(post);
    }

/*    @Override
    public List<PostDto> getAllPost() {
List<Post> allPosts = this.postRepo.findAll();
List<PostDto> postDtos = allPosts
        .stream()
        .map(post -> this.modelMapper.map(post, PostDto.class))
        .collect(Collectors.toList());
        return postDtos;
    }*/

// pageable in get all post
/*    @Override
    public List<PostDto> getAllPost(Integer pageNumber,Integer pageSize) {

        Pageable pageable =  PageRequest.of(pageNumber,pageSize);
        Page<Post> postPage = this.postRepo.findAll( pageable);
        List<Post> allPosts = postPage.getContent();
        List<PostDto> postDtos = allPosts
                .stream()
                .map(post -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }*/

    @Override
    public PostResponse
    getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
       /* Sort sort = null;
        if (sortDir.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }else {
            sort= Sort.by(sortBy).descending();
        }*/

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();


        Pageable pageable =  PageRequest.of(pageNumber,pageSize, Sort.by(sortBy).descending());
        Page<Post> postPage = this.postRepo.findAll( pageable);
        List<Post> allPosts = postPage.getContent();
        List<PostDto> postDtos = allPosts
                .stream()
                .map(post -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());
        return postResponse;
    }


    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post","Post Id",postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        List<Post> posts = this.postRepo.findByCategory(category);
        List<PostDto> postDtos = posts
                .stream()
                .map((post -> this.modelMapper.map(post, PostDto.class)))
                .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postDtos = posts
                .stream()
                .map((post -> this.modelMapper.map(post, PostDto.class)))
                .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
List<Post> posts = this.postRepo.findByTitleContaining("%" + keyword + "%");
List<PostDto> postDtos = posts
        .stream()
        .map(post -> this.modelMapper.map(post, PostDto.class))
        .collect(Collectors.toList());

        return postDtos;
    }
}
