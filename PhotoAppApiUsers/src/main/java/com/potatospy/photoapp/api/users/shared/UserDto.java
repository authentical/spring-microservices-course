package com.potatospy.photoapp.api.users.shared;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

public class UserDto {
	

	@Getter
	@Setter
	private String firstName;
	@Getter
	@Setter
	private String lastName;
	@Getter
	@Setter
	private String password;
	@Getter
	@Setter
	private String email;
	@Getter
	@Setter
	private String encryptedPassword;
	@Getter
	@Setter
	private String userId;

}
