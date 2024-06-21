package com.example.blog_app.controller;

import com.example.blog_app.config.jjwt.JwtAuthResponse;
import com.example.blog_app.config.jjwt.JwtTokenHelper;
import com.example.blog_app.exception.ApiException;
import com.example.blog_app.payloads.JwtAuthRequest;
import com.example.blog_app.payloads.UserDTO;
import com.example.blog_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired(required = false)
    private AuthenticationManager authenticationManager;
    @Autowired
 private    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {

        this.authenticate(jwtAuthRequest.getUsername(),jwtAuthRequest.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
     String token =   this.jwtTokenHelper.generateToken(userDetails);
JwtAuthResponse response = new JwtAuthResponse();
response.setToken(token);
return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
try {
    this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

}catch (BadCredentialsException e){
    System.out.println("Invalid Details !!!" + e.getMessage());
//    throw new Exception("invalid username or password!!!");
    throw new ApiException("invalid username or password!!!");


}

    }


    // register new user api
    @PostMapping("/register")
public ResponseEntity<UserDTO> registerNewUser(@RequestBody UserDTO userDTO){
        UserDTO registeredUser = this.userService.registerNewUser(userDTO);
        return new ResponseEntity<>(registeredUser,HttpStatus.CREATED);
    }

}
