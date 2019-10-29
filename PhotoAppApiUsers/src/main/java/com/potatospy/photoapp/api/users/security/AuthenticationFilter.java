package com.potatospy.photoapp.api.users.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.potatospy.photoapp.api.users.service.UsersServiceImpl;
import com.potatospy.photoapp.api.users.shared.UserDto;
import com.potatospy.photoapp.api.users.ui.model.LoginRequestModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;





// Register this WebSecurity -> It will be invoked every time a user does a login
// This filter is give to configure() in WebSecurity to help determine if the user can access resources
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	
	private UsersServiceImpl usersService;
	private Environment environment;
	
	
	public AuthenticationFilter(UsersServiceImpl usersService, 
			Environment environment, AuthenticationManager authenticationManager) {
		this.usersService = usersService;
		this.environment = environment;
		super.setAuthenticationManager(authenticationManager);
	}
	
	
	
	/* Take Login credentials off of the request, 
	*/
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			
			
			// Copy LoginRequestModel credentials from request
			LoginRequestModel credentials = 
					new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
			
			
			
			// To use this CONSTRUCTOR WAS REQUIRED TO super.setAuthenticationManager(authenticationManager);
			return getAuthenticationManager()
					.authenticate(
							new UsernamePasswordAuthenticationToken(
									credentials.getEmail(), 
									credentials.getPassword(),
									new ArrayList<>())
							);
			
	
			
		}catch(IOException e) {
			log.info("--------> attemptAuthentication got IOException {}");
			throw new RuntimeException(e);
		}
	}

	
	
	// If authentication is successful:
	//  
	// Get the username from the authentication Result,
	// Search for the username in the repository,
	// 
	//
	@Override
	protected void successfulAuthentication(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		log.info("successfulAuthentication for user: " + ((User) authResult.getPrincipal()).getUsername());
		
		String emailAsUsername = ((User) authResult.getPrincipal()).getUsername();
    	UserDto userDetails = usersService.getUserDetailsByEmail(emailAsUsername);
    	
    	
    	
    	
    	
    	// GENERATE SECURE JSON WEB TOKEN
    	
    	String token;
    	{
	        token = Jwts.builder()
	                .setSubject(userDetails.getUserId())
	                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
	                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret") )
	                .compact();
    	}
    	
    	
    	
    	// Add token and userId to response header
        response.addHeader("token", token);
        response.addHeader("userId", userDetails.getUserId());
		
	}
}
