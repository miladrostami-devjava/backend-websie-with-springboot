package com.example.blog_app.payloads;


import lombok.Data;

@Data
public class JwtAuthRequest {
private String username;
private String password;

}
