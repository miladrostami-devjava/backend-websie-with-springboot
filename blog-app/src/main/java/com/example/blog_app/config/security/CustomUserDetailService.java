package com.example.blog_app.config.security;

import com.example.blog_app.entities.User;
import com.example.blog_app.exception.ResourceNotFoundException;
import com.example.blog_app.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService  implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //load user from  database by username
        User user = this.userRepo.findByEmail(username)
                .orElseThrow(()-> new ResourceNotFoundException("User","email :" + username,0));

        return user;
    }
}
