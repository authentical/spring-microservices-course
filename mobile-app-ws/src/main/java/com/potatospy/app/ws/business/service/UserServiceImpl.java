package com.potatospy.app.ws.business.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.potatospy.app.ws.shared.Utils;
import com.potatospy.app.ws.ui.model.request.UserDetailsRequestModel;
import com.potatospy.app.ws.ui.model.response.UserRest;



@Service
public class UserServiceImpl {
	
	
	
	Map<String, UserRest> usersMap;
	Utils utils; // This does..................
	
	
	
	public UserServiceImpl() {} //.................... TEST IF THIS IS REQUIRED
	
	
	
	@Autowired
	public UserServiceImpl(Utils  utils) {
		this.utils=utils;
	}
	
	
	
	
	public UserRest createUser(UserDetailsRequestModel userDetails) {
		
		
		
		UserRest returnValue = new UserRest();
		returnValue.setFirstName(userDetails.getFirstName());
		returnValue.setLastName(userDetails.getLastName());
		returnValue.setEmail(userDetails.getEmail());
		
		String userId	=	utils.generatreUserId();
		returnValue.setUserId(userId);
		
		if(usersMap==null) usersMap = new HashMap<>();
		usersMap.put(userId, returnValue);
		
		
		return returnValue;
	}
}