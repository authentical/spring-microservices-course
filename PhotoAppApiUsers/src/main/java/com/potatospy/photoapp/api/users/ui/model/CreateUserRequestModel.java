package com.potatospy.photoapp.api.users.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;


// Object backing for user creation request


public class CreateUserRequestModel {
	
	
	
	@Getter
	@Setter
	@NotNull(message="It's null!")
	@Pattern(regexp="[A-Za-z]*", message="Alphas only")
	private String firstName;
	@Getter
	@Setter
	@NotNull(message="It's null!")
	@Pattern(regexp="[A-Za-z]*", message="Alphas only")
	private String lastName;
	@Getter
	@Setter
	@NotNull(message="It's null!")
	@Size(min=16, max=128)
	private String password;
	@Getter
	@Setter
	@NotNull(message="It's null!")
	@Email(message="Need an email address")
	private String email;

}
