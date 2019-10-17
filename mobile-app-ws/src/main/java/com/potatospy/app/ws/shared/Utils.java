package com.potatospy.app.ws.shared;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.potatospy.app.ws.ui.controllers.UserController;

@Service	// Because its injected into an autowired component?... Todo
public class Utils {
	
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	
	
	public String generatreUserId() {
		
		log.info("############################# Utils is instantiated");
		
		return UUID.randomUUID().toString();
	}

}
