package com.potatospy.photoapp.api.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



// Spring Security configuration
// For Authentication and Filtering see AuthorizationFilter 


@Configuration
@EnableWebSecurity
@Order(SecurityProperties.IGNORED_ORDER)
public class ZuulWebSecurity extends WebSecurityConfigurerAdapter {
	
	
	
	private Environment environment;


	
	
	@Autowired
	public ZuulWebSecurity(Environment environment)
	{
		this.environment = environment;

	}
	
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		
		http.csrf().disable();
		
		http.headers().frameOptions().disable(); // h2 console runs in a frame so disable frame options
		
		
		http.authorizeRequests()
		.antMatchers(environment.getProperty("api.h2console.url.path")).permitAll()
		.antMatchers(HttpMethod.POST, environment.getProperty("api.registration.url.path")).permitAll()
		.antMatchers(HttpMethod.POST, environment.getProperty("api.login.url.path")).permitAll()
		// And any other requests need to be authenticated
		.anyRequest().authenticated()
		.and().addFilter(new ZuulAuthorizationFilter(authenticationManager(), environment));
	
		
		/*
		 * Make the API stateless -> Don't want cookies caching the auth header FROM
		 * DOCS: Spring Security will never create an HttpSession and it will never use
		 * it to obtain the SecurityContext
		 */
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}
	
	
	
	
	
	
	
	
}