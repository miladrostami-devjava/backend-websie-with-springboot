package com.example.blog_app.payloads;

import com.example.blog_app.entities.Post;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Integer id ;
    private String content;
    private Post post;


}
