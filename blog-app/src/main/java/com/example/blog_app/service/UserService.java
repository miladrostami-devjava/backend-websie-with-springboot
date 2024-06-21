package com.example.blog_app.service;


import com.example.blog_app.entities.User;
import com.example.blog_app.payloads.UserDTO;

import java.util.List;

public interface UserService {
   UserDTO  registerNewUser(UserDTO user);
   UserDTO createUser(UserDTO userDTO);
   UserDTO updateUser(UserDTO userDTO,Integer userId);
   UserDTO getUserById(Integer userId);
   List<UserDTO> getAllUsers();
   void deleteUser(Integer userId);
}
