package com.example.blog_app.service;

import com.example.blog_app.entities.Role;
import com.example.blog_app.entities.User;
import com.example.blog_app.exception.ResourceNotFoundException;
import com.example.blog_app.payloads.UserDTO;
import com.example.blog_app.repository.RoleRepo;
import com.example.blog_app.repository.UserRepo;
import com.example.blog_app.utilies.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private RoleRepo roleRepo;
@Autowired
private PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepo userRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO registerNewUser(UserDTO user) {
        User newUser  = this.modelMapper.map(user, User.class);
       newUser.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Role role =  this.roleRepo.findById(AppConstants.NORMAL_USER).get();
        newUser.getRoles().add(role);
        User saveUser = this.userRepo.save(newUser);
        return this.modelMapper.map(saveUser, UserDTO.class);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = this.dtoToUser(userDTO);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
user.setName(userDTO.getName());
user.setEmail(userDTO.getEmail());
user.setPassword(userDTO.getPassword());
user.setAbout(userDTO.getAbout());
User updatedUser = this.userRepo.save(user);
UserDTO userDto = this.userToDto(updatedUser);
        return userDto;
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDTO> userDTOS = users
                .stream()
                .map(user-> this.userToDto(user))
                .collect(Collectors.toList());
        return userDTOS;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        this.userRepo.delete(user);
    }


    // with out modelMapper
 /*   private User dtoToUser(UserDTO userDTO){

       User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());
        return user;
    }*/
    private User dtoToUser(UserDTO userDTO){
        User user = this.modelMapper.map(userDTO, User.class);

        return user;
    }
    // with out modelMapper
/*private UserDTO userToDto(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setAbout(user.getAbout());
        return userDTO;
}*/
private UserDTO userToDto(User user){
    UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
    return userDTO;
}


}
