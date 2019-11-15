package com.potatospy.photoapp.api.users.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.potatospy.photoapp.api.users.service.UsersServiceImpl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


@Slf4j		// Todo This one... I don't want it to log into a random file...how do I specifyiyyty
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.IGNORED_ORDER)
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	
	
	private Environment environment;
	private UsersServiceImpl usersService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	
	
	
	@Autowired
	public WebSecurity(Environment environment, UsersServiceImpl usersService, BCryptPasswordEncoder bCryptPasswordEncoder)
	{
		this.environment = environment;
		this.usersService = usersService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip"))
		
		.and().addFilter(getAuthenticationFilter()); // Register AuthenticationFilter with THIS HTTP security 
		
		
		http.headers().frameOptions().disable(); // h2 console runs in a frame so disable frame options
		
	}
	
	
	
	
	private AuthenticationFilter getAuthenticationFilter() throws Exception
	{
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, environment, authenticationManager());
		//authenticationFilter.setAuthenticationManager(authenticationManager()); 
		authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
		return authenticationFilter;
	}

	
	
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
    		// AuthenticationManagerBuilder requires that service extends 
        auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
    }
}