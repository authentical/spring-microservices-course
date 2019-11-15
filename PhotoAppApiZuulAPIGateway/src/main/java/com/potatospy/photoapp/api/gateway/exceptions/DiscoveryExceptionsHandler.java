package com.potatospy.photoapp.api.gateway.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.netflix.zuul.exception.ZuulException;
import com.potatospy.photoapp.api.gateway.model.ErrorMessageResponse;




//In combination with other Error Message and custom Exception(extended Runtime) classes,
//This class is used to send custom exception messages in web response body instead



//ControllerAdvice is specialized @Component but it is used to apply to all Controllers globally
@ControllerAdvice
public class DiscoveryExceptionsHandler {
	
	
	@ExceptionHandler(value = {ZuulException.class})
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request){
		
		
		// Create an error message with timestamp NOW and 
		// (the localized exception message, OR if that does not exist, the exception tostring)
		
		String errorDescription = ex.getLocalizedMessage();
		if(errorDescription==null) errorDescription = ex.toString();
		
		ErrorMessageResponse errorMessage = new ErrorMessageResponse(
				new Date(), 
				errorDescription
				);
		
		
		return new  ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}