package com.potatospy.photoapp.api.users;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.potatospy.photoapp.api.users.shared.UserDto;

@SpringBootApplication
@EnableDiscoveryClient
public class PhotoAppApiUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppApiUsersApplication.class, args);
	}
	

	
	@Bean	// This is injected into UsersService
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		
		
		return new BCryptPasswordEncoder();
	}
}
