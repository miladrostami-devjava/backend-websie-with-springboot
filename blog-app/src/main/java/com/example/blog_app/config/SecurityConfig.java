package com.example.blog_app.config;


import com.example.blog_app.config.jjwt.JwtAuthenticationEntryPoint;
import com.example.blog_app.config.jjwt.JwtAuthenticationFilter;
import com.example.blog_app.config.security.CustomUserDetailService;
import com.example.blog_app.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

@Autowired
    private  CustomUserDetailService customUserDetailService;

@Autowired
private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
@Autowired
private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf(
                        (csrf) -> {
                    try {
                        csrf
                                .csrfTokenRepository(new HttpSessionCsrfTokenRepository())
                        .disable()
                        .authorizeHttpRequests((authz)-> authz
                                .anyRequest()
                                .authenticated())
//                                .securityMatcher("/api/v1/auth/login")
                                .securityMatcher("/api/v1/auth/**")
                                .securityMatcher(String.valueOf(HttpMethod.GET))
                                .exceptionHandling((username)-> new ResourceNotFoundException("Authentication" + username,"Authentication Id",0))
                         //  .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
                             //   .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                             .httpBasic(Customizer.withDefaults());

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        return httpSecurity.build();
    }

  /*  @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        BasicAuthenticationEntryPoint entryPoint = new  BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("admin realm");
        return entryPoint;
    }*/


    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());
    }

  /*  @Bean
    public SecurityFilterChain   configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());
        return (SecurityFilterChain) auth;
    }*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

   /* @Bean
    public ResponseEntity<AuthenticationManager> authenticationManagerBean() throws Exception{
        return new ResponseEntity<>((AuthenticationManager)jwtAuthenticationEntryPoint, HttpStatus.OK)  ;
    }*/

    @Bean
    public JwtAuthenticationEntryPoint authenticationManagerBean() throws Exception{
        return jwtAuthenticationEntryPoint   ;
    }


}
