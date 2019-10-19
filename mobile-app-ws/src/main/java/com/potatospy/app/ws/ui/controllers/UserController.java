package com.potatospy.app.ws.ui.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.potatospy.app.ws.business.service.UserServiceImpl;
import com.potatospy.app.ws.exceptions.UserServiceException;
import com.potatospy.app.ws.ui.model.request.CalcRequest;
import com.potatospy.app.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.potatospy.app.ws.ui.model.request.UserDetailsRequestModel;
import com.potatospy.app.ws.ui.model.response.CalcResponse;
import com.potatospy.app.ws.ui.model.response.UserRest;


@RestController
@RequestMapping("users") // local 8080 /users
public class UserController {
	
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
    
    @PostConstruct
    public void init() {
		log.info("Logger is initialized for " + this.getClass());
    }
    
    
    
    
    @Autowired
    UserServiceImpl userService;	// Dependency injection
    
    Map<String, UserRest> usersMap = new HashMap<String, UserRest>();  //DELETE WHEN READY. STILL IN USE
    
    
    
    
	
	// Get all users and display page and optionally limit per page
	@GetMapping(
			produces = {MediaType.APPLICATION_JSON_VALUE, 
					MediaType.APPLICATION_XML_VALUE}
			)// If ReqParam param required=false, defaultValue IS required because ReqParam int is primitive and can't be converted to null which would be required if no defaultValue is set
	public UserRest getUsers(	
			@RequestParam(value="page", defaultValue="1", required=false) int page,	// Requestparam params other than boolean must be strings only	
			@RequestParam(value="limit", defaultValue="50", required=false) int limit,
			@RequestParam(value="sort", defaultValue="desc", required=false) String sort
			) {
		
		
		log.info("got all users. Page " + page + " with limit per page " + limit);
		
		return new UserRest();
	}
	
	
	
	
	
	@GetMapping(
			path = "/{userId}", // url/RequestMapping/PathVariable
			produces = { MediaType.APPLICATION_JSON_VALUE, 
					MediaType.APPLICATION_XML_VALUE }
			) 
	public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
		
		log.info("got user " + userId);
		
		

		
		
		if(usersMap.containsKey(userId)) { 
			return new ResponseEntity<UserRest>(usersMap.get(userId), HttpStatus.OK); 	
		}

		return new ResponseEntity<UserRest>(HttpStatus.NO_CONTENT);
	}
	
	
	
	
	
	@PostMapping(
			produces = { 
					MediaType.APPLICATION_JSON_VALUE, 
					MediaType.APPLICATION_XML_VALUE 
					}, 
			consumes = {
					MediaType.APPLICATION_JSON_VALUE, 
					MediaType.APPLICATION_XML_VALUE 
					})
	public ResponseEntity<UserRest> createUser(
			@Valid @RequestBody UserDetailsRequestModel userDetails
			) {
		
		log.info("################>" + this.getClass().getMethods().toString() + " needs it's log info set");
		
		
		UserRest returnValue = userService.createUser(userDetails);
		
		
		return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);
	}
	
	
	
	
	
	@PutMapping(
			path = "/{userId}",
			produces = { 
			MediaType.APPLICATION_JSON_VALUE, 
			MediaType.APPLICATION_XML_VALUE 
			}, 
	consumes = {
			MediaType.APPLICATION_JSON_VALUE, 
			MediaType.APPLICATION_XML_VALUE 
			})
	public UserRest updateUser(	// Todo Dont know why we not returning RequestEntity here
			@Valid @RequestBody UpdateUserDetailsRequestModel updateUserDetailsRequestModel,
			@PathVariable String userId
			) {
		
		
		log.info("################>" + this.getClass().getMethods().toString() + " needs it's log info set");

		
		// Transfer updateUserDetailsRequestModel to storedUserRest
		UserRest storedUserRest = usersMap.get(userId);
		storedUserRest.setFirstName(updateUserDetailsRequestModel.getFirstName());
		storedUserRest.setLastName(updateUserDetailsRequestModel.getLastName());
		
		
		// Transfer storedUserRest to usersMap
		if(usersMap.containsKey(userId)) {
			usersMap.put(userId, storedUserRest);
		}
		
		
		return usersMap.get(userId);
	}
	
	
	
	
	
	@DeleteMapping(
			path = "/{userId}",
			produces = { 
			MediaType.APPLICATION_JSON_VALUE, 
			MediaType.APPLICATION_XML_VALUE 
			}, 
	consumes = {
			MediaType.APPLICATION_JSON_VALUE, 
			MediaType.APPLICATION_XML_VALUE 
			})
	public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
		
		
		log.info("################>" + this.getClass().getMethods().toString() + " needs it's log info set");

		
		if(usersMap.containsKey(userId)) {
			
			
			usersMap.remove(userId);
		}
		
		
		
		// return new ResponseEntity.noContent().build();
		return  new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}


