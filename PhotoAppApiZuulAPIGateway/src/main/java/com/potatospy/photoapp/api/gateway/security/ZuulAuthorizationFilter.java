package com.potatospy.photoapp.api.gateway.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import jdk.internal.jline.internal.Log;



public class ZuulAuthorizationFilter extends BasicAuthenticationFilter {
    
	
	Environment environment;

	
    public ZuulAuthorizationFilter(AuthenticationManager authManager, Environment environment) {
        super(authManager);
        this.environment = environment;
    }
    
    

    // Called by Spring framework when an auth request comes to the server
    // 
    @Override
    protected void doFilterInternal(HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain) throws IOException, ServletException {

    	
    	// Read the header from request
        String authorizationHeader = req.getHeader(environment.getProperty("authorization.token.header.name"));

        
        // If header is null or, doesn't start with Bearer 
        if (authorizationHeader == null || !authorizationHeader.startsWith(environment.getProperty("authorization.token.header.prefix"))) {
            
        	chain.doFilter(req, res);	// Go to the next filter in chain
            return;
        }

        
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
       
        
       
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);	// Go to the next filter in chain
    }  
     
    
    
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
    	
    	
         String authorizationHeader = req.getHeader(environment.getProperty("authorization.token.header.name"));

         if (authorizationHeader == null) {
             return null;
         }

         // Remove Token Prefix from authheader
         String token = authorizationHeader.replace(environment.getProperty("authorization.token.header.prefix"), "");

         
         // Decrypt auth header and get userId
         String userId = Jwts.parser()
                 .setSigningKey(environment.getProperty("token.secret"))	// SAME AS is used for Users-ws
                 .parseClaimsJws(token)
                 .getBody()
                 .getSubject();
         //Log.info(userId);

         if (userId == null) {
             return null;
         }
   
         return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
     }
}














