package com.example.blog_app.controller;


import com.example.blog_app.entities.Comment;
import com.example.blog_app.payloads.ApiResponse;
import com.example.blog_app.payloads.CommentDto;
import com.example.blog_app.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId){
CommentDto createCommentDto = this.commentService.createComment(commentDto,postId);
return new ResponseEntity<>(createCommentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("comments/{commentId}/")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
      this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("comment deleted successfully!!!",true), HttpStatus.OK);
    }

}
