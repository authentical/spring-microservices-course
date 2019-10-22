package com.potatospy.photoapp.api.users.ui.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.potatospy.photoapp.api.users.service.UsersServiceImpl;
import com.potatospy.photoapp.api.users.shared.UserDto;
import com.potatospy.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.potatospy.photoapp.api.users.ui.model.CreateUserResponseModel;




@RestController
@RequestMapping("/users")
public class UsersController {
	
	
 
	
	@Autowired
	private Environment env;
	
	
	@Autowired
	UsersServiceImpl UsersServiceImpl;
	
	
	
	//
	// Get Environment information
	//
	@GetMapping("/status/check")
	public String status() {
		
		return "Spring application instance working on port: " + env.getProperty("local.server.port") + "\n"
				+ env.toString();
	}
	
	
	
	//
	// Create-User Requested
	//
	@PostMapping
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
		

		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDtoToService = modelMapper.map(userDetails, UserDto.class);
		UserDto createdUser = UsersServiceImpl.createUser(userDtoToService);
		
		
		
		CreateUserResponseModel responseBody = modelMapper.map(createdUser, CreateUserResponseModel.class);
		
		
		
		
		return new ResponseEntity<CreateUserResponseModel>(responseBody, HttpStatus.CREATED);
	}
	
	
}






































