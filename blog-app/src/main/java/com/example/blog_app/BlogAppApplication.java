package com.example.blog_app;

import com.example.blog_app.entities.Role;
import com.example.blog_app.repository.RoleRepo;
import com.example.blog_app.utilies.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();

	}


	@Override
	public void run(String... args) throws Exception {
		System.out.println("the encoder password is::");
		System.out.println(this.passwordEncoder.encode("xyz"));
		try {
			Role adminRole = new Role();
			adminRole.setId(AppConstants.ADMIN_USER);
			adminRole.setName("ADMIN_USER");

			Role normalRole = new Role();
			normalRole.setId(AppConstants.NORMAL_USER);
			normalRole.setName("NORMAL_USER");
			List<Role> roles = List.of(adminRole,normalRole);
			List<Role> result = this.roleRepo.saveAll(roles);
			result.forEach(r ->{
				System.out.println(r.getName());
			});

		}catch (Exception e){
			System.out.println(e.getStackTrace());
		}
	}
}
