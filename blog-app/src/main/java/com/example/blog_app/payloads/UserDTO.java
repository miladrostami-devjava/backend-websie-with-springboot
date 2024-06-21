package com.example.blog_app.payloads;

import com.example.blog_app.entities.Role;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
public class UserDTO {
    private int id;
    private String name;
    private String email;
    private String password;
    private String about;

    private Set<RoleDto> roles = new HashSet<>();

}
