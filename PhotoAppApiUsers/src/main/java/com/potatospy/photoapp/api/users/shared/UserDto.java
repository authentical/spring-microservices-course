package com.potatospy.photoapp.api.users.shared;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

public class UserDto {
	

	private String firstName;

	private String lastName;

	private String password;

	private String email;
	
	private String encryptedPassword;

	private String userId;

}
