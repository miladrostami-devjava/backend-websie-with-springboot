package com.example.blog_app.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Setter
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    @Column(name = "title",length = 100,nullable = false)
    @NotBlank
    @Size(min = 4, message = "please enter min 4")
    private String categoryTitle;
    @Column(name = "description")
    @NotBlank
    @Size(max = 95 , message = "please enter max 95")
    private String categoryDescription;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<Post> posts = new ArrayList<>();

}
