package com.potatospy.app.ws.ui.controllers;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.potatospy.app.ws.ui.model.response.UserRest;


@RestController
@RequestMapping("users") // local 8080 /users
public class UserController {
	
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @PostConstruct
    public void init() {
    	logger.debug("Logger is initialized for " + this.getClass());
    }
    
	
	// Get all users and display page and optionally limit per page
	@GetMapping()						// If ReqParam param required=false, defaultValue IS required because ReqParam int is primitive and can't be converted to null which would be required if no defaultValue is set
	public UserRest getUsers(	
			@RequestParam(value="page", defaultValue="1", required=false) int page,	// Requestparam params other than boolean must be strings only	
			@RequestParam(value="limit", defaultValue="50", required=false) int limit,
			@RequestParam(value="sort", defaultValue="desc", required=false) String sort
			) {
		
		
		logger.debug("got all users. Page " + page + " with limit per page " + limit);
		
		return new UserRest();
	}
	
	
	
	@GetMapping(path="/{userId}")	// url/RequestMapping/PathVariable
	public String getUser(@PathVariable String userId) {
		
		return "got user " + userId;
	}
	
	
	
	@PostMapping
	public String createUser() {
		
		return "created user";
	}
	
	
	
	@PutMapping
	public String updateUser() {
		
		return "updated user";
	}
	
	
	@DeleteMapping
	public String deleteUser() {
		
		return "deleted user";
	}
}
