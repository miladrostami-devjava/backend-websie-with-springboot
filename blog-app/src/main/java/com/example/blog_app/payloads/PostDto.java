package com.example.blog_app.payloads;

import com.example.blog_app.entities.Category;
import com.example.blog_app.entities.Comment;
import com.example.blog_app.entities.User;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class PostDto {
    private Integer postId;
    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    private CategoryDto category;
    private UserDTO user;
    private Set<CommentDto> comments = new HashSet<>();


}
